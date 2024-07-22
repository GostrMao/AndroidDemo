package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.QuickApp.QuickAppActivity;
import com.example.android.location.LocationActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.location_bt).setOnClickListener(this);
        findViewById(R.id.quickapp_bt).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        Context context = getApplicationContext();
        if(id == R.id.location_bt){ //位置信息模块
            startActivity(new Intent(context, LocationActivity.class));
        }else if(id == R.id.quickapp_bt){
            startActivity(new Intent(context, QuickAppActivity.class));
        }else{
            Toast.makeText(context,"你点击了哪里？？？",Toast.LENGTH_SHORT).show();
        }
    }
}