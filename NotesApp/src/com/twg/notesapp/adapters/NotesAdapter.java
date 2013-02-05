package com.twg.notesapp.adapters;

import java.util.ArrayList;
import java.util.Map;

import com.twg.notesapp.AppConstants.ColumnTypes;
import com.twg.notesapp.AppConstants.DatabaseTables;
import com.twg.notesapp.AppConstants.ModelKeys;
import com.twg.notesapp.database.Database;
import com.twg.notesapp.database.DatabaseException;
import com.twg.notesapp.database.DeleteStatement;
import com.twg.notesapp.database.InsertStatement;
import com.twg.notesapp.network.Network;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

public class NotesAdapter {

	private final Database database;
	private final Network network;

	public NotesAdapter(final Database database, final Network network) {
		this.database = database;
		this.network = network;
	}

	public ArrayList<ContentValues> getNotes(final ContentValues params) {
		String urlFragment = "notes/list";
		ArrayList<ContentValues> results = this.network.getCollection(
				urlFragment, params);
		return results;
	}

	public void saveNotes(ArrayList<ContentValues> notes)
			throws DatabaseException {
		for (final ContentValues note : notes) {
			this.database.insert(new InsertStatement(
					DatabaseTables.NOTES_TABLE, note));
		}
		this.database.commit();
	}

	public void deleteAllNotes() throws DatabaseException {
		final DeleteStatement deleteAllNotesStmt = new DeleteStatement(
				DatabaseTables.NOTES_TABLE, null, null);
		this.database.delete(deleteAllNotesStmt);
		this.database.commit();
	}

	public ContentValues getNoteFromCache(int noteID) {
		final String query = SQLiteQueryBuilder.buildQueryString(false,
				DatabaseTables.NOTES_TABLE, ModelKeys.NOTE_KEYS.keySet()
						.toArray(new String[0]), "id = " + noteID, null, null,
				null, null);

		final Cursor cursor = this.database.query(query);
		cursor.moveToPosition(0);

		ContentValues contentValues = new ContentValues();
		for (final Map.Entry<String, String> entry : ModelKeys.NOTE_KEYS
				.entrySet()) {
			final String key = entry.getKey();
			final String type = entry.getValue();
			if (type == ColumnTypes.LONG) {
				contentValues.put(key,
						cursor.getLong(cursor.getColumnIndex(key)));
			} else if (type == ColumnTypes.DOUBLE) {
				contentValues.put(key,
						cursor.getDouble(cursor.getColumnIndex(key)));
			} else if (type == ColumnTypes.INTEGER) {
				contentValues.put(key,
						cursor.getInt(cursor.getColumnIndex(key)));
			} else if (type == ColumnTypes.STRING) {
				contentValues.put(key,
						cursor.getString(cursor.getColumnIndex(key)));
			}
		}

		return contentValues;
	}

}
