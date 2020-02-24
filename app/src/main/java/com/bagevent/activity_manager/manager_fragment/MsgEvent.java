package com.bagevent.activity_manager.manager_fragment;

import android.graphics.Bitmap;

/**
 * Created by zwj on 2016/7/13.
 */
public class MsgEvent {
    public String mMsg;
    public int pos;
    public String changeName;
    public Bitmap bitmap;
    public int pageNumber;
    public int pageCount;

    public MsgEvent(String msg) {
        this.mMsg = msg;
    }

    public MsgEvent(String msg, int pos) {
        this.mMsg = msg;
        this.pos = pos;
    }

    public MsgEvent(String msg, Bitmap bitmap) {
        this.mMsg = msg;
        this.bitmap = bitmap;
    }

    public MsgEvent(int pos, String change, String msg) {
        this.pos = pos;
        this.changeName = change;
        this.mMsg = msg;
    }

    public MsgEvent(int pageCount, int pageNumber, String msg) {
        this.pageCount = pageCount;
        this.pageNumber = pageNumber;
        this.mMsg = msg;
    }

}
