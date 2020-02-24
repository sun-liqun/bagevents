package com.bagevent.util;

import android.util.Log;

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
public class CompareRex {

    //    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

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
                    return R.string.start + "";
                } else if (curTime.before(strDate1)) {
                    return R.string.not_start + "";
                } else if (curTime.after(strDate1) && curTime.before(strDate2)) {
                    return R.string.start + "";
                } else if (curTime.after(strDate2)) {
                    return R.string.stop + "";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean compareStartAndEndTime(String startTime, String endTime) {
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


    public static String compareDiff(String startTime, String endTime) {
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

    public static String compareDay(long nowTime, long endTime) {
        Date date1 = new Date(endTime);
        Date date2 = new Date(nowTime);
        if (date1 != null && date2 != null) {
            if (date1.after(date2)) {
                long diff = date1.getTime() - date2.getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                return days + "";
            }
        }
        return "";
    }


    /**
     * 邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkEmail(String email) {
//        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
//        String regx = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //  Log.e("regex",Pattern.matches(REGEX_EMAIL,"会@qq.com") +"");
        //  Log.e("regex",Pattern.matches(REGEX_EMAIL,"781888926@qq.com") +"");
        return Pattern.matches(REGEX_EMAIL, email);
    }

    public static boolean isCellPhone(String cellphone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(cellphone);
        //    Log.e("s--->",email);
        return matcher.matches();
    }

    /**
     * 判断字符串中是否包含数字
     **/
    public static boolean isContainsNum(String input) {
        int len = input.length();
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNum(String text) {
        return Pattern.matches("\"^[0-9]*$\"", text);
    }

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return simpleDateFormat.parse(dateString, position);
    }

    public static String escapeCharacter(String text) {
        String str = text.replace("&amp;", "&");
        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");
        str = str.replace("&#39;", "'");
        str = str.replace("&#40;", "(");
        str = str.replace("&#41;", ")");
        str = str.replace("&quot;", "<");
        str = str.replace("&lt;", "\\");
        str = str.replace("&nbsp", " ");
        return str;
    }

    public static String replaceBlank(String text) {
        String dest = "";
        if (text != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(text);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
