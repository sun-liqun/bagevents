package com.bagevent.util;

import com.bagevent.R;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zwj on 2016/5/31.
 * <p/>
 * 比较时间
 */
public class CompareDate {
    public static Boolean compare(String startTime, String endTime) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                java.util.Locale.getDefault());
        try {
            Date strDate1 = sdf.parse(startTime);
            Date strDate2 = sdf.parse(endTime);
            Date curTime = new Date(System.currentTimeMillis());
            if (strDate1 != null && strDate2 != null) {
                if (curTime.equals(strDate1)) {
                    return true;
                } else if (curTime.before(strDate1)) {
                    return true;
                } else if (curTime.after(strDate1) && curTime.before(strDate2)) {
                    return true;
                } else if (curTime.after(strDate2)) {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean compareEndTime(String endTime) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                java.util.Locale.getDefault());
        try {
            Date strDate = sdf.parse(endTime);
            Date curTime = new Date(System.currentTimeMillis());
            if (strDate != null ) {
                if (curTime.equals(strDate)) {
                    return true;
                } else if (curTime.before(strDate)) {//当前时间在结束时间之前，显示
                    return true;
                } else if (curTime.after(strDate)) {//当前时间在结束时间之后，不显示
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String dateCompare(String startTime, String endTime) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                java.util.Locale.getDefault());
        try {
            Date strDate1 = sdf.parse(startTime);
            Date strDate2 = sdf.parse(endTime);
            Date curTime = new Date(System.currentTimeMillis());
            if (strDate1 != null && strDate2 != null) {
                if (curTime.equals(strDate1)) {
                    return R.string.start+"";
                } else if (curTime.before(strDate1)) {
                    return R.string.not_start+"";
                } else if (curTime.after(strDate1) && curTime.before(strDate2)) {
                    return R.string.start+"";
                } else if (curTime.after(strDate2)) {
                    return R.string.stop+"";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean compareStartAndEndTime(String startTime,String endTime) {
        String patterns = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(patterns, Locale.getDefault());
        try {
            Date strDate1 = sdf.parse(startTime);
            Date strDate2 = sdf.parse(endTime);
            if (strDate1 != null && strDate2 != null) {
                if (strDate1.before(strDate2)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String compareDiff(String startTime,String endTime){
        String patterns = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(patterns, Locale.getDefault());
        try {
            Date strDate1 = sdf.parse(startTime);
            Date strDate2 = sdf.parse(endTime);
            Date curTime = new Date(System.currentTimeMillis());
            if (strDate1 != null && strDate2 != null) {
                if (curTime.equals(strDate1) || (curTime.after(strDate1) && curTime.before(strDate2))) {
                    return "start";
                } else if (curTime.before(strDate1)) {
                    long diff = strDate1.getTime() - curTime.getTime();
                    long days = diff / (1000 * 60 * 60 * 24);
                    return days + "";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 邮箱格式是否正确
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9a-z._%+-]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})");
        Matcher matcher = pattern.matcher(email);
        //    Log.e("s--->",email);
        return matcher.matches();
    }

    /** 判断字符串中是否包含数字 **/
    public static boolean isContainsNum(String input) {
        int len = input.length();
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        return simpleDateFormat.parse(dateString, position);
    }


}
