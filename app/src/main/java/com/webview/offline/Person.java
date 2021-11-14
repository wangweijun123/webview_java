package com.webview.offline;

import static java.lang.System.out;

public class Person {

    public int add(int num, String name) {
        out.println(name);
        return num *2;
    }

    public int add2(String num, String name) {
        out.println(name);
        return 2;
    }
}
