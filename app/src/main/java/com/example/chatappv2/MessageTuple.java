package com.example.chatappv2;

public class MessageTuple {
    private String User;
    private String Llama;

    public MessageTuple(String user, String llama) {
        this.User = user;
        Llama = llama;
    }

    public String getUser() {
        return User;
    }

    public String getLlama() {
        return Llama;
    }

    public String getMessageTuple() {
        return "User: " + User + "\nLlama: " + Llama;
    }
}
