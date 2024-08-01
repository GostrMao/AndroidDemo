package com.example.android.screencap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaActionSound;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import com.example.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class ScreenCapActivity extends AppCompatActivity implements View.OnClickListener {
    private String tag = "wltx_screencap" ;
    private final static int REQUEST_MEDIA_PROJECTION = 0;
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screencap);
        findViewById(R.id.screencap_bt_1).setOnClickListener(this);
        findViewById(R.id.screencap_bt_2).setOnClickListener(this);
        findViewById(R.id.screencap_bt_3).setOnClickListener(this);
        findViewById(R.id.screencap_bt_4).setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public int checkPermission(String permission, int pid, int uid) {
        return super.checkPermission(permission, pid, uid);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Context context = getApplicationContext();
        Bitmap bitmap = null;
        if(id == R.id.screencap_bt_1){ // 方式一: 全局截屏
            View v = getWindow().getDecorView();     // 获取DecorView
            bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            canvas.setBitmap(bitmap);
            v.draw(canvas);
        }else if(id == R.id.screencap_bt_2){// 方式二: 组件截屏
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            canvas.setBitmap(bitmap);
            view.draw(canvas);
        }else if(id == R.id.screencap_bt_3){// 方式三: MediaProjection截屏
            // 步骤一
            startService(new Intent(getApplicationContext(),CaptureScreenService.class));
            mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            Log.d(tag,String.valueOf(mediaProjectionManager != null));
            if (mediaProjectionManager != null) {
                startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
            }
        }else if(id == R.id.screencap_bt_4){// 方式四: 调用系统源码截屏

        }else{
            Toast.makeText(context,"你点击了哪里？？？",Toast.LENGTH_SHORT).show();
        }

        if (bitmap != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
                // 图片文件路径
                String name = String.valueOf(System.currentTimeMillis());
                String filePath = sdCardPath + File.separator + "screenshot" + name + ".png";

                File file = new File(filePath);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();

                Log.d(tag, "存储完成：" + filePath);

                ImageView image = findViewById(R.id.imageView);
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION && resultCode == RESULT_OK) {
//            CaptureScreenService.setMediaProject(mediaProjection); //将MediaProject传进service里，这个medieaProject应该为 null
//            CaptureScreenService.setMediaProjectionManager(mediaProjectionManager); //将mediaProjectionManager传进service里，这个manager你应该已经在`ServiceConnection`时实例化好了（）

            mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
//            Intent service = new Intent(this, CaptureScreenService.class);
//            service.putExtra("code", resultCode);
//            service.putExtra("data", data);
//            startForegroundService(service);
            saveImage();
        }

    }

    public void saveImage(){
        // Step 3: 创建 VirtualDisplay  start capture reader
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int screenDensity = metrics.densityDpi;
        ImageReader imageReader = ImageReader.newInstance(screenWidth, screenHeight, PixelFormat.RGBA_8888, 2);
        VirtualDisplay virtualDisplay = mediaProjection.createVirtualDisplay("ScreenCapture",
                screenWidth, screenHeight, screenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(), null, null);
        if (virtualDisplay == null) {
            return;
        }

        // Step 4: 截取屏幕内容
        Image image = imageReader.acquireLatestImage();
        try{
            int recycle = 0;
            while (image == null) {
                SystemClock.sleep(10);
                image = imageReader.acquireLatestImage();
                recycle ++;
                if(recycle >= 10) break;
            }

            // 处理 Image 对象，保存为文件或显示在 ImageView 中
            if (image != null) {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
                // 图片文件路径
                String name = String.valueOf(System.currentTimeMillis());
                String filePath = sdCardPath + File.separator + "screenshot" + name + ".png";
                Log.e(tag,"mediaProjection获取截图");

                int width = image.getWidth();
                int height = image.getHeight();
                final Image.Plane[] planes = image.getPlanes();
                final ByteBuffer buffer = planes[0].getBuffer();
                //每个像素的间距
                int pixelStride = planes[0].getPixelStride();
                //总的间距
                int rowStride = planes[0].getRowStride();
                int rowPadding = rowStride - pixelStride * width;
                Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(buffer);
                Bitmap bitmapImage = Bitmap.createBitmap(bitmap, 0, 0, width, height);
                image.close();

//                    saveJpeg(image,filePath);
//                    image.close();
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmapImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        virtualDisplay.release();
    }

    private void saveJpeg(Image image,String name) {
//        Image.Plane[] planes = image.getPlanes();
//        ByteBuffer buffer = planes[0].getBuffer();
//        int pixelStride = planes[0].getPixelStride();
//        int rowStride = planes[0].getRowStride();
//        int rowPadding = rowStride - pixelStride * mWidth;
//
//        Bitmap bitmap = Bitmap.createBitmap(mWidth + rowPadding / pixelStride, mHeight, Bitmap.Config.ARGB_8888);
//        bitmap.copyPixelsFromBuffer(buffer);
//        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//        ImageSaveUtil.saveBitmap2file(bitmap,getApplicationContext(),name);
    }

}