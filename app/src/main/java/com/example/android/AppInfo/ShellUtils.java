package com.example.android.AppInfo;

import java.io.DataOutputStream;

public class ShellUtils {
    public static boolean enablePackage(String packageName) {
        try {
            Process process = Runtime.getRuntime().exec("su"); // 切换到 Root 权限
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("pm enable " + packageName + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

