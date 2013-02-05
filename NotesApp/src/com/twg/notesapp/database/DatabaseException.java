package com.twg.notesapp.database;

import android.database.SQLException;

public class DatabaseException extends Exception {

	private static final long serialVersionUID = 3755562758094105079L;

	private final SQLException sqlException;
    
    public DatabaseException() {
        this(null, null);
    }
    
    public DatabaseException(final String message) {
        this(message, null);
    }
    
    public DatabaseException(final String message, final SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }
    
    public SQLException getSQLException() {
        return sqlException;
    }
}
