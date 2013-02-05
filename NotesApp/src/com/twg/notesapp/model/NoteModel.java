package com.twg.notesapp.model;

import android.content.ContentValues;

public class NoteModel {
	
	private final String title;
	private final String body;

	public NoteModel(ContentValues modelValues) {
		this.title = modelValues.getAsString("title");
		this.body = modelValues.getAsString("body");
	}

	public String getTitle() {
		return this.title;
	}
	
	public String getBody() {
		return this.body;
	}
	
}
