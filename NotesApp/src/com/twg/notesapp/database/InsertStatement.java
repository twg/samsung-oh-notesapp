package com.twg.notesapp.database;

import android.content.ContentValues;

public class InsertStatement {
	private final String tableName;
    private final ContentValues contentValues;
    
    public InsertStatement(final String tableName, final ContentValues contentValues) {
        this.tableName = tableName;
        this.contentValues = contentValues;
    }

    public String getTableName() {
        return tableName;
    }

    public ContentValues getContentValues() {
        return contentValues;
    }
}
