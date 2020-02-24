package com.bagevent.util;

import android.app.Activity;
import android.content.Context;

import com.bagevent.R;

/**
 * Created by zwj on 2016/5/27.
 */
public class ErrCodeUtil {
    private static Context context;
    public ErrCodeUtil(Context context){
        this.context=context;
    }
    public static String ErrCode(int errCode) {
        if (errCode == 199) {
            return context.getResources().getString(R.string.verification_code_overdue);
        }
        if (errCode == 201) {
            return context.getResources().getString(R.string.empty_parameter);
        }
        if (errCode == 211) {
            return context.getResources().getString(R.string.user_already_exists);
        }
        if (errCode == 212) {
            return context.getResources().getString(R.string.user_already_exists);
        }
        if (errCode == 213) {
            return context.getResources().getString(R.string.error_username_password);
        }
        if (errCode == 214) {
            return context.getResources().getString(R.string.account_no_activation);
        }
        if (errCode == 215) {
            return context.getResources().getString(R.string.phone_err4);
        }
        if (errCode == 216) {
            return context.getResources().getString(R.string.no_same_pwd);
        }
        if (errCode == 207) {
            return context.getResources().getString(R.string.verification_code_have_error);
        }
        if (errCode == 221) {
            return context.getResources().getString(R.string.invalid);
        }
        if (errCode == 99) {
            return context.getResources().getString(R.string.handling_exceptions);
        }
        if (errCode == 101) {
            return context.getResources().getString(R.string.illegal);
        }
        if (errCode == 253 ) {
            return context.getResources().getString(R.string.no_organize);
        }
        if (errCode == 254 ) {
            return context.getResources().getString(R.string.illegal_sms);
        }

        if(errCode == 1001) {
            return context.getResources().getString(R.string.incorrect_state_parameters);
        }
        if(errCode == -100) {
            return context.getResources().getString(R.string.no_collection);
        }
        if(errCode == -1002) {
            return context.getResources().getString(R.string.error_collection_type);
        }
        if(errCode == -1) {
            return context.getResources().getString(R.string.no_collection_user);
        }
        if(errCode == -1003) {
            return context.getResources().getString(R.string.no_in_collection_time);
        }
        if(errCode == -1004) {
            return context.getResources().getString(R.string.no_enter);
        }
        if(errCode == 301) {
            return context.getResources().getString(R.string.repeater_enter);
        }
        if(errCode == 302) {
            return context.getResources().getString(R.string.repeater_enter);
        }
        if(errCode == 303) {
            return context.getResources().getString(R.string.please_enter_correct);
        }
        return null;
    }
}
