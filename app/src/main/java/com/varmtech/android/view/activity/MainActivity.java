package com.varmtech.android.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.varmtech.android.R;
import com.varmtech.android.view.fragment.ChatFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create chat fragment
        createFragment(R.id.fragmentContainer, new ChatFragment());
    }

    private void createFragment(int resId, Fragment fragment) {
        // check if main activity fragment manager not null
        if (getSupportFragmentManager() != null) {
            // check if fragment is added to container, we use this created fragment
            Fragment addedFragment = getSupportFragmentManager().findFragmentById(resId);
            if (addedFragment != null) {
                fragment = addedFragment;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(resId, fragment, fragment.getClass().getName())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment instanceof ChatFragment) {
            finish();
        }
    }
}
