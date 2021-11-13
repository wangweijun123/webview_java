package com.webview.offline;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

public class JsBridge {
    final Handler mHandler = new Handler(Looper.getMainLooper());
    WebView webView;
    public JsBridge(WebView webView) {
        this.webView = webView;
    }
    public void post(String script) {
        Log.i(MainActivity.TAG, "java call js and script " + script);
        // 必须执行在 UI Thread (JsBrigd同一个线程)
        mHandler.post(() -> {
            webView.evaluateJavascript(script, null);
        });
    }
}
