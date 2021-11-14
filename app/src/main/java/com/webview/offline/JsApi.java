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
        return null;
    }

    public interface CallBack {
        void onCallBack(String msg);
    }

}
