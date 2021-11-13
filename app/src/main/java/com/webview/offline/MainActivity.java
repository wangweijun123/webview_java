package com.webview.offline;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * 与H5通信,
 *
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "wangwiejun java ";
    public static final String LOCAL_URL = "file:///android_asset/my.html";
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true); // 启动js
        // 注入对象后，前端可以window.injectedObject.methodId
        webView.addJavascriptInterface(new JsObject(webView), "injectedObject");
        webView.loadUrl(LOCAL_URL);

    }
}