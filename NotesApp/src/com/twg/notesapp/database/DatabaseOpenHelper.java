package com.twg.notesapp.database;

import java.util.ArrayList;

import com.twg.notesapp.R;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseOpenHelper(final Context context) {
        super(context, null, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final ArrayList<String> createTableStatements = this.getCreateTableStatements();
        try {
            for (String createStatement : createTableStatements) {
                db.execSQL(createStatement);
            }
        } catch (SQLException exception) {
            Log.e("Database Error", "Failed to create tables");
        }
    }

    private ArrayList<String> getCreateTableStatements() {
        ArrayList<String> statements = new ArrayList<String>();
        statements.add(this.getSQLString(R.string.notes_tbl));
        
        return statements;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private String getSQLString(int id) {
        return this.context.getResources().getString(id);
    }
}
