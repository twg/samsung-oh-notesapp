package com.twg.notesapp.intentprocessors;

import java.util.ArrayList;

import com.twg.notesapp.AppConstants.IntentKeys;
import com.twg.notesapp.AppConstants.ModelProperties;
import com.twg.notesapp.AppConstants.Reads;
import com.twg.notesapp.AppConstants.ResultCode;
import com.twg.notesapp.adapters.NotesAdapter;
import com.twg.notesapp.database.DatabaseException;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class NotesIntentProcessor extends IntentProcessor {

	private final NotesAdapter adapter;

	public NotesIntentProcessor(final NotesAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void process(Intent intent) {
		final Bundle bundle = intent.getExtras();
		final String command = bundle.getString(IntentKeys.COMMAND);

		if (command.equals(Reads.GET_NOTES)) {
			this.processGetNotes(command, bundle);
		} else if (command.equals(Reads.GET_CACHED_NOTE)) {
			this.processGetCachedNote(command, bundle);
		}
	}
	
	private void processGetNotes(String command, Bundle bundle) {
		final ContentValues params = bundle
				.getParcelable(IntentKeys.PARAMS);
		final ResultReceiver resultReceiver = bundle
				.getParcelable(IntentKeys.RECEIVER);

		resultReceiver.send(ResultCode.LOADING, null);
		final ArrayList<ContentValues> notes = this.adapter
				.getNotes(params);
		try {
			this.adapter.deleteAllNotes();
			this.adapter.saveNotes(notes);
			Bundle resultData = new Bundle();
			resultData.putParcelableArrayList(command, notes);
			resultReceiver.send(ResultCode.SUCCESS, resultData);
		} catch (DatabaseException e) {
			resultReceiver.send(ResultCode.ERROR, null);
		} finally {
			resultReceiver.send(ResultCode.COMPLETE, null);
		}
	}
	
	private void processGetCachedNote(String command, Bundle bundle) {
		final ResultReceiver resultReceiver = bundle
				.getParcelable(IntentKeys.RECEIVER);
		final int noteId = bundle.getInt(ModelProperties.ID);
		
		resultReceiver.send(ResultCode.LOADING, null);
		final ContentValues note = this.adapter.getNoteFromCache(noteId);
		if (note != null) {
			Bundle resultData = new Bundle();
			resultData.putParcelable(command, note);
			resultReceiver.send(ResultCode.SUCCESS, resultData);
		} else {
			resultReceiver.send(ResultCode.ERROR, null);
		}

		resultReceiver.send(ResultCode.COMPLETE, null);
	}

}
