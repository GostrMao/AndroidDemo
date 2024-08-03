package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.browser.BrowserActivity;
import com.example.android.content_provider.ContentProviderActivity;
import com.example.android.hybrid.QuickAppActivity;
import com.example.android.location.LocationActivity;
import com.example.android.screencap.ScreenCapActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.location_bt).setOnClickListener(this);
        findViewById(R.id.quickapp_bt).setOnClickListener(this);
        findViewById(R.id.screencap_bt).setOnClickListener(this);
        findViewById(R.id.browser_bt).setOnClickListener(this);
        findViewById(R.id.contentProvider_bt).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        Context context = getApplicationContext();
        if(id == R.id.location_bt){ //位置信息模块
            startActivity(new Intent(context, LocationActivity.class));
        }else if(id == R.id.quickapp_bt){
            startActivity(new Intent(context, QuickAppActivity.class));
        }else if(id == R.id.screencap_bt){
            startActivity(new Intent(context, ScreenCapActivity.class));
        }else if(id == R.id.browser_bt) {// web浏览器（基于WebView实现）
            startActivity(new Intent(context, BrowserActivity.class));
        }else if(id == R.id.contentProvider_bt) {// 内容提供程序
            startActivity(new Intent(context, ContentProviderActivity.class));
        }else{
            Toast.makeText(context,"你点击了哪里？？？",Toast.LENGTH_SHORT).show();
        }
    }
}