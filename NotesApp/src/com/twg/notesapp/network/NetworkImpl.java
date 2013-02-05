package com.twg.notesapp.network;

import java.util.ArrayList;

import android.content.ContentValues;

/* THIS IS A MOCK NETWORK IMPLEMENTATION.
 * A REAL PROGRAM WOULD CONNECT TO A REAL WEB API
 * THIS IS FOR DEMO ONLY.
 * 
 */
public class NetworkImpl implements Network {

	@Override
	public ArrayList<ContentValues> getCollection(String urlFragment,
			ContentValues params) {
		final ArrayList<ContentValues> results = new ArrayList<ContentValues>();
		
		if (urlFragment.equals("notes/list")) {
			for (int i = 0; i < 15; i++) {
				ContentValues note = new ContentValues();
				note.put("id", i);
				note.put("title", "note title " + i);
				note.put("body", "note body " + i);
				results.add(note);
			}
		} else if(urlFragment.equals("reminders/list")) {
			for (int i = 0; i < 15; i++) {
				ContentValues reminder = new ContentValues();
				reminder.put("title", "reminder title " + i);
				reminder.put("body", "reminder body " + i);
				results.add(reminder);
			}
		}
		
		return results;
	}
}
