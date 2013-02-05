package com.twg.notesapp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AppConstants {
	public static final class IntentKeys {
		public static final String COMMAND = "command";
        public static final String MODEL_TYPE = "modelType";
        public static final String MODEL_VALUES = "modelValues";
        public static final String RECEIVER = "receiver";
        public static final String PARAMS = "params";
	}
	
	public static final class Reads {
		public static final String GET_NOTES = "getNotes";
		public static final String GET_CACHED_NOTE = "getCachedNote";
	}
	
    public static final class ResultCode {
        public static final int LOADING = 0;
        public static final int SUCCESS = 1;
        public static final int ERROR = 2;
        public static final int COMPLETE = 3;
    }
    
    public static class ModelTypes {
        public static final String NOTES = "notes";
    }

    public static class ModelProperties {
    	public static final String ID = "id";
    	public static final String TITLE = "title";
    	public static final String BODY = "body";
    }

    public static class DatabaseTables {
    	public static final String NOTES_TABLE = "notes";
    }
    
    public static class ColumnTypes {
        public static final String LONG = "long";
        public static final String DOUBLE = "double";
        public static final String INTEGER = "integer";
        public static final String STRING = "string";
    }
    
    public static class ModelKeys {
    	public static final Map<String, String> NOTE_KEYS =
                Collections.unmodifiableMap(new HashMap<String, String>() {
 
                    private static final long serialVersionUID = 7187230086727228953L;

                    {
                        put(ModelProperties.ID, ColumnTypes.INTEGER);
                        put(ModelProperties.TITLE, ColumnTypes.STRING);
                        put(ModelProperties.BODY, ColumnTypes.STRING);
                    }
                });
    }
}
