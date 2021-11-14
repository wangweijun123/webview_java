package com.webview.offline;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;

/**
 * 与H5通信,
 *
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "wangwiejun java ";
    public static final String LOCAL_URL = "file:///android_asset/my.html";
    WebView webView;

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startFrameAnimation();

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true); // 启动js
        // 注入对象后，前端可以window.injectedObject.methodId
        JsBridge jsBridge = new JsBridge(webView, new JsApi());
        webView.addJavascriptInterface(new JsObject(jsBridge), "injectedObject");
        webView.loadUrl(LOCAL_URL);

    }

    private void startFrameAnimation() {
        iv = (ImageView) findViewById(R.id.iv);
        iv.setImageResource(R.drawable.ring_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }
}