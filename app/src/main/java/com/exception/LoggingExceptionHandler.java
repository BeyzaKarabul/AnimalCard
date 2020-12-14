package com.exception;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.helper.LogHelper;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggingExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = LoggingExceptionHandler.class.getSimpleName();
    private final Context context;
    private final Thread.UncaughtExceptionHandler rootHandler;

    public LoggingExceptionHandler(Context context) {
        this.context = context;
        this.rootHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable ex) {
        final StringWriter sw = new StringWriter();
        try {
            Log.d(TAG, "called for " + ex.getClass());
            PrintWriter pw = new PrintWriter(sw, true);
            ex.printStackTrace(pw);
            String exceptionDetail = sw.getBuffer().toString();
            System.out.println(exceptionDetail);
        } catch (Exception e) {
            LogHelper.writeLogData(e);
        }

        (new Thread() {
            public void run() {
                Looper.prepare();
                LogHelper.writeLogData(sw.getBuffer().toString());
                Toast.makeText(LoggingExceptionHandler.this.context, sw.getBuffer().toString(), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();

        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            LogHelper.writeLogData(e);
        }

        this.rootHandler.uncaughtException(thread, ex);
    }
}