package com.twg.notesapp.database;

import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseImpl implements Database {

	private final DatabaseOpenHelper databaseOpenHelper;

    private final List<Runnable> commandQueue;

    private SQLiteDatabase database;

    public DatabaseImpl(DatabaseOpenHelper helper, List<Runnable> commandQueue) {
        this.databaseOpenHelper = helper;
        this.commandQueue = commandQueue;
    }

    public void open() throws DatabaseException {
        try {
            this.database = this.databaseOpenHelper.getWritableDatabase();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to open a writable database", sqlException);
        }
    }

    public void close() {
        this.databaseOpenHelper.close();
    }

    public void insert(final InsertStatement insertStatement) {
        this.commandQueue.add(new Runnable() {

            public void run() {
                DatabaseImpl.this.database.insertOrThrow(insertStatement.getTableName(), null,
                        insertStatement.getContentValues());
            }
        });
    }

    public void update(final UpdateStatement updateStatement) {
        this.commandQueue.add(new Runnable() {

            public void run() {
                DatabaseImpl.this.database.update(
                        updateStatement.getTableName(),
                        updateStatement.getContentValues(),
                        updateStatement.getWhereClause(),
                        updateStatement.getWhereArgs());
            }
        });
    }

    public void delete(final DeleteStatement deleteStatement) {
        this.commandQueue.add(new Runnable() {

            public void run() {
                DatabaseImpl.this.database.delete(deleteStatement.getTableName(),
                        deleteStatement.getWhereClause(), deleteStatement.getWhereArgs());
            }
        });
    }

    public Cursor query(String query) {
        return this.database.rawQuery(query, null);
    }
    
    public void commit() throws DatabaseException {
        this.database.beginTransaction();
        try {
            for (final Runnable command : this.commandQueue) {
                command.run();
            }
            this.database.setTransactionSuccessful();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Transaction failed", sqlException);
        } finally {
            this.database.endTransaction();
            this.commandQueue.clear();
        }
    }

}
