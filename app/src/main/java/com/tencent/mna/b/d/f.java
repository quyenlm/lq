package com.tencent.mna.b.d;

import com.appsflyer.share.Constants;
import com.tencent.mna.base.utils.h;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: KartinCalculator */
final class f {
    private Map<String, Long> a;
    private Map<String, Integer> b;

    f(Map<String, Long> map) {
        this.a = map == null ? Collections.emptyMap() : map;
        this.b = new ConcurrentHashMap();
        this.b.put("*", 1);
        this.b.put(Constants.URL_PATH_DELIMITER, 1);
        this.b.put("+", 2);
        this.b.put("-", 2);
        this.b.put("<", 3);
        this.b.put("<=", 3);
        this.b.put(">", 3);
        this.b.put(">=", 3);
        this.b.put("==", 4);
        this.b.put("!=", 4);
        this.b.put("&&", 5);
        this.b.put("||", 6);
    }

    /* access modifiers changed from: package-private */
    public long a(String str) {
        try {
            String[] split = str.split("\\$");
            ArrayList arrayList = new ArrayList(split.length);
            for (String str2 : split) {
                if (str2 != null && str2.length() > 0) {
                    arrayList.add(str2);
                }
            }
            return b(a((List<String>) arrayList));
        } catch (Exception e) {
            h.a("calculateInfix exception:" + e.getMessage());
            return -1;
        }
    }

    private List<String> a(List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Stack stack = new Stack();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (!b(next)) {
                arrayList.add(next);
            } else if (next.equals(com.tencent.tp.a.h.a) || stack.isEmpty()) {
                stack.push(next);
            } else if (next.equals(com.tencent.tp.a.h.b)) {
                while (!((String) stack.peek()).equals(com.tencent.tp.a.h.a)) {
                    arrayList.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && !((String) stack.peek()).equals(com.tencent.tp.a.h.a) && a(next, (String) stack.peek()) > 0) {
                    arrayList.add(stack.pop());
                }
                stack.push(next);
            }
        }
        while (!stack.isEmpty()) {
            arrayList.add(stack.pop());
        }
        return arrayList;
    }

    private long b(List<String> list) {
        try {
            Stack stack = new Stack();
            for (String next : list) {
                if (!b(next)) {
                    Long c = c(next);
                    if (c == null) {
                        stack.clear();
                        return 0;
                    }
                    stack.push(c);
                } else {
                    long longValue = ((Long) stack.pop()).longValue();
                    stack.push(Long.valueOf(a(next, ((Long) stack.pop()).longValue(), longValue)));
                }
            }
            return ((Long) stack.pop()).longValue();
        } catch (Exception e) {
            h.a("calculateSuffix exception:" + e.getMessage());
            return -2;
        }
    }

    private boolean b(String str) {
        if (str.equals(com.tencent.tp.a.h.a) || str.equals(com.tencent.tp.a.h.b) || this.b.get(str) != null) {
            return true;
        }
        return false;
    }

    private int a(String str, String str2) {
        return this.b.get(str).intValue() - this.b.get(str2).intValue();
    }

    private Long c(String str) {
        Long l = this.a.get(str);
        if (l != null || !str.startsWith("#")) {
            return l;
        }
        return Long.valueOf(Long.parseLong(str.replace("#", "")));
    }

    private long a(String str, long j, long j2) {
        boolean z = true;
        long j3 = 0;
        boolean z2 = j != 0;
        if (j2 == 0) {
            z = false;
        }
        if (str.equals("*")) {
            return j * j2;
        }
        if (str.equals(Constants.URL_PATH_DELIMITER)) {
            return j / j2;
        }
        if (str.equals("+")) {
            return j + j2;
        }
        if (str.equals("-")) {
            return j - j2;
        }
        if (str.equals("<")) {
            return j < j2 ? 1 : 0;
        } else if (str.equals("<=")) {
            if (j > j2) {
                return 0;
            }
            return 1;
        } else if (str.equals(">")) {
            if (j <= j2) {
                return 0;
            }
            return 1;
        } else if (str.equals(">=")) {
            if (j < j2) {
                return 0;
            }
            return 1;
        } else if (str.equals("==")) {
            if (j != j2) {
                return 0;
            }
            return 1;
        } else if (str.equals("!=")) {
            if (j == j2) {
                return 0;
            }
            return 1;
        } else if (str.equals("&&")) {
            if (!z2 || !z) {
                return 0;
            }
            return 1;
        } else if (!str.equals("||")) {
            return 0;
        } else {
            if (z2 || z) {
                j3 = 1;
            }
            return j3;
        }
    }
}
