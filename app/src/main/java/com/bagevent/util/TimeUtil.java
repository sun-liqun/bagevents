package com.bagevent.util;

import android.app.Activity;
import android.content.Context;

import com.bagevent.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ZWJ on 2017/11/22 0022.
 */

public class TimeUtil {

    private Context context;

    static String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static String getNewChatTime(long timesamp,Context context) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);

        String timeFormat = "M月d日 HH:mm";
        String yearTimeFormat = "yyyy年M月d日 HH:mm";
//        String am_pm="";
//        int hour=otherCalendar.get(Calendar.HOUR_OF_DAY);
//        if(hour>=0&&hour<6){
//            am_pm="凌晨";
//        }else if(hour>=6&&hour<12){
//            am_pm="早上";
//        }else if(hour==12){
//            am_pm="中午";
//        }else if(hour>12&&hour<18){
//            am_pm="下午";
//        }else if(hour>=18){
//            am_pm="晚上";
//        }
//        timeFormat="M月d日 "+ am_pm +"HH:mm";
//        yearTimeFormat="yyyy年M月d日 "+ am_pm +"HH:mm";

        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = context.getResources().getString(R.string.today1) + getHourAndMin(timesamp);
//                        result = "今天 " + getHourAndMin(timesamp);
                        break;
                    case 1:
                        result = context.getResources().getString(R.string.yesterday) + getHourAndMin(timesamp);
//                        result = "昨天" + getHourAndMin(timesamp);
                        //  result = "昨天 ";
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
//                            if(dayOfWeek!=1){//判断当前是不是星期日
                            //result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK)-1] + getHourAndMin(timesamp);
                            result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1];
//                            }else{
//                                result = getTime(timesamp,timeFormat);
//                            }
                        } else {
                            result = getTime(timesamp, timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timesamp, timeFormat);
                        break;
                }
            } else {
                result = getTime(timesamp, timeFormat);
            }
        } else {
            result = getYearTime(timesamp, yearTimeFormat);
        }
        return result;
    }

