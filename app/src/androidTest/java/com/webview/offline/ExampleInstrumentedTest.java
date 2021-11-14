package com.webview.offline;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.System.out;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.webview.offline", appContext.getPackageName());
        String json2 = "{\"method\":\"doAsyncAction\"}";
        process(json2);

    }

    public void process(String jsonStr) {
        try { // {"method":"doAsyncAction","types":["string","function","string"],"args":["listenPage",0,"supply"]}
            JSONObject callJson = new JSONObject(jsonStr);
            String methodName = callJson.getString("method");
            /*JSONArray argsTypes = callJson.getJSONArray("types");
            JSONArray argsVals = callJson.getJSONArray("args");
            int len = argsTypes.length();
            Object[] values = new Object[len];*/
            out.println("methodName:"+methodName /*+ ", len = "+argsTypes.length()*/);


            // 数字类型细分匹配
            /*if (numIndex > 0) {
                Class[] methodTypes = currMethod.getParameterTypes();
                int currIndex;
                Class currCls;
                while (numIndex > 0) {
                    currIndex = numIndex - numIndex / 10 * 10 - 1;
                    currCls = methodTypes[currIndex];
                    if (currCls == int.class) {
                        values[currIndex] = argsVals.getInt(currIndex);
                    } else if (currCls == long.class) {
                        //WARN: argsJson.getLong(k + defValue) will return a bigger incorrect number
                        values[currIndex] = Long.parseLong(argsVals.getString(currIndex));
                    } else {
                        values[currIndex] = argsVals.getDouble(currIndex);
                    }
                    numIndex /= 10;
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}