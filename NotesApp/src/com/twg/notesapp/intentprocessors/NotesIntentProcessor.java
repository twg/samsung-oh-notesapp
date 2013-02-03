package com.twg.notesapp.intentprocessors;

import java.util.ArrayList;

import com.twg.notesapp.AppConstants.IntentKeys;
import com.twg.notesapp.AppConstants.Reads;
import com.twg.notesapp.AppConstants.ResultCode;
import com.twg.notesapp.adapters.NotesAdapter;

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
		Bundle resultData = new Bundle();
		resultData.putParcelableArrayList(command, notes);
		resultReceiver.send(ResultCode.SUCCESS, resultData);
		resultReceiver.send(ResultCode.COMPLETE, null);
	}

}
