package com.example.android.content_provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public final class TContentObserver extends ContentObserver {
    public static final String tag = "wltx_contentProvider";
    CaptureScreenUtils captureScreenUtils;

    public interface Utils {
        void getUriInfo (Uri uri);
    }
    public TContentObserver(Handler arg2) {
        super(arg2);
    }

    public void setCaptureScreenUtils(CaptureScreenUtils captureScreenUtils){
        this.captureScreenUtils = captureScreenUtils;
    }

    @Override  // android.database.ContentObserver
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        if (uri == null) {
            return;
        }
        captureScreenUtils.getUriInfo(uri);
    }
}
