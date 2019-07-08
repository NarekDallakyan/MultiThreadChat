package com.varmtech.android.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.varmtech.android.model.ThreadModel;
import com.varmtech.android.viewmodel.engine.files.FileManager;
import com.varmtech.android.viewmodel.engine.numbers.Even;
import com.varmtech.android.viewmodel.engine.numbers.Odd;

/**
 * Thread Communication view model
 */
public class ThreadCommunicationViewModel extends AndroidViewModel {
    private static final String TAG = ThreadCommunicationViewModel.class.getName();
    private MutableLiveData<ThreadModel> mutableLiveData = new MutableLiveData<>();
    private Thread threadOdd;
    private Thread threadEven;
    private Even even;
    private Odd odd;

    public ThreadCommunicationViewModel(@NonNull Application application) {
        super(application);
    }

    public void startChatWork(boolean isStartWork) {
        if (isStartWork) {
            // if thread not running start work and
            if (isNotRunning(threadOdd, threadOdd)) {
                even = new Even(mutableLiveData);
                odd = new Odd(mutableLiveData);
                threadOdd = new Thread(odd);
                threadEven = new Thread(even);
                threadEven.start();
                threadOdd.start();
                Log.i(TAG, "startChatWork: " + threadOdd.getName());
                Log.i(TAG, "startChatWork: " + threadEven.getName());
                FileManager fileManager = new FileManager();
                //fileManager.createFile();
            }
        } else {
            // stop work
            if (threadOdd != null) {
                Log.i(TAG, "startChatWork: " + threadOdd.getName());
                odd.setRunning(false);
                threadOdd = null;
            }

            if (threadEven != null) {
                Log.i(TAG, "stopChatWork: " + threadEven.getName());
                even.setRunning(false);
                threadEven = null;
            }
        }
    }

    private boolean isNotRunning(Thread threadOdd, Thread threadEven) {
        if (threadOdd != null || threadEven != null) {
            String stateEven = threadEven.getState().name();
            String stateOdd = threadEven.getState().name();
            return stateEven.toUpperCase().equals("TERMINATED") &&
                    stateOdd.toUpperCase().equals("TERMINATED");
        } else {
            return true;
        }
    }

    public MutableLiveData<ThreadModel> getMutableLiveData() {
        return mutableLiveData;
    }
}
