package com.bagevent.new_home.new_interface.new_view;

import com.bagevent.login.model.UserInfo;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/12
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public interface WeChatLoginView {

    String getUnionId();

    void showFailedErr(String msg);

    void toMainActivity(UserInfo response);
}
