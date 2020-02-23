package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: AccelGameMap */
public class c {
    /* access modifiers changed from: private */
    public static final Locale a = Locale.US;
    @NonNull
    private final b b;
    @Nullable
    private HashMap<String, b> c;
    @Nullable
    private HashMap<String, b> d;

    /* compiled from: AccelGameMap */
    public interface b {
        boolean a(@NonNull String str);

        boolean b(@NonNull String str);

        boolean c(@NonNull String str);
    }

    public c(@NonNull b bVar, @Nullable List<b> list) {
        this.b = bVar;
        a(list);
    }

    public c(@Nullable List<b> list) {
        this(new a(), list);
    }

    public void a(@Nullable List<b> list) {
        if (list == null || list.isEmpty()) {
            this.c = null;
            this.d = null;
            return;
        }
        this.c = new HashMap<>(list.size());
        this.d = new HashMap<>(16);
        for (b next : list) {
            if (next.c()) {
                List<String> list2 = next.e;
                if (list2 != null) {
                    for (String put : list2) {
                        this.d.put(put, next);
                    }
                }
            } else {
                this.c.put(next.a.toLowerCase(a), next);
            }
        }
    }

    @Nullable
    public b a(@NonNull String str, @NonNull String str2) {
        b bVar;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || this.b.a(str2) || this.b.b(str)) {
            return null;
        }
        if (this.d == null || (bVar = this.d.get(str)) == null) {
            return a(str2);
        }
        return bVar;
    }

    private b a(@NonNull String str) {
        if (this.c == null) {
            return null;
        }
        String lowerCase = str.toLowerCase(a);
        b bVar = this.c.get(lowerCase);
        if (bVar != null) {
            return bVar;
        }
        if (lowerCase.length() <= 3) {
            return null;
        }
        for (Map.Entry next : this.c.entrySet()) {
            String str2 = (String) next.getKey();
            if (str2.length() > 2) {
                b bVar2 = (b) next.getValue();
                if (!bVar2.l && !bVar2.b() && lowerCase.contains(str2)) {
                    if (this.b.c(lowerCase)) {
                        return null;
                    }
                    return bVar2;
                }
            }
        }
        return null;
    }

    /* compiled from: AccelGameMap */
    private static class a implements b {
        private static final String[] a = {"notification", "pps", "pptv", "theme", "wallpaper", "wifi", "安装", "八门神器", "百宝箱", "伴侣", "宝典", "备份", "必备", "壁纸", "变速", "表情", "补丁", "插件", "查询", "查询", "出招表", "春节神器", "答题", "大全", "大师", "单机", "动态", "翻图", "辅助", "辅助", "改名", "工具", "攻略", "喊话", "合成表", "合集", "盒子", "红包神器", "画报", "集市", "计算", "技巧", "計算", "加速", "脚本", "解说", "精选", "剧场", "快问", "礼包", "连招表", "论坛", "漫画", "秘籍", "模拟器", "魔盒", "配装器", "拼图", "启动器", "全集", "社区", "视频", "视讯", "手册", "刷开局", "刷魔", "锁屏", "台词", "特辑", "头条", "图集", "图鉴", "圖鑑", "外挂", "系列", "下载", "小说", "小智", "修改", "一键", "英雄帮", "英雄榜", "游戏盒", "游戏通", "掌游宝", "照相", "直播", "指南", "制作器", "主题", "助理", "助手", "抓包", "追剧", "桌面", "资料", "资讯", "資料", "作弊"};
        private static final String[] b = {"掌上英雄联盟", "影之诗", "Shadowverse"};
        private static final String[] c = {"com.kugou.android", "com.huluxia.mctool", "com.tencent.qt.sns", "com.cygames.Shadowverse", "jp.co.cygames.Shadowverse"};

        private a() {
        }

        public boolean a(@NonNull String str) {
            for (String equals : b) {
                if (equals.equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public boolean b(@NonNull String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            String lowerCase = str.toLowerCase(c.a);
            for (String equals : c) {
                if (equals.equals(lowerCase)) {
                    return true;
                }
            }
            return false;
        }

        public boolean c(@NonNull String str) {
            for (String contains : a) {
                if (str.contains(contains)) {
                    return true;
                }
            }
            return false;
        }
    }
}
