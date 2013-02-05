package com.twg.notesapp.service;

import com.twg.notesapp.AppConstants.ResultCode;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class ServiceResultReceiver extends ResultReceiver {
	private ServiceResultHandler resultHandler;

    public ServiceResultReceiver(final Handler handler) {
        super(handler);
    }
    
    public void setResultHandler(final ServiceResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (this.resultHandler == null) {
            return;
        }

        switch (resultCode) {
            case ResultCode.LOADING:
                this.resultHandler.onStartUp();
                break;
            case ResultCode.SUCCESS:
                this.resultHandler.onSuccess(resultData);
                break;
            case ResultCode.ERROR:
                this.resultHandler.onError(null);
                break;
            case ResultCode.COMPLETE:
                this.resultHandler.onComplete();
                break;
            default:
                break;
        }
    }
}
