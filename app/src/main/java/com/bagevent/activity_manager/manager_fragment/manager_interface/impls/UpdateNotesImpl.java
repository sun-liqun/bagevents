package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.UpdateNotesInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnEditNotesListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by WenJie on 2017/11/2.
 */

public class UpdateNotesImpl implements UpdateNotesInterface {
    @Override
    public void editAttendeeNotes(Context mContext, int eventId, int attnedId, String notes, final OnEditNotesListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.UPDATE_NOTES + eventId)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .addParams("attendeeId",attnedId+"")
                .addParams("notes",notes)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.showEditNotesSuccess(response.getRespObject());
                        }else {
                            listener.showEditNotesFailed(response.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void editOrderNotes(Context mContext, int eventId, String orderNumber, String notes, final OnEditNotesListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.UPDATE_NOTES + eventId)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .addParams("orderNumber",orderNumber)
                .addParams("notes",notes)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.showEditNotesSuccess(response.getRespObject());
                        }else {
                            listener.showEditNotesFailed(response.getRespObject());
                        }
                    }
                });
    }
}
