package com.varmtech.android.viewmodel.engine.files;

import android.util.Log;

import com.varmtech.android.AppApplication;
import com.varmtech.android.model.ThreadModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileManager {
    private static final String TAG = FileManager.class.getName();
    private final String fileName = "VarmTech.txt";
    private final String basePath = AppApplication.appApplication.getFilesDir().getPath();

    public boolean createFile() {
        File myFile = new File(basePath + "/" + fileName);
        if (!myFile.exists()) {
            try {
                return myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "createFile: " + e.getMessage());
            }
        }
        return false;
    }

    public void writeToFile(List<ThreadModel> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(basePath + "/" + fileName))) {
            List<ThreadModel> list = new LinkedList<>(data);
            for (ThreadModel model : list) {
                String value = model.getName().concat(" : ").concat(model.getValue() + "\n");
                writer.write(value);
            }
            writer.write("\n\n -------------- Work Completed --------------");
        } catch (IOException e) {
            Log.i(TAG, "writeToFile: " + e.getMessage());
        }
    }
}
