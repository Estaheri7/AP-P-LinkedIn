package com.example.server.models;

public class Connection {
    private int id;
    private String sender;
    private String receiver;
    private boolean commited;

    public Connection(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    public Connection(int id ,String sender, String receiver , boolean commited) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.commited = commited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isCommited() {
        return commited;
    }

    public void setCommited(boolean commited) {
        this.commited = commited;
    }
}
