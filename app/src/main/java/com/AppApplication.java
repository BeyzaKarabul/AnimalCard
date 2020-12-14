package com;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;

import com.exception.LoggingExceptionHandler;
import com.helper.ConnectivityHelper;
import com.helper.GPSHelper;
import com.helper.LogHelper;
import com.listener.ConnectivityReceiverListener;
import com.manager.PreferenceManager;

import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class AppApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static AppApplication mInstance;

    private PreferenceManager prefManager;

    @Override
    public void onCreate() {
        super.onCreate();
        new LoggingExceptionHandler(this);
        mInstance = this;

        LogHelper.init(getApplicationContext());
        prefManager = new PreferenceManager(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        System.exit(0);
    }

    public static synchronized AppApplication getInstance() {
        return mInstance;
    }

    public void remove(String key) {
        prefManager.remove(key);
    }

    public String getString(String key) {
        return prefManager.getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return prefManager.getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        prefManager.putString(key, value);
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return prefManager.getStringSet(key, defaultValue);
    }

    public void putStringSet(String key, Set<String> value) {
        prefManager.putStringSet(key, value);
    }

    public void putBoolean(String key, Boolean value) {
        prefManager.putBoolean(key, value);
    }

    public Boolean getBoolean(String key) {
        return prefManager.getBoolean(key);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return prefManager.getBoolean(key, defaultValue);
    }

    public void putInt(String key, int value) {
        prefManager.putInt(key, value);
    }

    public int getInt(String key, int defaultValue) {
        return prefManager.getInt(key, defaultValue);
    }

    public void putImage(String key, Bitmap value) {
        prefManager.putImage(key, value);
    }

    public Bitmap getImage(String key) {
        return prefManager.getImage(key);
    }

    public void putLong(String key, long value) {
        prefManager.putLong(key, value);
    }

    public long getLong(String key, int defaultValue) {
        return prefManager.getLong(key, defaultValue);
    }

    public String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface networkInterface : all) {
                if (!networkInterface.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = networkInterface.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder macAddress = new StringBuilder();
                for (byte b : macBytes) {
                    if (Integer.toHexString(b & 0xFF).length() == 1) {
                        macAddress.append(0).append(Integer.toHexString(b & 0xFF)).append(":");
                    } else {
                        macAddress.append(Integer.toHexString(b & 0xFF)).append(":");
                    }
                }

                if (macAddress.length() > 0) {
                    macAddress.deleteCharAt(macAddress.length() - 1);
                }
                return macAddress.toString();
            }

        } catch (Exception ex) {
            LogHelper.writeLogData(ex);
            return "";
        }
        return "02:00:00:00:00:00";
    }

    public String convertTimeFormat(String date, String oldPattern, String newPattern) {
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat(oldPattern);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat(newPattern);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimeZone timeZone = calendar.getTimeZone();
        format.setTimeZone(timeZone);

        try {
            Date d = dateFormat.parse(date);
            if (d != null)
                return format.format(d);
        } catch (ParseException e) {
            return "";
        }
        return "";
    }

    public String getCurrentTime() {
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat(Constants.TIME_PATTERN);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String now = format.format(new Date());
        now = now.replace("GMT", "");
        return now;
    }

    public Location getCurrentLocation() {
        return GPSHelper.getInstance(this).getCurrentLocation();
    }

    public String getCurrentDate() {
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat(Constants.LONG_TIME_PATTERN);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String now = format.format(new Date());
        now = now.replace("GMT", "");
        return now;
    }

    public long dateDifference(String start, String end) {
        try {
            @SuppressLint("SimpleDateFormat")
            DateFormat format = new SimpleDateFormat(Constants.LONG_TIME_PATTERN);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = format.parse(start);
                endDate = format.parse(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //milliseconds
            long different = 0;
            if (endDate != null && startDate != null) {
                different = endDate.getTime() - startDate.getTime();
            }

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            return different;
        } catch (Exception e) {
            return 0;
        }
    }

    public String dateLastSeen(String start) {
        try {
            @SuppressLint("SimpleDateFormat")
            DateFormat format = new SimpleDateFormat(Constants.TIME_PATTERN);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = format.parse(start);
                endDate = format.parse(getCurrentTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //milliseconds
            long different = 0;
            if (endDate != null && startDate != null) {
                different = endDate.getTime() - startDate.getTime();
            }

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            if (different < minutesInMilli) {
                long diff = different / secondsInMilli;
                return diff + "sec";
            } else if (different < hoursInMilli) {
                long diff = different / minutesInMilli;
                return diff + "min";
            } else if (different < daysInMilli) {
                long diff = different / hoursInMilli;
                return diff + "hr";
            } else if (different < (daysInMilli * 30)) {
                long diff = different / daysInMilli;
                return diff + "d";
            } else {
                return start.replace(" ", "\n");
            }

        } catch (Exception e) {
            return "";
        }

    }

    public static ConnectivityReceiverListener connectivityReceiverListener;

    public void setConnectivityListener(ConnectivityReceiverListener listener) {
        connectivityReceiverListener = listener;
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean isConnected;
            int status = ConnectivityHelper.getConnectivityStatusString(context);
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                isConnected = status != ConnectivityHelper.NETWORK_STATUS_NOT_CONNECTED;

                if (connectivityReceiverListener != null) {
                    connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
                }
            }
        }
    };
}
