package com.image.imageview.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import com.image.imageview.Constants;

public class L {
    static String className;//类名
    static String content;
    static String methodName;//方法名
    static int lineNumber;//行数


    public static boolean isDebuggable() {
        return true;
    }

    private static String createLog(String log) {
        if (log == null){
            log = "null";
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(content).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        content = sElements[1].getFileName();
        className = sElements[1].getClassName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className+":"+ Constants.TAG_ERROR, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className+":"+ Constants.TAG_ERROR, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className+":"+ Constants.TAG_ERROR, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className+":"+ Constants.TAG_ERROR, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className+":"+ Constants.TAG_ERROR, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className+":"+ Constants.TAG_ERROR, createLog(message));
    }

}
