package com.twg.notesapp.service;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.twg.notesapp.AppConstants.IntentKeys;
import com.twg.notesapp.AppConstants.ModelTypes;
import com.twg.notesapp.adapters.NotesAdapter;
import com.twg.notesapp.database.Database;
import com.twg.notesapp.database.DatabaseException;
import com.twg.notesapp.database.DatabaseImpl;
import com.twg.notesapp.database.DatabaseOpenHelper;
import com.twg.notesapp.intentprocessors.IntentProcessor;
import com.twg.notesapp.intentprocessors.NotesIntentProcessor;
import com.twg.notesapp.network.Network;
import com.twg.notesapp.network.NetworkImpl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class NotesAppService extends Service {

	private Looper serviceLooper;
	private ServiceHandler serviceHandler;

	private LinkedList<Intent> intentQueue = new LinkedList<Intent>();
	private final HashMap<String, IntentProcessor> intentProcessors = new HashMap<String, IntentProcessor>();

	private Database database;
	private Network network;

	@Override
	public void onCreate() {
		super.onCreate();

		this.database = new DatabaseImpl(new DatabaseOpenHelper(this),
				new ArrayList<Runnable>());
		this.network = new NetworkImpl();

		try {
			this.database.open();
			this.initIntentProcessors();

			final HandlerThread thread = new HandlerThread(
					NotesAppService.class.getSimpleName());
			thread.start();

			this.serviceLooper = thread.getLooper();
			this.serviceHandler = new ServiceHandler(this, this.serviceLooper);
		} catch (DatabaseException e) {
			Log.e("Freshbooks Engine", "Failed to open the database", e);
		}
	}

	private void initIntentProcessors() {
		final NotesAdapter notesAdapter = new NotesAdapter(this.database,
				this.network);
		final NotesIntentProcessor notesIntentProcessor = new NotesIntentProcessor(
				notesAdapter);
		this.intentProcessors.put(ModelTypes.NOTES, notesIntentProcessor);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			synchronized (this.intentQueue) {
				this.intentQueue.add(intent);
			}
			this.serviceHandler.sendEmptyMessage(0);
		}
		return START_STICKY;
	}

	private void processIntents() {
		final List<Intent> intents = new ArrayList<Intent>();
		synchronized (this.intentQueue) {
			intents.addAll(this.intentQueue);
			this.intentQueue.clear();
		}

		for (final Intent intent : intents) {
			final Bundle payload = intent.getExtras();
			final String modelType = payload.getString(IntentKeys.MODEL_TYPE);
			final IntentProcessor intentProcessor = this.intentProcessors
					.get(modelType);
			intentProcessor.process(intent);
		}
	}

	private static final class ServiceHandler extends Handler {
		private final WeakReference<NotesAppService> service;

		public ServiceHandler(final NotesAppService service, final Looper looper) {
			super(looper);
			this.service = new WeakReference<NotesAppService>(service);
		}

		@Override
		public void handleMessage(Message msg) {
			final NotesAppService service = this.service.get();
			service.processIntents();
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		this.database.close();
		super.onDestroy();
	}

}
