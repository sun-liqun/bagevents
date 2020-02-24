package com.bagevent.home.home_interface;

import android.content.Context;

/**
 * Created by zwj on 2016/5/31.
 */
public interface GetExercisingIn {
    void getExercising(Context mContext,String userId, int page,int pageSize,String status,int showType,OnGetExercisingIn getExercisingIn);
}
