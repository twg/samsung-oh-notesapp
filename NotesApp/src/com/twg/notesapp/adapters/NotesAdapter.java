package com.twg.notesapp.adapters;

import java.util.ArrayList;

import com.twg.notesapp.database.Database;
import com.twg.notesapp.network.Network;

import android.content.ContentValues;

public class NotesAdapter {
	
	private final Database database;
	private final Network network;
	
	public NotesAdapter(final Database database, final Network network) {
		this.database = database;
		this.network = network;
	}

	public ArrayList<ContentValues> getNotes(final ContentValues params) {
		String urlFragment = "/notes/list";
		ArrayList<ContentValues> results = this.network.getCollection(urlFragment, params);
		return results;
	}
	
}
