package com.example.android.content_provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

public final class CaptureScreenUtils implements TContentObserver.Utils {
    public static TContentObserver images_contentObserver;
    public static ContentResolver contentResolver;
    public static Handler main_handler;
    public static final String tag = "wltx_contentProvider";

    static {
        //线程
        HandlerThread handlerThread = new HandlerThread(new String("uriobserver"));
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        images_contentObserver = new TContentObserver(handler);
        images_contentObserver.setCaptureScreenUtils(new CaptureScreenUtils());

        //主线程
        main_handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void getUriInfo(Uri uri) {
        String uri_path = uri.getPath();
        Log.d(tag, String.valueOf(uri));
        Log.d(tag, uri_path);

        Cursor cursor = contentResolver.query(uri, null, null, null, "date_added DESC");
        Log.d(tag, cursor.toString());
        if(cursor != null) {
            boolean isNext = cursor.moveToFirst();
            Log.d(tag, String.valueOf(isNext));
            while(isNext){
                Log.d(tag,String.valueOf(cursor));
                try{
                    int columIndex = cursor.getColumnIndex("_data");
                    String _data = cursor.getString(columIndex);
                    Log.d(tag,columIndex + ":   " + _data);

                    columIndex = cursor.getColumnIndex("relative_path");
                    String relativePathIndex = cursor.getString(columIndex);
                    Log.d(tag,columIndex + ":   " + relativePathIndex);

                    columIndex = cursor.getColumnIndex("date_added");
                    Long date_added = cursor.getLong(columIndex);
                    Log.d(tag,columIndex + ":   " + date_added);

                }catch(CursorIndexOutOfBoundsException e){
                    Log.d(tag,"CursorIndexOutOfBoundsException(\"Index 0 requested, with a size of 0\")");
                }finally {
                    isNext = cursor.moveToNext();
                }
            }
        }else {
            Log.d(tag,"cursor is null");
        };
    }
}
