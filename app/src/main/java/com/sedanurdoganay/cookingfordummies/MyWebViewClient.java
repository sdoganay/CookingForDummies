package com.sedanurdoganay.cookingfordummies;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by bengisukose on 5/10/16.
 */
public class MyWebViewClient extends WebViewClient {

    public MyWebViewClient() {
        super();
        Log.d("sdfg","client");
        //start anything you need to
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        //Do something to the urls, views, etc.
    }
}
