package com.example.android.quickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.R;

import java.util.Arrays;
import java.util.Objects;

public class QuickAppActivity extends AppCompatActivity implements View.OnClickListener{
    private String  protocol = "HTTP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickapp);


        Spinner spinner = findViewById(R.id.spinner);
        spinner.setSelection(Adapter.NO_SELECTION, true); //Add this line before setting listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                protocol = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                protocol = "HTTP";
            }
        });
        findViewById(R.id.open_quickapp_bt).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.open_quickapp_bt){
            //TODO list: 临时方获获取editText中内容 -> 随时监听文本框中内容变化
            //TODO list: 添加一个选择框，直接输入包名。生成url_data
            TextView dataEditText = findViewById(R.id.package_et);
            String packageName = dataEditText.getText().toString();
            String uri_data = getUrl(packageName);

            TextView uri_tv = findViewById(R.id.uri_tv);
            if(uri_data == null){
                uri_tv.setText("null");
                return;
            }
            uri_tv.setText(uri_data);
            //TODO list:添加标志位控制日志输出
            Log.d("wltx",uri_data);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri_data));
            QuickAppActivity.this.startActivity(intent);
        }
    }

    private String getUrl(String packageName){
        String[] protocols = getResources().getStringArray(R.array.protocol);

        Log.d("wltx",protocol);
        Log.d("wltx", Arrays.toString(protocols));
        if(packageName == null){
            return null;
        }
        if(Objects.equals(protocol, protocols[0])) {
            return "http://hapjs.org/app/" + packageName;
        } else if (Objects.equals(protocol, protocols[1])) {
            return "https://hapjs.org/app/" + packageName;
        } else if (Objects.equals(protocol, protocols[2])) {
            return "hap://app/" + packageName;
        } else if (Objects.equals(protocol, protocols[3])) {
            return "hwfastapp://" + packageName;
        }
        return null;
    }

}