//    public static String getNewChatTime(long timesamp) {
//        String result = "";
//        Calendar todayCalendar = Calendar.getInstance();
//        Calendar otherCalendar = Calendar.getInstance();
//        otherCalendar.setTimeInMillis(timesamp);
//
//        String timeFormat = "M月d日 HH:mm";
//        String yearTimeFormat = "yyyy年M月d日 HH:mm";
////        String am_pm="";
////        int hour=otherCalendar.get(Calendar.HOUR_OF_DAY);
////        if(hour>=0&&hour<6){
////            am_pm="凌晨";
////        }else if(hour>=6&&hour<12){
////            am_pm="早上";
////        }else if(hour==12){
////            am_pm="中午";
////        }else if(hour>12&&hour<18){
////            am_pm="下午";
////        }else if(hour>=18){
////            am_pm="晚上";
////        }
////        timeFormat="M月d日 "+ am_pm +"HH:mm";
////        yearTimeFormat="yyyy年M月d日 "+ am_pm +"HH:mm";
//
//        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
//        if (yearTemp) {
//            int todayMonth = todayCalendar.get(Calendar.MONTH);
//            int otherMonth = otherCalendar.get(Calendar.MONTH);
//            if (todayMonth == otherMonth) {//表示是同一个月
//                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
//                switch (temp) {
//                    case 0:
////                        result = context.getResources().getString(R.string.today1) + getHourAndMin(timesamp);
//                        result = "今天 " + getHourAndMin(timesamp);
//                        break;
//                    case 1:
////                        result = context.getResources().getString(R.string.yesterday) + getHourAndMin(timesamp);
//                        result = "昨天" + getHourAndMin(timesamp);
//                        //  result = "昨天 ";
//                        break;
//                    case 2:
//                    case 3:
//                    case 4:
//                    case 5:
//                    case 6:
//                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
//                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
//                        if (dayOfMonth == todayOfMonth) {//表示是同一周
//                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
////                            if(dayOfWeek!=1){//判断当前是不是星期日
//                            //result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK)-1] + getHourAndMin(timesamp);
//                            result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1];
////                            }else{
////                                result = getTime(timesamp,timeFormat);
////                            }
//                        } else {
//                            result = getTime(timesamp, timeFormat);
//                        }
//                        break;
//                    default:
//                        result = getTime(timesamp, timeFormat);
//                        break;
//                }
//            } else {
//                result = getTime(timesamp, timeFormat);
//            }
//        } else {
//            result = getYearTime(timesamp, yearTimeFormat);
//        }
//        return result;
//    }
    /**
     * 获得两个消息的时间间隔
     *
     * @param timesamp
     * @return
     */

    public static String getTimeInterval(long timesamp, long lastTimeSamp,Context context) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        Calendar lastCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);
        lastCalendar.setTimeInMillis(lastTimeSamp);
        String timeFormat = "M月d日 HH:mm";
        String yearTimeFormat = "yyyy年M月d日 HH:mm";
        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == lastCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int lastMonth = lastCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (lastMonth == otherMonth) {//两个日期是否为同一个月
                if (lastMonth == todayMonth) {
                    int day = lastCalendar.get(Calendar.DATE) - todayCalendar.get(Calendar.DATE);//第二条消息与今天是否为同一天
                    if (day == 0) {
                        int temp = todayCalendar.get(Calendar.DATE) - lastCalendar.get(Calendar.DATE);
                        switch (temp) {
                            case 0:
                                long minuteInterval = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
                                if (minuteInterval > 5) {
                                    result = context.getResources().getString(R.string.today1) + getHourAndMin(lastTimeSamp);
                                } else {
                                    result = "";
                                }
                                break;
                            case 1:
                                long minYes = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
                                if (minYes > 5) {
                                    result = context.getResources().getString(R.string.yesterday)+ getHourAndMin(lastTimeSamp);
                                } else {
                                    result = "";
                                }
                                break;
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                int dayOfMonth = lastCalendar.get(Calendar.WEEK_OF_MONTH);
                                int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                                if (dayOfMonth == todayOfMonth) {//表示是同一周
                                    long minWeek = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
                                    if (minWeek > 5) {
                                        result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1] + getHourAndMin(lastTimeSamp);
                                    } else {
                                        result = "";
                                    }
                                } else {
                                    long min = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
                                    if (min > 5) {
                                        result = getTime(lastTimeSamp, timeFormat);
                                    } else {
                                        result = "";
                                    }
                                }
                                break;
                            default:
                                result = "";
                                break;
                        }
                    } else {
                        long min = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
                        if (min > 5) {
                            result = getTime(lastTimeSamp, timeFormat);
                        } else {
                            result = "";
                        }
                    }
                } else {
                    long min = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
                    if (min > 5) {
                        result = getTime(lastTimeSamp, timeFormat);
                    } else {
                        result = "";
                    }
                }
            } else {
                long min = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
                if (min > 5) {
                    result = getTime(lastTimeSamp, timeFormat);
                } else {
                    result = "";
                }
            }
        } else {
            result = getYearTime(lastTimeSamp, yearTimeFormat);
        }
        return result;
    }

