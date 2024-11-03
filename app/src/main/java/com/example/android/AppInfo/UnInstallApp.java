package com.example.android.AppInfo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;

public class UnInstallApp extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_install_app);

        findViewById(R.id.openBrowser_bt).setOnClickListener(this);
        findViewById(R.id.closeBrowser_bt).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        Context context = getApplicationContext();
        if(id == R.id.openBrowser_bt){ //打开
            AppUtils.enablePackage(context,"com.huawei.browser");
        }else if(id == R.id.closeBrowser_bt){ // 关闭
            AppUtils.disablePackage(context,"com.huawei.browser");
        }else{
            Toast.makeText(context,"你点击了哪里？？？",Toast.LENGTH_SHORT).show();
        }
    }


}