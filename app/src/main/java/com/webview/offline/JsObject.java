package com.webview.offline;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JsObject {

    final Handler mHandler = new Handler(Looper.getMainLooper());
    WebView webView;
    public JsObject(WebView webView) {
        this.webView = webView;
    }

    @JavascriptInterface
    public void post(String cmd, String param) {
        Log.i(MainActivity.TAG, "cmd = " + cmd + ", param = " + param);
        // javascript:dj.callback({"title":"cancel","id":"cancel","callbackname":"djapi_callback_1636628537832_2261"})
//            final String script = "javascript:dj.callback('title')";// 也可以直接传一个字符串
//            final String script = "javascript:dj.callback({'title':'cancel'})";
        String response = "{'title':'cancel'}";
        final String script = "javascript:" + "dj.callback" + "(" + response + ")";
        mHandler.post(new Runnable() { // 必须执行在 UI Thread (JsBrigd同一个线程)
            @Override
            public void run() {
                // java 调用js, 一定是在ui thread, 第二前端告诉你对象以及方法
                // javascript:dj.callback(xxxx)
                Log.i(MainActivity.TAG, "java call js and script " + script);
                webView.evaluateJavascript(script, null);
            }
        });
    }

    @JavascriptInterface
    public int add_sync(int num1, int num2) {
        int re = num1 + num2;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(MainActivity.TAG, "java re = " + re);
        return re;
    }

    @JavascriptInterface
    public void add_async(int num1, int num2, String callBackName) {
        int re = num1 + num2;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(MainActivity.TAG, "java re = " + re);
        // 必须执行在 UI Thread (JsBrigd同一个线程)
        mHandler.post(() -> {
            // java 调用js, 一定是在ui thread, 第二前端告诉你对象以及方法
            String response = String.format("{'title':'cancel', 'callBackName': '%s', 're':'%d'}",
                    callBackName, re);
            final String script = "javascript:" + "dj.callback" + "(" + response + ")";
            Log.i(MainActivity.TAG, "java call js and script " + script);
            webView.evaluateJavascript(script, null);
        });
    }

    interface CallBack {
        void onCallBack(String msg);
    }
}
