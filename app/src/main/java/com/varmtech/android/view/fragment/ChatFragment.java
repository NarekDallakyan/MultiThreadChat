package com.varmtech.android.view.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.varmtech.android.R;
import com.varmtech.android.model.ThreadModel;
import com.varmtech.android.view.adapter.ChatRecyclerViewAdapter;
import com.varmtech.android.viewmodel.ThreadCommunicationViewModel;
import com.varmtech.android.viewmodel.engine.files.FileManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ChatFragment.class.getName();
    public static final Object lock = new Object();
    // view
    private View view;
    private Button start, stop;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ChatRecyclerViewAdapter adapter;
    private ThreadCommunicationViewModel viewModel;

    // object
    private Context context;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        context = getContext();
        initViewModel();
        initViews();
        initChatAdapter();
        listenReceivedNumber();
        return view;
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ThreadCommunicationViewModel.class);
    }

    private void initChatAdapter() {
        gridLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ChatRecyclerViewAdapter(context);
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        recyclerView = view.findViewById(R.id.chatAdapterID);
        start = view.findViewById(R.id.button);
        stop = view.findViewById(R.id.button2);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                viewModel.startChatWork(true);
                break;
            case R.id.button2:
                viewModel.startChatWork(false);
                break;

        }
    }

    private void listenReceivedNumber() {
        Observer<ThreadModel> observer = new Observer<ThreadModel>() {
            @Override
            public void onChanged(ThreadModel threadModel) {
                Log.i(TAG, "onChanged: " + threadModel);
                if(adapter !=null)
                    adapter.add(threadModel);
            }
        };
        viewModel.getMutableLiveData().observe(this, observer);
    }
}
