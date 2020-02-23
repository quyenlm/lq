package com.google.android.gms.analytics;

import android.content.Context;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.internal.zzaob;
import java.lang.Thread;
import java.util.ArrayList;

public class ExceptionReporter implements Thread.UncaughtExceptionHandler {
    private final Context mContext;
    private ExceptionParser zzadA;
    private GoogleAnalytics zzadB;
    private final Thread.UncaughtExceptionHandler zzady;
    private final Tracker zzadz;

    public ExceptionReporter(Tracker tracker, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (context == null) {
            throw new NullPointerException("context cannot be null");
        } else {
            this.zzady = uncaughtExceptionHandler;
            this.zzadz = tracker;
            this.zzadA = new StandardExceptionParser(context, new ArrayList());
            this.mContext = context.getApplicationContext();
            String valueOf = String.valueOf(uncaughtExceptionHandler == null ? Constants.NULL_VERSION_ID : uncaughtExceptionHandler.getClass().getName());
            zzaob.v(valueOf.length() != 0 ? "ExceptionReporter created, original handler is ".concat(valueOf) : new String("ExceptionReporter created, original handler is "));
        }
    }

    public ExceptionParser getExceptionParser() {
        return this.zzadA;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.zzadA = exceptionParser;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String str = "UncaughtException";
        if (this.zzadA != null) {
            str = this.zzadA.getDescription(thread != null ? thread.getName() : null, th);
        }
        String valueOf = String.valueOf(str);
        zzaob.v(valueOf.length() != 0 ? "Reporting uncaught exception: ".concat(valueOf) : new String("Reporting uncaught exception: "));
        this.zzadz.send(new HitBuilders.ExceptionBuilder().setDescription(str).setFatal(true).build());
        if (this.zzadB == null) {
            this.zzadB = GoogleAnalytics.getInstance(this.mContext);
        }
        GoogleAnalytics googleAnalytics = this.zzadB;
        googleAnalytics.dispatchLocalHits();
        googleAnalytics.zzji().zzkv().zzkm();
        if (this.zzady != null) {
            zzaob.v("Passing exception to the original handler");
            this.zzady.uncaughtException(thread, th);
        }
    }

    /* access modifiers changed from: package-private */
    public final Thread.UncaughtExceptionHandler zzjn() {
        return this.zzady;
    }
}
