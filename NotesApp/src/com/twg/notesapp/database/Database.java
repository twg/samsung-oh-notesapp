package com.twg.notesapp.database;

import android.database.Cursor;

public interface Database {

    public void open() throws DatabaseException;

    public void close();
    
    public void insert(final InsertStatement insertStatement);
    
    public void update(final UpdateStatement updateStatement);
    
    public void delete(final DeleteStatement deleteStatement);
    
    public Cursor query(String query);
    
    public void commit() throws DatabaseException;
    
}
