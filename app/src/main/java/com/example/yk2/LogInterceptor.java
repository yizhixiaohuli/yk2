package com.example.yk2;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * date:2018/11/22
 * author:别的小朋友(别的小朋友)
 * function:
 */
public class LogInterceptor implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.e("HttpLogInfo",message);
    }
}
