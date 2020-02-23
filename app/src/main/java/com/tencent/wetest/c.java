package com.tencent.wetest;

import android.util.Log;
import org.w3c.dom.Element;

class c {
    public d a;
    public String b;
    public String c;
    public Element d;
    final /* synthetic */ Robot e;

    c(Robot robot) {
        this.e = robot;
        this.a = new d(robot);
    }

    public void a() {
        this.a.a = "";
        this.a.b = "";
        this.a.c = 0;
        this.b = null;
        this.c = null;
        this.d = null;
    }

    public boolean b() {
        if (this.b == null) {
            Log.e("wetest", "action is NULL!");
            return false;
        } else if (this.a == null) {
            Log.e("wetest", "findexpr is NULL!");
            return false;
        } else if (this.a.a.length() == 0 && this.a.b.length() == 0) {
            Log.e("wetest", "containText and classType can't both be NULL or empty!");
            return false;
        } else if (this.c == null) {
            Log.e("wetest", "sequence is NULL!");
            return false;
        } else if (this.d != null) {
            return true;
        } else {
            Log.e("wetest", "param is NULL!");
            return false;
        }
    }
}
