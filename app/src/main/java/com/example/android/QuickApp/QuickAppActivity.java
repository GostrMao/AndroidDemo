package com.example.android.QuickApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.R;

import java.util.Arrays;

public class QuickAppActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    private String protocol;
    private String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickapp);


        findViewById(R.id.spinner).setOnKeyListener(this);
        findViewById(R.id.open_quickapp_bt).setOnClickListener(this);

//        findViewById(R.id.url_et)

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.open_quickapp_bt){
            //TODO list: 临时方获获取editText中内容 -> 随时监听文本框中内容变化
            //TODO list: 添加一个选择框，直接输入包名。生成url_data
            TextView dataEditText = findViewById(R.id.data_et);
            String uri_data = dataEditText.getText().toString();

            //TODO list:添加标志位控制日志输出
            Log.d("wltx",uri_data);
//            (EditText)findViewById(R.id.url_et)

            String url = getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri_data));
            QuickAppActivity.this.startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = view.getId();

        if (id == R.id.spinner){
            protocol = adapterView.getItemAtPosition(i).toString();
        }
    }

    private String getUrl(){
        String[] protocols = getResources().getStringArray(R.array.protocol);

        if(protocol == protocols[0]) {
            return "http://hapjs.org/app/" + packageName;
        } else if (protocol == protocols[1]) {
            return "https://hapjs.org/app/" + packageName;
        } else if (protocol == protocols[2]) {
            return "hap://app/" + packageName;
        } else if (protocol == protocols[3]) {
            return "hwfastapp://" + packageName;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }
}
