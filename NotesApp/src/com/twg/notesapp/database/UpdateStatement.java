package com.twg.notesapp.database;

import android.content.ContentValues;

public class UpdateStatement {
    private final String tableName;
    private final ContentValues contentValues;
    private final String whereClause;
    private final String[] whereArgs;

    public UpdateStatement(final String tableName, final ContentValues contentValues,
            final String whereClause,
            final String[] whereArgs) {
        this.tableName = tableName;
        this.contentValues = contentValues;
        this.whereClause = whereClause;
        this.whereArgs = whereArgs;
    }

    public String getTableName() {
        return tableName;
    }

    public ContentValues getContentValues() {
        return contentValues;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public String[] getWhereArgs() {
        return whereArgs;
    }
}
