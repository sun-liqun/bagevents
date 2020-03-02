package com.bagevent.eventbus;

import android.os.Bundle;

public class MessageEvent {
    private int action;
    private Bundle bundle;
    private Object object;
    public MessageEvent(int action){
        this.action = action;
    }

    public MessageEvent(int action,Bundle bundle){
        this.action = action;
        this.bundle = bundle;
    }
    public MessageEvent(int action,Object object){
        this.action = action;
        this.object = object;
    }

    public int getAction() {
        return action;
    }

    public void setValue(Object object){
        this.object = object;
    }

    public Object getValue(){
        return this.object;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}

