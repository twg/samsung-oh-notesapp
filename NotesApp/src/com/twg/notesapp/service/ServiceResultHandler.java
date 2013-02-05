package com.twg.notesapp.service;

import android.os.Bundle;

public interface ServiceResultHandler {
	public void onStartUp();

    public void onSuccess(Bundle data);

    public void onError(ServiceError error);

    public void onComplete();
}
