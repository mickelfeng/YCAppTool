package com.yc.toolutils;

import android.text.TextUtils;

/**
 * <pre>
 *     @author yangchong
 *     GitHub : https://github.com/yangchong211/YCAppTool
 *     email  : yangchong211@163.com
 *     time  : 2016/09/23
 *     desc  : num格式转化工具类
 *     revise:
 * </pre>
 */
public final class AppNumberUtils {

    public static int convertToInt(String intStr, int defValue) {
        if (TextUtils.isEmpty(intStr)){
            return defValue;
        }
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static long convertToLong(String longStr, long defValue) {
        if (TextUtils.isEmpty(longStr)){
            return defValue;
        }
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static float convertToFloat(String fStr, float defValue) {
        if (TextUtils.isEmpty(fStr)){
            return defValue;
        }
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static double convertToDouble(String dStr, double defValue) {
        if (TextUtils.isEmpty(dStr)){
            return defValue;
        }
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }


    public static Integer convertToInteger(String intStr) {
        if (TextUtils.isEmpty(intStr)){
            return null;
        }
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Long convertToLong(String longStr) {
        if (TextUtils.isEmpty(longStr)){
            return null;
        }
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Float convertToFloat(String fStr) {
        if (TextUtils.isEmpty(fStr)){
            return null;
        }
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Double convertToDouble(String dStr) {
        if (TextUtils.isEmpty(dStr)){
            return null;
        }
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }


}
