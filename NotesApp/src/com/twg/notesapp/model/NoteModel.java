package com.twg.notesapp.model;

import android.content.ContentValues;

public class NoteModel {
	
	private final ContentValues modelValues;
	
	private final int id;
	private final String title;
	private final String body;

	public NoteModel(ContentValues modelValues) {
		this.modelValues = modelValues;
		
		this.id = modelValues.getAsInteger("id");
		this.title = modelValues.getAsString("title");
		this.body = modelValues.getAsString("body");
	}

	public int getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public ContentValues getContentValues() {
		return this.modelValues;
	}
	
}
