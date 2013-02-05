package com.twg.notesapp;

import java.util.ArrayList;

import com.twg.notesapp.AppConstants.IntentKeys;
import com.twg.notesapp.AppConstants.ModelTypes;
import com.twg.notesapp.AppConstants.Reads;
import com.twg.notesapp.model.NoteModel;
import com.twg.notesapp.service.NotesAppService;
import com.twg.notesapp.service.ServiceError;
import com.twg.notesapp.service.ServiceResultHandler;
import com.twg.notesapp.service.ServiceResultReceiver;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NotesActivity extends Activity implements ServiceResultHandler{

	private static final String TAG = "NotesActivity";
	
	private ListView notesView;

    private ArrayList<NoteModel> models;

    private ServiceResultReceiver resultReceiver;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_notes, menu);
        return true;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        this.notesView = (ListView) findViewById(R.id.notes_list);
        this.notesView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: onclick
            }
        });

        this.resultReceiver = new ServiceResultReceiver(new Handler());

        final Intent intent = this.marshallGetNotesIntent();
        this.startService(intent);
    }
    
    private Intent marshallGetNotesIntent() {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKeys.COMMAND, Reads.GET_NOTES);
        bundle.putString(IntentKeys.MODEL_TYPE, ModelTypes.NOTES);

        bundle.putParcelable(IntentKeys.RECEIVER, this.resultReceiver);

        final Intent intent = new Intent(this, NotesAppService.class);
        intent.putExtras(bundle);

        return intent;
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
    
    public void onStartUp() {
        Log.i(TAG, "Startup");
    }

    public void onSuccess(Bundle data) {
        Log.i(TAG, "Success");
        final ArrayList<NoteModel> models = new ArrayList<NoteModel>();
        final ArrayList<ContentValues> modelsValues = data
                .getParcelableArrayList(Reads.GET_NOTES);
        for (ContentValues modelValues : modelsValues) {
            models.add(new NoteModel(modelValues));
        }

        this.models = models;
        this.refreshNotes();
    }

    public void onError(ServiceError error) {
        Log.i(TAG, "Error");
    }

    public void onComplete() {
        Log.i(TAG, "Complete");
    }

    private void refreshNotes() {
    	final NotesActivityAdapter adapter = new NotesActivityAdapter(this, this.models);
        this.notesView.setAdapter(adapter);
    }
}
