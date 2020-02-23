package com.subao.common.j;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import com.subao.common.j.o;
import java.lang.reflect.Method;

/* compiled from: SignalWatcherForCellular */
public class p extends o {
    private TelephonyManager a;
    private PhoneStateListener b;

    public p(o.a aVar) {
        super(aVar);
    }

    public void a(Context context) {
        synchronized (this) {
            if (this.a == null) {
                this.a = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
                if (this.a != null) {
                    this.b = new a(this, this.a);
                    this.a.listen(this.b, 256);
                }
            }
        }
    }

    public void a() {
        synchronized (this) {
            if (this.a != null) {
                this.a.listen(this.b, 0);
                this.b = null;
                this.a = null;
            }
        }
    }

    /* compiled from: SignalWatcherForCellular */
    static class a extends PhoneStateListener {
        private final TelephonyManager a;
        private final o b;

        a(o oVar, TelephonyManager telephonyManager) {
            this.a = telephonyManager;
            this.b = oVar;
        }

        static int a(int i) {
            if (i >= -70) {
                return 4;
            }
            if (i >= -85) {
                return 3;
            }
            if (i >= -95) {
                return 2;
            }
            if (i >= -100) {
                return 1;
            }
            return 0;
        }

        static int b(int i) {
            if (i >= -90) {
                return 4;
            }
            if (i >= -110) {
                return 3;
            }
            if (i >= -130) {
                return 2;
            }
            if (i >= -150) {
                return 1;
            }
            return 0;
        }

        static int c(int i) {
            if (i >= 7) {
                return 4;
            }
            if (i >= 5) {
                return 3;
            }
            if (i >= 3) {
                return 2;
            }
            if (i >= 1) {
                return 1;
            }
            return 0;
        }

        static int a(SignalStrength signalStrength, String str) {
            Object invoke;
            try {
                Method method = signalStrength.getClass().getMethod(str, new Class[0]);
                if (!(method == null || (invoke = method.invoke(signalStrength, new Object[0])) == null || !(invoke instanceof Integer))) {
                    return ((Integer) invoke).intValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }

        static int a(TelephonyManager telephonyManager, SignalStrength signalStrength) {
            int a2;
            int gsmSignalStrength;
            int a3 = a(signalStrength, "getLevel");
            if (a3 >= 0) {
                return Math.min(a3, 4);
            }
            if (signalStrength.isGsm() && (gsmSignalStrength = signalStrength.getGsmSignalStrength()) != 99) {
                return a((gsmSignalStrength * 2) - 113);
            }
            if (telephonyManager.getNetworkType() == 13 && (a2 = a(signalStrength, "getLteLevel")) >= 0) {
                return Math.min(a2, 4);
            }
            int evdoSnr = signalStrength.getEvdoSnr();
            if (evdoSnr >= 0) {
                return c(evdoSnr);
            }
            int a4 = a(signalStrength.getCdmaDbm());
            int b2 = b(signalStrength.getCdmaEcio());
            if (a4 >= b2) {
                return b2;
            }
            return a4;
        }

        static int d(int i) {
            if (i <= 0) {
                return 0;
            }
            if (i >= 4) {
                return 100;
            }
            return (i * 100) / 4;
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            this.b.a(d(a(this.a, signalStrength)));
        }
    }
}
