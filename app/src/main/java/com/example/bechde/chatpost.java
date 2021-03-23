package com.example.bechde;

public class chatpost {
    String mymessage,myname;

    @Override
    public String toString() {
        return "chatpost{" +
                "mymessage='" + mymessage + '\'' +
                ", myname='" + myname + '\'' +
                '}';
    }

    public String getMymessage() {
        return mymessage;
    }

    public void setMymessage(String mymessage) {
        this.mymessage = mymessage;
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public chatpost() {
    }
}
