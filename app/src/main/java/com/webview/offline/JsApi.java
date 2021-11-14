package com.webview.offline;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * 同步返回
 * 异步回调
 */
public class JsApi {
    // {"method":"doAsyncAction","types":["string","function","string"],"args":["listenPage",0,"supply"]}
    // {"method":"doAction","types":["string","string"],"args":["listenPage","supply"]}
    @JavascriptInterface
    public String doAction(String dataType, final String extra) {
        Log.i(MainActivity.TAG, "doAction dataType:"+dataType+", extra:"+extra);
        return "ok";
    }

    @JavascriptInterface
    public String doAsyncAction(String dataType, final String extra ,final CallBack jsCallback) {
        Log.i(MainActivity.TAG, "doAsyncAction dataType:"+dataType+", extra:"+extra);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(MainActivity.TAG, "jsCallback onCallBack ...");
                jsCallback.onCallBack("处理好了返回js");
            }
        }).start();

        return "ok";
    }

    public interface CallBack {
        void onCallBack(String msg);
    }

}
