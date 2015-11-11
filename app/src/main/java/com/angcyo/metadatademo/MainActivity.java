package com.angcyo.metadatademo;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_DATA = "msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        init();
    }

    private void init() {
        TextView textView = (TextView) findViewById(R.id.text);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("app data:");
        stringBuilder.append(getAppMeta());
        stringBuilder.append("\n");

        stringBuilder.append("act data:");
        stringBuilder.append(getActMeta());
        stringBuilder.append("\n");

        stringBuilder.append("ser data:");
        stringBuilder.append(getSerMeta());
        stringBuilder.append("\n");

        stringBuilder.append("rec data:");
        stringBuilder.append(getRecMeta());
        stringBuilder.append("\n");

        textView.setText(stringBuilder.toString());
    }

    private String getAppMeta() {
        try {
            ApplicationInfo info = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            return getString(info.metaData.getInt(KEY_DATA));//如果是通过 resource 设置的值,需要通过id访问数据
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    private String getSerMeta() {
        try {
            ServiceInfo info = this.getPackageManager().getServiceInfo(new ComponentName(this, TestService.class), PackageManager.GET_META_DATA);
            return info.metaData.getString(KEY_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    private String getRecMeta() {
        try {
            ActivityInfo info = this.getPackageManager().getReceiverInfo(new ComponentName(this, TestReceiver.class), PackageManager.GET_META_DATA);
            return getString(info.metaData.getInt(KEY_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    private String getActMeta() {
        try {
            ActivityInfo info = this.getPackageManager().getActivityInfo(new ComponentName(this, MainActivity.class), PackageManager.GET_META_DATA);
            return info.metaData.getString(KEY_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
