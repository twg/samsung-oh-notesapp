package com.twg.notesapp;

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
}
