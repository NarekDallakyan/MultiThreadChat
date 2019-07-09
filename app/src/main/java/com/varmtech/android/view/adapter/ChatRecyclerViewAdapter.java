package com.varmtech.android.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.varmtech.android.R;
import com.varmtech.android.model.ThreadModel;
import com.varmtech.android.viewmodel.engine.files.FileManager;

import java.util.ArrayList;
import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ODD = 1;
    private static final int VIEW_TYPE_EVEN = 2;
    private List<ThreadModel> messageList = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {// create even and odd view types
        int isEven = messageList.get(position).getValue();
        if (isEven % 2 == 0) {
            return VIEW_TYPE_EVEN;
        }
        return VIEW_TYPE_ODD;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        // inflate corresponding layout
        if (viewType == VIEW_TYPE_EVEN) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_recycler_item_even, parent, false);
        } else if (viewType == VIEW_TYPE_ODD) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_recycler_item_odd, parent, false);
        }
        assert view != null;
        return new ChatViewHolder(view);// if view inflated, create view holder object
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // get current model
        ThreadModel message = messageList.get(position);
        // display message
        showContent(holder, message);
    }

    private void showContent(RecyclerView.ViewHolder holder, ThreadModel message) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_EVEN:
                ((ChatViewHolder) holder).bind(message, true);
                break;
            case VIEW_TYPE_ODD:
                ((ChatViewHolder) holder).bind(message, false);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void add(ThreadModel threadModel) {
        if (messageList != null) {
            messageList.add(threadModel);
            notifyItemChanged(getItemCount(), null);
            // if threads completed, save message to file
            // file path -> /data/data/com.varmtech.android/files/VarmTech.txt
            if (messageList.size() == 100) {
                FileManager fileManager = new FileManager();
                fileManager.writeToFile(messageList);
            }
        }
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView even;
        TextView odd;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            even = itemView.findViewById(R.id.text_message_even);
            odd = itemView.findViewById(R.id.text_message_odd);
        }

        void bind(ThreadModel message, boolean isEven) {
            // set data to view
            if (isEven) {
                even.setText(message.getName().concat(" : ").concat(String.valueOf(message.getValue())));
            } else {
                odd.setText(message.getName().concat(" : ").concat(String.valueOf(message.getValue())));
            }
        }
    }
}
