package com.tencent.component.plugin;

import java.util.ArrayList;

public class PluginReporter {
    public static final String EVENT_CENTER_INSTALL = "center_install";
    public static final String EVENT_CENTER_UPDATE = "center_update";
    public static final String EVENT_INSTALL = "install";
    public static final String EVENT_LOAD = "load";
    private static final Pool<ReportEvent> sReportEventPool = new Pool<>(16, new Pool.Factory<ReportEvent>() {
        public ReportEvent create() {
            return new ReportEvent();
        }
    });
    private static volatile Reporter sReporter;

    public static class ReportEvent {
        public String brief;
        public Throwable exception;
        public String msg;
        public String name;
        public boolean succeed;
    }

    public interface Reporter {
        void report(ReportEvent reportEvent);
    }

    static final class Pool<T> {
        private final Factory<T> mFactory;
        private final ArrayList<T> mList;
        private final int mPoolSize;

        public interface Factory<T> {
            T create();
        }

        public Pool(int size, Factory<T> factory) {
            this.mPoolSize = size;
            this.mList = new ArrayList<>(size);
            this.mFactory = factory;
        }

        public synchronized T get() {
            int n;
            n = this.mList.size();
            return n > 0 ? this.mList.remove(n - 1) : newObject();
        }

        public synchronized void recycle(T t) {
            if (this.mList.size() < this.mPoolSize) {
                this.mList.add(t);
            }
        }

        private T newObject() {
            return this.mFactory.create();
        }
    }

    public static void report(String name, boolean succeed, String brief, String msg, Throwable e) {
        Reporter reporter = getReporter();
        if (reporter != null) {
            ReportEvent event = sReportEventPool.get();
            event.name = name;
            event.succeed = succeed;
            event.brief = brief;
            event.msg = msg;
            event.exception = e;
            reporter.report(event);
            sReportEventPool.recycle(event);
        }
    }

    public static void setReporter(Reporter reporter) {
        synchronized (PluginReporter.class) {
            sReporter = reporter;
        }
    }

    private static Reporter getReporter() {
        return sReporter;
    }
}
