package com.example.android.screencap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.R;

import java.io.File;
import java.io.FileOutputStream;

public class ScreenCapActivity extends AppCompatActivity implements View.OnClickListener {
    private String tag = "wltx_screencap" ;
    private int number = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screencap);

        findViewById(R.id.screencap_bt_1).setOnClickListener(this);
        findViewById(R.id.screencap_bt_2).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        Context context = getApplicationContext();
        Bitmap bitmap = null;
        if(id == R.id.screencap_bt_1){ // 方式一: 全局截屏
            View v = getWindow().getDecorView();     // 获取DecorView
            bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);

            ImageView image = findViewById(R.id.imageView);
            image.setImageBitmap(bitmap);
        }else if(id == R.id.screencap_bt_2){// 方式二: 组件截屏
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        }else if(id == R.id.screencap_bt){
            startActivity(new Intent(context, ScreenCapActivity.class));
        }else{
            Toast.makeText(context,"你点击了哪里？？？",Toast.LENGTH_SHORT).show();
        }

        if (bitmap != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot" + number + ".png";

                File file = new File(filePath);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();

                Log.d(tag, "存储完成：" + filePath);
                number += 1 ;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}