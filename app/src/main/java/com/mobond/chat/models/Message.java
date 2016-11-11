package com.mobond.chat.models;

public class Message {

    public String message;
    public String sid;
    public String gid;
    public String time;
    public String status;

    public Message(){

    }

    public Message(String message, String sid, String gid, String time, String status) {
        this.message = message;
        this.sid = sid;
        this.gid = gid;
        this.time = time;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getSid() {
        return sid;
    }

    public String getGid() {
        return gid;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
