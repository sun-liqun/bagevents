package com.bagevent.util;

import android.content.Context;
import com.bagevent.R;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/04/03
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class LocalManageUtil {
    private static final String TAG = "LocalManageUtil";

    public static Locale getSystemLocal(Context context){
        return SPUtil.getInstance(context).getSystemCurrentLocal();
    }

    public static String getSelectLanguage(Context context){
        switch (SPUtil.getInstance(context).getSelectLanguage()){
            case 0:
                return "";
            case 1:
                return "";
            default:
                return "";
        }
    }

    public static Locale getSetLanguageLocale(Context context){
        switch (SPUtil.getInstance(context).getSelectLanguage()){
            case 0:
                return getSystemLocal(context);
            case 1:
                return Locale.ENGLISH;
            default:
                    return getSystemLocal(context);
        }
    }

    public static void saveSelectLanguage(Context context,int select){
        SPUtil.getInstance(context).saveLanguage(select);
        setApplicationLanguage(context);
    }

    public static Context setLocal(Context context){
        return updateResources(context,getSetLanguageLocale(context));
    }

    public static Context updateResources(Context context,Locale locale){
        Locale.setDefault(locale);
        Resources resources=context.getResources();
        Configuration configuration=new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT>=17){
            configuration.setLocale(locale);
            context=context.createConfigurationContext(configuration);
        }else {
            configuration.locale=locale;
            resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        }
        return context;
    }

    public static void setApplicationLanguage(Context context){
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm=resources.getDisplayMetrics();
        Configuration config=resources.getConfiguration();
        Locale locale=getSetLanguageLocale(context);
        config.locale=locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config,dm);
    }

    public static void saveSystemCurrentLanguage(Context context){
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            locale=LocaleList.getDefault().get(0);
        }else {
            locale=Locale.getDefault();
        }
        Log.i(TAG,locale.getLanguage());
        SPUtil.getInstance(context).setSystemCurrentLocal(locale);
    }

    public static void onConfigurationChanged(Context context){
        saveSystemCurrentLanguage(context);
        setLocal(context);
        setApplicationLanguage(context);
    }
}
