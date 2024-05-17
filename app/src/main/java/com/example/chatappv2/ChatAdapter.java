package com.example.chatappv2;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    List<MessageTuple> chatHistory;
    Context context;

    public ChatAdapter(Context context, List<MessageTuple> chatHistory) {
        this.context = context;
        this.chatHistory = chatHistory;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_tuple, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageTuple messageTuple = chatHistory.get(position);
        holder.tv_userInitial.setText("U");
        holder.tv_messageOutgoing.setText(messageTuple.getUser());
        holder.tv_messageIncoming.setText(messageTuple.getLlama());
    }

    public int getItemCount() {
        return chatHistory.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tv_userInitial, tv_messageOutgoing, tv_messageIncoming;

        public MessageViewHolder(@NonNull View msgView) {
            super(msgView);
            tv_userInitial = msgView.findViewById(R.id.tv_userInitial);
            tv_messageOutgoing = msgView.findViewById(R.id.tv_messageOutgoing);
            tv_messageIncoming = msgView.findViewById(R.id.tv_messageIncoming);
        }
    }
}
