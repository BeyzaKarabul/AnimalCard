package com.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.AppApplication;
import com.R;
import com.google.android.material.snackbar.Snackbar;
import com.listener.ConnectivityReceiverListener;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements ConnectivityReceiverListener {

    protected final BaseActivity mBaseActivity;

    public static final int PERMISSIONS_REQUEST = 10;
    public static final int REQUEST_ENABLE_BT = 1;
    public static final int REQUEST_ENABLE_LOCATION = 2;

    public static String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private Snackbar sbConnectionStatus;
    public MediaPlayer mPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public BaseActivity() {
        mBaseActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
//        showConnectionError(isConnected);
    }

    public boolean EthernetControl() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showConnectionError(boolean isConnected) {
        String message = getString(R.string.check_network_connection);
        int color = Color.WHITE;
        if (sbConnectionStatus == null)
            sbConnectionStatus = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);

        if (!isConnected) {
            View sbView = sbConnectionStatus.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(color);
            sbConnectionStatus.show();
        } else {
            if (sbConnectionStatus.isShown())
                sbConnectionStatus.dismiss();
        }
    }

    public boolean checkPermission() {
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public boolean locationStatusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return !manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public final void IntentToActivity(final Class<?> activityClassName) {
        runOnUiThread(() -> {
            if (mBaseActivity.getClass() != activityClassName) {
                Intent intent = new Intent(mBaseActivity, activityClassName);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
    }

    public int getDisplayWidth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().width() - insets.left - insets.right;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }

    public final void ShowMessageSnackBar(final String string) {
        runOnUiThread(() -> Snackbar.make(findViewById(android.R.id.content),
                string, Snackbar.LENGTH_LONG).show());
    }

    public final void ShowAlert(final String title, final String message) {
        Dialog dialog = new Dialog(this, R.style.StyledDialog);
        dialog.setContentView(R.layout.popup_alert);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        TextView tvBody = dialog.findViewById(R.id.tvBody);
        tvBody.setText(message);
        Button btnPositive = dialog.findViewById(R.id.btnPositive);
        btnPositive.setText(getString(R.string.ok));

        Button btnNegative = dialog.findViewById(R.id.btnNegative);
        btnNegative.setVisibility(View.INVISIBLE);
        Button btnNeutral = dialog.findViewById(R.id.btnNeutral);
        btnNeutral.setVisibility(View.INVISIBLE);

        btnPositive.setOnClickListener(v -> dialog.dismiss());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int dialogWindowWidth = (int) (getDisplayWidth() * 0.9f);
            dialog.getWindow().setLayout(dialogWindowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();
    }

    public void goGpsSettings() {
        Dialog dialog = new Dialog(this, R.style.StyledDialog);
        dialog.setContentView(R.layout.popup_alert);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.settings);

        TextView tvBody = dialog.findViewById(R.id.tvBody);
        tvBody.setText(R.string.go_gps_settings);

        Button btnPositive = dialog.findViewById(R.id.btnPositive);
        btnPositive.setText(getString(R.string.settings));

        Button btnNegative = dialog.findViewById(R.id.btnNegative);
        btnNegative.setText(getString(R.string.cancel));

        Button btnNeutral = dialog.findViewById(R.id.btnNeutral);
        btnNeutral.setVisibility(View.INVISIBLE);

        btnPositive.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        });
        btnNegative.setOnClickListener(v -> dialog.dismiss());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int dialogWindowWidth = (int) (getDisplayWidth() * 0.9f);
            dialog.getWindow().setLayout(dialogWindowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();
    }
}
