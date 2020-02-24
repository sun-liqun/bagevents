package com.bagevent.activity_manager.manager_fragment.data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zwj on 2016/7/4.
 */
public class SerializableMap implements Serializable {

    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
