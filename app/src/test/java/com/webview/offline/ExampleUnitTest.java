package com.webview.offline;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Method;

import static java.lang.System.out;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void reflect() {
        try {
            Person p = new Person();// 可变参数 == 一个数组
            Method addMethod = p.getClass().getDeclaredMethod("add", new Class[]{int.class, String.class});
//            Method addMethod = p.getClass().getDeclaredMethod("add", int.class, String.class);
            addMethod.setAccessible(true);

            Object[] values = new Object[2];
            values[0] = 1;
            values[1] = "duanxia";
            Object xxxx = addMethod.invoke(p, 100, "xxxx"); // 这两种方式都是可以的
//            Object xxxx = addMethod.invoke(p, values);
            out.println(xxxx);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void reflect2() {
        try {
            Person p = new Person();
            Method addMethod = p.getClass().getDeclaredMethod("add2", String.class, String.class);
            addMethod.setAccessible(true);

            Object[] values = new Object[2];
            values[0] = "1";
            values[1] = "duanxia";
//            Object xxxx = addMethod.invoke(p, 100, "xxxx");
            Object xxxx = addMethod.invoke(p, values); // error
            out.println(xxxx);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void addition_isCorrect() {
        String jsonStr = "{\"method\":\"doAsyncAction\",\"types\":[\"string\",\"function\",\"string\"],\"args\":[\"listenPage\",0,\"supply\"]}";
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