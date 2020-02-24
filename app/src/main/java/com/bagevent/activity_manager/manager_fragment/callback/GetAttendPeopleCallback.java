package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.AttendPeople;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zwj on 2016/6/6.
 */
public abstract class GetAttendPeopleCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                .create();
     //   AttendPeople people = new Gson().fromJson(string,AttendPeople.class);
//        Log.e("GetAttendPeopleCallback",string);
        return string;
    }


}
