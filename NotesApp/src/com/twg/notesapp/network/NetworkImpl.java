package com.twg.notesapp.network;

import java.util.ArrayList;

import android.content.ContentValues;

public class NetworkImpl implements Network {

	@Override
	public ArrayList<ContentValues> getCollection(String urlFragment,
			ContentValues params) {
		final ArrayList<ContentValues> notes = new ArrayList<ContentValues>();
		
		ContentValues note1 = new ContentValues();
		note1.put("title", "title1");
		note1.put("body", "body1");
		
		notes.add(note1);
		return notes;
	}
}
