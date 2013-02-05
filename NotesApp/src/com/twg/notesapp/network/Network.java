package com.twg.notesapp.network;

import java.util.ArrayList;

import android.content.ContentValues;

public interface Network {
	public ArrayList<ContentValues> getCollection(final String urlFragment,
			ContentValues params);
}
