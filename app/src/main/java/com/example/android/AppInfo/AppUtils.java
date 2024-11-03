package com.example.android.AppInfo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class AppUtils {
    public static boolean disablePackage(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.setApplicationEnabledSetting(
                    packageName,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER,
                    0
            );
            Log.d("AppUtils", "Package " + packageName + " disabled successfully.");
            return true;
        } catch (Exception e) {
            Log.e("AppUtils", "Failed to disable package: " + packageName, e);
            return false;
        }
    }

    public static boolean enablePackage(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.setApplicationEnabledSetting(
                    packageName,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    0
            );
            Log.d("AppUtils", "Package " + packageName + " enabled successfully.");
            return true;
        } catch (Exception e) {
            Log.e("AppUtils", "Failed to enable package: " + packageName, e);
            return false;
        }
    }
}
