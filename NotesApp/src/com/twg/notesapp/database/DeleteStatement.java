package com.twg.notesapp.database;

public class DeleteStatement {
	private final String tableName;
    private final String whereClause;
    private final String[] whereArgs;

    public DeleteStatement(final String tableName, final String whereClause,   
            final String[] whereArgs) {
        this.tableName = tableName;
        this.whereClause = whereClause;
        this.whereArgs = whereArgs;
    }

    public String getTableName() {
        return tableName;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public String[] getWhereArgs() {
        return whereArgs;
    }
}
