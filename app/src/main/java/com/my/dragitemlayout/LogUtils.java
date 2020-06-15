package com.my.dragitemlayout;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by hongliJiang on 2019/4/24 14:20
 * 描述：log工具类
 */
public class LogUtils {

    private static boolean canLog() {
        return BuildConfig.DEBUG;
    }


    private static String APP_TAG = "LogUtils";

    private static HashMap<String, String> sCachedTag = new HashMap<>();
    private static JsonFormatter sJsonFormatter = new DefaultFormatter();

    private LogUtils() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " cannot be instantiated");
    }

    public static void init(@NonNull String appTag, @NonNull JsonFormatter formatter) {
        APP_TAG = appTag;
        sJsonFormatter = formatter;
    }

    public static void i(String message) {
        Log.i(buildTag(APP_TAG), buildMessage(message));
    }

    public static void d(String message) {
        Log.d(buildTag(APP_TAG), buildMessage(message));
    }

    public static void w(String message) {
        Log.w(buildTag(APP_TAG), buildMessage(message));
    }

    public static void e(String message) {
        Log.e(buildTag(APP_TAG), buildMessage(message));
    }

    public static void v(String message) {
        Log.v(buildTag(APP_TAG), buildMessage(message));
    }

    public static void wtf(String message) {
        Log.wtf(buildTag(APP_TAG), buildMessage(message));
    }

    public static void json(String message) {
        Log.v(buildTag(APP_TAG), buildMessage(message));
    }

    public static void i(@NonNull String tag, String message) {
        Log.i(buildTag(tag), buildMessage(message));
    }

    public static void d(@NonNull String tag, String message) {
        Log.d(buildTag(tag), message);
    }

    public static void w(@NonNull String tag, String message) {
        Log.w(buildTag(tag), buildMessage(message));
    }

    public static void e(@NonNull String tag, String message) {
        Log.e(buildTag(tag), buildMessage(message));
    }

    public static void v(@NonNull String tag, String message) {
        Log.v(buildTag(tag), buildMessage(message));
    }

    public static void wtf(@NonNull String tag, String message) {
        Log.wtf(buildTag(tag), buildMessage(message));
    }

    public static void json(@NonNull String tag, String content) {
        Log.v(buildTag(tag), buildMessage(formatJson(content)));
    }

    private static String buildTag(@NonNull String tag) {
        String key = String.format(Locale.US, "%s@%s", tag, Thread.currentThread().getName());

        sCachedTag.put(key, String.format(Locale.US, "|%s_%s|",
                APP_TAG,
                tag
        ));

        return sCachedTag.get(key);
    }

    private static String buildMessage(String message) {
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();

        if (traceElements == null || traceElements.length < 4) {
            return message;
        }
        StackTraceElement traceElement = traceElements[4];

        return String.format(Locale.US, "%s.%s(%s:%d) %s",
                traceElement.getClassName().substring(traceElement.getClassName().lastIndexOf(".") + 1),
                traceElement.getMethodName(),
                traceElement.getFileName(),
                traceElement.getLineNumber(),
                message
        );
    }

    private static String formatJson(String content) {
        return String.format(Locale.US, "\n%s", sJsonFormatter.formatJson(content));
    }

    public interface JsonFormatter {
        String formatJson(String content);
    }

    private static class DefaultFormatter implements JsonFormatter {
        @Override
        public String formatJson(String content) {
            return content;
        }
    }
}
