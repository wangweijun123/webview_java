package com.webview.offline;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * 1 只提供了一个js接口给前端调用,通过json参数来控制业务 (统一分发),与前端通过js通信
 *  "{\"method\":\"doAsyncAction\",\"types\":[\"string\",\"function\",\"string\"],\"args\":[\"listenPage\",0,\"supply\"]}";
 *  method： 真正干活的对象的方法
 *  types: 参数类型，数组: 业务类型, function: 回调，就是一个字符串，callback的标识符,
 *  values: 参数值, (转化callback)
 *
 *  这些都有就可以反射调用
 */
public class JsObject {
    JsBridge jsBridge;
    public JsObject(JsBridge jsBridge) {
        this.jsBridge = jsBridge;
    }


    /**
     * 所有js调用本地方法的通道
     *
     * @param apiRequest 请求的方法对应的json映射
     * @return 返回执行结果
     */
    @JavascriptInterface
    public String invokeJavaMethod(String apiRequest) {
        Log.i(MainActivity.TAG, "apiRequest = " + apiRequest);
        // 全部转移到jsapi去做吧
//        apiRequest = "{\"method\":\"doAsyncAction\",\"types\":[\"string\",\"function\",\"string\"],\"args\":[\"listenPage\",0,\"supply\"]}";
//        apiRequest = "{\"method\":\"doAction\",\"types\":[\"string\",\"string\"],\"args\":[\"listenPage\",\"supply\"]}";
            //  "{\"code\": 200, \"result\": "{}"}"
        return jsBridge.process(apiRequest);
    }

    @JavascriptInterface
    public void post(String cmd, String param) {
        Log.i(MainActivity.TAG, "cmd = " + cmd + ", param = " + param);
        // javascript:dj.callback({"title":"cancel","id":"cancel","callbackname":"djapi_callback_1636628537832_2261"})
//            final String script = "javascript:dj.callback('title')";// 也可以直接传一个字符串
//            final String script = "javascript:dj.callback({'title':'cancel'})";
        String response = "{'title':'cancel'}";
        final String script = "javascript:" + "dj.callback" + "(" + response + ")";
        jsBridge.post(script);
    }

    @JavascriptInterface
    public int add_sync(int num1, int num2) {
        int re = num1 + num2;
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(MainActivity.TAG, "java re = " + re+ ", tid:"+Thread.currentThread().getId());
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
        // java 调用js, 一定是在ui thread, 第二前端告诉你对象以及方法
        String response = String.format("{'title':'cancel', 'callBackName': '%s', 're':'%d'}",
                callBackName, re);
        final String script = "javascript:" + "dj.callback" + "(" + response + ")";
        Log.i(MainActivity.TAG, "java call js and script " + script);
        jsBridge.post(script);
    }

    interface CallBack {
        void onCallBack(String msg);
    }
}
