package com.example.chatappv2;

import java.util.List;

public class PostBody {
    private String userMessage;
    private List<MessageTuple> chatHistory;

    public PostBody(String userMessage, List<MessageTuple> chatHistory) {
        this.userMessage = userMessage;
        this.chatHistory = chatHistory;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public List<MessageTuple> getChatHistory() {
        return chatHistory;
    }
}
