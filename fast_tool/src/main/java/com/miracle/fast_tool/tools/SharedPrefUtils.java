package com.miracle.fast_tool.tools;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefUtils {

    private static SharedPreferences mSharedPreferences = ToolsKernel.getInstance.getContext().getSharedPreferences("shentu_sp_info", Context.MODE_PRIVATE);

    public static void saveData(String key, Object value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    public static <T> T getData(String key, T defaultValue) {
        if (defaultValue instanceof String) {
            return (T) mSharedPreferences.getString(key, (String) defaultValue);
        }
        if (defaultValue instanceof Boolean) {
            return (T) (Boolean) mSharedPreferences.getBoolean(key, (Boolean) defaultValue);
        }
        if (defaultValue instanceof Float) {
            return (T) (Float) mSharedPreferences.getFloat(key, (Float) defaultValue);
        }
        if (defaultValue instanceof Integer) {
            return (T) (Integer) mSharedPreferences.getInt(key, (Integer) defaultValue);
        }
        if (defaultValue instanceof Long) {
            return (T) (Long) mSharedPreferences.getLong(key, (Long) defaultValue);
        }
        return defaultValue;
    }

    public static void clearAll(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
