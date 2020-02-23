package com.subao.common.e;

import com.vk.sdk.api.VKApiConst;
import java.util.Locale;

/* compiled from: Defines */
public class n {
    public static final Locale a = Locale.US;
    public static final Locale b = a;
    public static a c;
    public static final ai d = new ai(VKApiConst.HTTPS, "api.xunyou.mobi", -1);

    /* compiled from: Defines */
    public static class b {
        public static String a = "key_portal_misc";
    }

    /* compiled from: Defines */
    public enum a {
        SDK("SDK"),
        UI("UI"),
        SERVICE("SERVICE"),
        ROM("ROM"),
        LEAK_CANARY("LEAK_CANARY"),
        EGUAN("EGUAN");
        
        public final String g;

        private a(String str) {
            this.g = str;
        }
    }
}
