package com.varmtech.android.viewmodel.engine.numbers;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.varmtech.android.AppApplication;
import com.varmtech.android.model.ThreadModel;

public class Odd extends Thread {
    private static final String TAG = Odd.class.getName();
    private MutableLiveData<ThreadModel> mutableLiveData;
    private final Object lock = AppApplication.appApplication;
    private boolean running = true;

    public Odd(MutableLiveData<ThreadModel> mutableLiveData) {
        this.mutableLiveData = mutableLiveData;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 1; i <= 100; i++) {
            if (!running) {
                break;
            }
            if (i % 2 == 0) {
                synchronized (lock) {
                    // notify data to view
                    mutableLiveData.postValue(new ThreadModel(Odd.class.getSimpleName(), i));
                    try {
                        Thread.sleep(100);
                        lock.notify();
                        lock.wait();
                    } catch (Exception e) {
                        Log.i(TAG, "run: " + e.getMessage());
                    }
                }
            }
        }
    }
}