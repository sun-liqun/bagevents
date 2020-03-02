package com.bagevent.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zwj on 2016/5/30.
 */
public class IsPhoneNum {
    public static boolean isPhone(String num) {
        String phoneRule = "^((13[0-9])|(14[5,7])|(15[^4])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(phoneRule);
        Matcher m = p.matcher(num);
        return m.matches();
    }
}
