package com.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import androidx.preference.PreferenceManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LogHelper {

    private static SharedPreferences.Editor mEditor;
    private static boolean isFileCreated = false;
    private static int fileCreatedMonth = -1;

    public static void init(Context context) {
        SharedPreferences credentialsPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = credentialsPreferences.edit();
        isFileCreated = credentialsPreferences.getBoolean("LogFileCreated", false);
        fileCreatedMonth = credentialsPreferences.getInt("LogFileMonth", -1);
        mEditor.apply();
    }

    public static void writeLogData(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        writeLogData(sw.getBuffer().toString());
    }

    public static void writeLogData(String data) {
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/RFTT Error/");
            boolean isExist = path.exists();
            if (!isExist) {
                isExist = path.mkdirs();
            }

            if (!isExist) {
                return;
            }

            String log1 = "/Log.1.txt";
            String log2 = "/Log.2.txt";
            String log3 = "/Log.3.txt";

            File file1 = new File(path, log1);
            File file2 = new File(path, log2);
            File file3 = new File(path, log3);

            File file;
            if (!file1.exists()) {
                file = file1;
            } else {
                if (getCurrentMonth() % 4 == 0 && !isFileCreated) {
                    if (!file2.exists()) {
                        file = file2;
                    } else {
                        if (!file3.exists()) {
                            file = file3;
                        } else {
                            fileCreatedMonth = getCurrentMonth();
                            boolean isResult = file1.delete();
                            isResult = isResult && file2.renameTo(file1);
                            isResult = isResult && file3.renameTo(file2);
                            if (!isResult) {
                                return;
                            }
                            file = file3;
                        }
                    }
                } else {
                    if (file3.exists()) {
                        file = file3;
                    } else {
                        if (file2.exists()) {
                            file = file2;
                        } else {
                            file = file1;
                        }
                    }
                }
            }

            isExist = file.exists();
            if (!isExist) {
                fileCreatedMonth = getCurrentMonth();
                isExist = file.createNewFile();
            }

            setCreatedValue();

            if (!isExist) {
                return;
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(getCurrentTime() + "\n" + data + "\n\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTime() {
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimeZone timeZone = calendar.getTimeZone();
        format.setTimeZone(timeZone);
        return format.format(new Date());
    }

    private static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    private static void setCreatedValue() {
        isFileCreated = fileCreatedMonth == getCurrentMonth();
        mEditor.putBoolean("LogNewCreate", isFileCreated);
        mEditor.putInt("LogFileMonth", fileCreatedMonth);
        mEditor.apply();
    }

}

