package com.twg.notesapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.twg.notesapp.AppConstants.IntentKeys;
import com.twg.notesapp.AppConstants.ModelProperties;
import com.twg.notesapp.AppConstants.ModelTypes;
import com.twg.notesapp.AppConstants.Reads;
import com.twg.notesapp.model.NoteModel;
import com.twg.notesapp.service.NotesAppService;
import com.twg.notesapp.service.ServiceError;
import com.twg.notesapp.service.ServiceResultHandler;
import com.twg.notesapp.service.ServiceResultReceiver;

public class NotesDetailActivity extends Activity implements
		ServiceResultHandler {

	private static final String TAG = "NotesDetailActivity";

    private ServiceResultReceiver resultReceiver;

	private TextView title;
	private TextView body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes_detail);

		this.title = (TextView) this.findViewById(R.id.note_detail_title);
		this.body = (TextView) this.findViewById(R.id.note_detail_body);
		
        this.resultReceiver = new ServiceResultReceiver(new Handler());

        final Intent receivedIntent = this.getIntent();
        final Bundle bundle = receivedIntent.getExtras();
        final int modelId = bundle.getInt(ModelProperties.ID);
        
        final Intent intentToService = this.marshallGetCachedNoteIntent(modelId);
        this.startService(intentToService);
	}
	
	private Intent marshallGetCachedNoteIntent(int modelId) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKeys.COMMAND, Reads.GET_CACHED_NOTE);
        bundle.putString(IntentKeys.MODEL_TYPE, ModelTypes.NOTES);
        bundle.putInt(ModelProperties.ID, modelId);

        bundle.putParcelable(IntentKeys.RECEIVER, this.resultReceiver);

        final Intent intent = new Intent(this, NotesAppService.class);
        intent.putExtras(bundle);

        return intent;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_notes_detail, menu);
		return true;
	}

	@Override
    protected void onResume() {
        super.onResume();
        this.resultReceiver.setResultHandler(this);
    }

    @Override
    protected void onPause() {
        this.resultReceiver.setResultHandler(null);
        super.onPause();
    }
	
	@Override
	public void onStartUp() {
        Log.i(TAG, "Startup");		
	}

	@Override
	public void onSuccess(Bundle data) {
        Log.i(TAG, "Success");
        final ContentValues modelValues = data.getParcelable(Reads.GET_CACHED_NOTE);
        final NoteModel note = new NoteModel(modelValues);
        
        this.title.setText(note.getTitle());
        this.body.setText(note.getBody());
	}

	@Override
	public void onError(ServiceError error) {
        Log.i(TAG, "Error");		
	}

	@Override
	public void onComplete() {
        Log.i(TAG, "Complete");
	}

}
