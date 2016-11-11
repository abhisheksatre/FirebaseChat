package com.mobond.chat;

public class Groupdata {

    public String gid;
    public String gname;
    public String time;

    public Groupdata(){

    }

    public Groupdata(String ctime, String name, String key) {
        this.time = ctime;
        this.gname = name;
        this.gid = key;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGname() {
        return gname;
    }

    public String getTime() {
        return time;
    }
}
