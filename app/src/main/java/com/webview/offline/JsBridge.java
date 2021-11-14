package com.webview.offline;

import android.os.Handler;
import android.os.Looper;
import android.telecom.Call;
import android.util.Log;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;

import static java.lang.System.out;


public class JsBridge {
    static String RETURN_RESULT_FORMAT = "{\"code\": %d, \"result\": %s}";
    final Handler mHandler = new Handler(Looper.getMainLooper());
    WebView webView;

    JsApi jsApi;
    public JsBridge(WebView webView, JsApi jsApi) {
        this.webView = webView;
        this.jsApi = jsApi;
    }

    public String process(String jsonStr) {
        try { // {"method":"putData","types":["string","string"],"args":["title","{\"show\":0}"]}
            JSONObject callJson = new JSONObject(jsonStr);
            String methodName = callJson.getString("method");
            JSONArray argsTypes = callJson.getJSONArray("types");
            JSONArray argsVals = callJson.getJSONArray("args");
            int len = argsTypes.length();
            Object[] values = new Object[len];
            Class[] types = new Class[len];
            Log.i(MainActivity.TAG, "methodName " + methodName);
            for (int i = 0; i <argsTypes.length(); i++) {
                String type = argsTypes.getString(i);
                if ("string".equals(type)) {
                    types[i] = String.class;
                    values[i] = argsVals.getString(i);
                } else if ("function".equals(type)){
                    final String callbackName = argsVals.getString(i);
                    types[i] = JsApi.CallBack.class;
                    values[i] = new JsApi.CallBack() {
                        @Override
                        public void onCallBack(String msg) {
                            Log.i(MainActivity.TAG, "可以给js返回了哈 msg = " + msg + ", callbackName="+callbackName);

                            String response = String.format("{'title':'%s', 'callBackName': '%s', 're':'%d'}",
                                    msg, callbackName, 100);
                            // javascript:dj.callback({"title":"cancel","id":"cancel","callbackname":"djapi_callback_1636628537832_2261"})
                            final String script = "javascript:" + "dj.callback" + "(" + response + ")";

                            mHandler.post(() -> {
                                webView.evaluateJavascript(script, null);
                            });
                        }
                    };
                } else {

                }
            }
            Method method = jsApi.getClass().getMethod(methodName, types);
            return String.valueOf(method.invoke(jsApi, values));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "xxx";
    }

    // 去掉的哈
    public void post(String script) {
        Log.i(MainActivity.TAG, "java call js and script " + script);
        // 必须执行在 UI Thread (JsBrigd同一个线程)
        mHandler.post(() -> {
            webView.evaluateJavascript(script, null);
        });
    }

    public void reflect() {
        try {
            Person p = new Person();
            Method addMethod = p.getClass().getDeclaredMethod("add", int.class, String.class);
            addMethod.setAccessible(true);

            Object[] values = new Object[2];
            values[0] = 1;
            values[1] = "duanxia";
//            Object xxxx = addMethod.invoke(p, 100, "xxxx");
            Object xxxx = addMethod.invoke(p, values);
            Log.i(MainActivity.TAG, "xxxx= "+xxxx);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
