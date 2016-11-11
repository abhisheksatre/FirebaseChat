package com.mobond.chat;

public class Profile {

    public String message;
    public String sid;
    public String gid;
    public String time;
    public String status;

    public Profile(){

    }


    public Profile(String message, String sid, String gid, String time, String status) {
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



    public void setMessage(String message) {
        this.message = message;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