//    public static String getTimeInterval(long timesamp, long lastTimeSamp) {
//        String result = "";
//        Calendar todayCalendar = Calendar.getInstance();
//        Calendar otherCalendar = Calendar.getInstance();
//        Calendar lastCalendar = Calendar.getInstance();
//        otherCalendar.setTimeInMillis(timesamp);
//        lastCalendar.setTimeInMillis(lastTimeSamp);
//        String timeFormat = "M月d日 HH:mm";
//        String yearTimeFormat = "yyyy年M月d日 HH:mm";
//        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == lastCalendar.get(Calendar.YEAR);
//        if (yearTemp) {
//            int todayMonth = todayCalendar.get(Calendar.MONTH);
//            int lastMonth = lastCalendar.get(Calendar.MONTH);
//            int otherMonth = otherCalendar.get(Calendar.MONTH);
//            if (lastMonth == otherMonth) {//两个日期是否为同一个月
//                if (lastMonth == todayMonth) {
//                    int day = lastCalendar.get(Calendar.DATE) - todayCalendar.get(Calendar.DATE);//第二条消息与今天是否为同一天
//                    if (day == 0) {
//                        int temp = todayCalendar.get(Calendar.DATE) - lastCalendar.get(Calendar.DATE);
//                        switch (temp) {
//                            case 0:
//                                long minuteInterval = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
//                                if (minuteInterval > 5) {
//                                    result = "今天 " + getHourAndMin(lastTimeSamp);
//                                } else {
//                                    result = "";
//                                }
//                                break;
//                            case 1:
//                                long minYes = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
//                                if (minYes > 5) {
//                                    result = "昨天 " + getHourAndMin(lastTimeSamp);
//                                } else {
//                                    result = "";
//                                }
//                                break;
//                            case 2:
//                            case 3:
//                            case 4:
//                            case 5:
//                            case 6:
//                                int dayOfMonth = lastCalendar.get(Calendar.WEEK_OF_MONTH);
//                                int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
//                                if (dayOfMonth == todayOfMonth) {//表示是同一周
//                                    long minWeek = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
//                                    if (minWeek > 5) {
//                                        result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1] + getHourAndMin(lastTimeSamp);
//                                    } else {
//                                        result = "";
//                                    }
//                                } else {
//                                    long min = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
//                                    if (min > 5) {
//                                        result = getTime(lastTimeSamp, timeFormat);
//                                    } else {
//                                        result = "";
//                                    }
//                                }
//                                break;
//                            default:
//                                result = "";
//                                break;
//                        }
//                    } else {
//                        long min = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
//                        if (min > 5) {
//                            result = getTime(lastTimeSamp, timeFormat);
//                        } else {
//                            result = "";
//                        }
//                    }
//                } else {
//                    long min = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
//                    if (min > 5) {
//                        result = getTime(lastTimeSamp, timeFormat);
//                    } else {
//                        result = "";
//                    }
//                }
//            } else {
//                long min = (lastCalendar.getTimeInMillis() - otherCalendar.getTimeInMillis()) / (60 * 1000);
//                if (min > 5) {
//                    result = getTime(lastTimeSamp, timeFormat);
//                } else {
//                    result = "";
//                }
//            }
//        } else {
//            result = getYearTime(lastTimeSamp, yearTimeFormat);
//        }
//        return result;
//    }

    public static String getCurrentTime(String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat, Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * 不同一周的显示时间格式
     *
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat, Locale.CHINA);
        return format.format(new Date(time));
    }

    public static String getHourAndMin(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     *
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time, String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat, Locale.CHINA);
        return format.format(new Date(time));
    }

    /**
     * 时间格式转时间戳
     */

    public static long timeStmp(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间格式转时间戳
     *
     * @param time
     * @return
     */
    public static String timedate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 动态圈的时间展示
     *
     * @param time
     * @return
     */
//    public static String getTimeStringFormat(String time) {
//
//        try {
//            String lastFormat = "yyyy-MM-dd HH:mm:ss";
//            SimpleDateFormat lastDataFormat = new SimpleDateFormat(lastFormat, Locale.CHINA);
//            long lastTimeMillis = lastDataFormat.parse(time).getTime();
//            Calendar lastCalendar = Calendar.getInstance();
//            lastCalendar.setTimeInMillis(lastTimeMillis);
//
//            Calendar toadyCalendar = Calendar.getInstance();
//            long toadyTimeMillis = toadyCalendar.getTimeInMillis();
//            long dTimeMillis = toadyTimeMillis - lastTimeMillis;
//
//            int lastYear = lastCalendar.get(Calendar.YEAR);
//            int lastMonth = lastCalendar.get(Calendar.MONTH) + 1;
//            int lastDay = lastCalendar.get(Calendar.DATE);
//
//            int todayYear = toadyCalendar.get(Calendar.YEAR);
//            int todayMonth = toadyCalendar.get(Calendar.MONTH) + 1;
//            int todayDay = toadyCalendar.get(Calendar.DATE);
//
//            int dYear = todayYear - lastYear;
//            int dMonth = todayMonth - lastMonth;
//            int dDay = todayDay - lastDay;
//
//            if (dTimeMillis < 3600000) {
//                //877886
//                //小于一小时
//                int retTime = (int) (dTimeMillis / 60000);
//                retTime = retTime <= 0 ? 1 : retTime;
//                return retTime + "分钟前";
//            } else if (dTimeMillis < 86400000) {
//                //小于一天,也就是今天
//                int retTime = (int) (dTimeMillis / 3600000);
//                retTime = retTime <= 0 ? 1 : retTime;
//                return retTime + "小时前";
//            } else if (dTimeMillis < 172800000) {
//                return "昨天";
//            } else if ((dYear == 0 && dMonth <= 1) || (dYear == 1 && todayMonth == 1 && lastMonth == 12)) {
//                //第一个条件是同年，且相隔时间在一个月内
//                //第二个条件是隔年，对于隔年，只能是去年12月与今年1月这种情况
//                int retDay = 0;
//                if (dYear == 0 && dMonth == 0) {
//                    //同年，同月
//                    retDay = dDay;
//                }
//
//                if (retDay <= 0) {
//                    //获取发布日期中，该月有多少天
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(lastYear, lastMonth, 0);
//                    int totalDays = calendar.get(Calendar.DAY_OF_MONTH);
//                    // 当前天数 + （发布日期月中的总天数-发布日期月中发布日，即等于距离今天的天数）
//                    retDay = todayDay + (totalDays - lastDay);
//                }
//                return retDay + "天前";
//            } else {
//                if (dYear <= 1) {
//                    if (dYear == 0) {
//                        //同年
//                        return dMonth + "月前";
//                    }
//
//                    //隔年
//                    if (todayMonth == 12 && lastMonth == 12) {
//                        //隔年，但同月，就作为满一年来计算
//                        return "1年前";
//                    }
//                    return Math.abs(12 - lastMonth + todayMonth) + "个月前";
//                }
//                return dYear + "年前";
//            }
//
//        } catch (Exception e) {
//
//        }
//
//        return "1小时前";
//    }

    public static String getTimeStringFormat(String time,Context context) {

        try {
            String lastFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat lastDataFormat = new SimpleDateFormat(lastFormat, Locale.CHINA);
            long lastTimeMillis = lastDataFormat.parse(time).getTime();
            Calendar lastCalendar = Calendar.getInstance();
            lastCalendar.setTimeInMillis(lastTimeMillis);

            Calendar toadyCalendar = Calendar.getInstance();
            long toadyTimeMillis = toadyCalendar.getTimeInMillis();
            long dTimeMillis = toadyTimeMillis - lastTimeMillis;

            int lastYear = lastCalendar.get(Calendar.YEAR);
            int lastMonth = lastCalendar.get(Calendar.MONTH) + 1;
            int lastDay = lastCalendar.get(Calendar.DATE);

            int todayYear = toadyCalendar.get(Calendar.YEAR);
            int todayMonth = toadyCalendar.get(Calendar.MONTH) + 1;
            int todayDay = toadyCalendar.get(Calendar.DATE);

            int dYear = todayYear - lastYear;
            int dMonth = todayMonth - lastMonth;
            int dDay = todayDay - lastDay;

            if (dTimeMillis < 3600000) {
                //877886
                //小于一小时
                int retTime = (int) (dTimeMillis / 60000);
                retTime = retTime <= 0 ? 1 : retTime;
                return retTime + context.getResources().getString(R.string.min_ago);
            } else if (dTimeMillis < 86400000) {
                //小于一天,也就是今天
                int retTime = (int) (dTimeMillis / 3600000);
                retTime = retTime <= 0 ? 1 : retTime;
                return retTime + context.getResources().getString(R.string.hour_ago);
            } else if (dTimeMillis < 172800000) {
                return context.getResources().getString(R.string.yesterday);
            } else if ((dYear == 0 && dMonth <= 1) || (dYear == 1 && todayMonth == 1 && lastMonth == 12)) {
                //第一个条件是同年，且相隔时间在一个月内
                //第二个条件是隔年，对于隔年，只能是去年12月与今年1月这种情况
                int retDay = 0;
                if (dYear == 0 && dMonth == 0) {
                    //同年，同月
                    retDay = dDay;
                }

                if (retDay <= 0) {
                    //获取发布日期中，该月有多少天
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(lastYear, lastMonth, 0);
                    int totalDays = calendar.get(Calendar.DAY_OF_MONTH);
                    // 当前天数 + （发布日期月中的总天数-发布日期月中发布日，即等于距离今天的天数）
                    retDay = todayDay + (totalDays - lastDay);
                }
                return retDay + context.getResources().getString(R.string.day_ago);
            } else {
                if (dYear <= 1) {
                    if (dYear == 0) {
                        //同年
                        return dMonth + context.getResources().getString(R.string.months_ago);
                    }

                    //隔年
                    if (todayMonth == 12 && lastMonth == 12) {
                        //隔年，但同月，就作为满一年来计算
                        return context.getResources().getString(R.string.one_year_ago);
                    }
                    return Math.abs(12 - lastMonth + todayMonth) + context.getResources().getString(R.string.months_ago);
                }
                return dYear + context.getResources().getString(R.string.before_the_new_year);
            }

        } catch (Exception e) {

        }

        return context.getResources().getString(R.string.one_hour_ago);
    }

}