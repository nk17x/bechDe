package com.example.bechde;

public class MessageHelperClass {
    public MessageHelperClass(String myname, String mymessage) {
        this.myname = myname;
        this.mymessage = mymessage;
    }

    public MessageHelperClass() {
    }

    String myname,mymessage;

    @Override
    public String toString() {
        return "MessageHelperClass{" +
                "myname='" + myname + '\'' +
                ", mymessage='" + mymessage + '\'' +
                '}';
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public String getMymessage() {
        return mymessage;
    }

    public void setMymessage(String mymessage) {
        this.mymessage = mymessage;
    }
}
