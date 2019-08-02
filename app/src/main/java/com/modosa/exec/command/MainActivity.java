package com.modosa.exec.command;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        Objects.requireNonNull(appInfo);

        try {
            OutputStream outputStream = Runtime.getRuntime().exec("su").getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(appInfo.metaData.getString("CMD"));
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        finish();
    }
}
