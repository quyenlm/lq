package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.subao.common.d;
import com.subao.common.e.z;
import com.subao.common.j.a;
import com.subao.common.n.b;
import com.subao.common.n.g;
import java.security.NoSuchAlgorithmException;

/* compiled from: PortalScriptDownloader */
class af extends z {
    private final int a;

    af(z.a aVar, int i) {
        super(aVar);
        this.a = i;
    }

    @Nullable
    public static aa a(@NonNull z.a aVar, int i) {
        aa aaVar;
        aa aaVar2;
        af afVar = new af(aVar, i);
        aa l = afVar.l();
        if (l != null) {
            if (b(l)) {
                aaVar = null;
                aaVar2 = l;
            } else if (!afVar.f(l)) {
                afVar.m();
                aaVar = null;
                aaVar2 = null;
            }
            afVar.e(aaVar2);
            return aaVar;
        }
        aaVar = l;
        aaVar2 = l;
        afVar.e(aaVar2);
        return aaVar;
    }

    static boolean b(aa aaVar) {
        byte[] a2;
        if (aaVar == null || (a2 = aaVar.a()) == null || a2.length <= 4) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean c(aa aaVar) {
        if (!super.c(aaVar)) {
            return false;
        }
        if (b(aaVar) || f(aaVar)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "scripts_" + this.a;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return String.format(n.b, "scripts/%d/%s", new Object[]{Integer.valueOf(this.a), n().b});
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String j() {
        return a.C0013a.ANY.e;
    }

    /* access modifiers changed from: package-private */
    public boolean f(aa aaVar) {
        boolean z = false;
        boolean a2 = d.a("SubaoData");
        if (aaVar == null) {
            if (a2) {
                Log.d("SubaoData", "PortalData of script is null");
            }
        } else if (d(aaVar)) {
            byte[] a3 = aaVar.a();
            if (a3 != null) {
                String c = aaVar.c();
                if (c != null && c.length() == 34) {
                    try {
                        z = c.substring(1, c.length() - 1).equalsIgnoreCase(g.a(b.a(a3), false));
                        if (a2) {
                            if (z) {
                                Log.d("SubaoData", "Script check ok");
                            } else {
                                Log.d("SubaoData", "Script digest is not expected");
                            }
                        }
                    } catch (NoSuchAlgorithmException e) {
                        if (a2) {
                            Log.d("SubaoData", "Digest calc failed");
                        }
                    }
                } else if (a2) {
                    Log.d("SubaoData", "Invalid script digest");
                }
            } else if (a2) {
                Log.d("SubaoData", "Script is null");
            }
        } else if (a2) {
            Log.d("SubaoData", "Invalid script version");
        }
        return z;
    }
}
