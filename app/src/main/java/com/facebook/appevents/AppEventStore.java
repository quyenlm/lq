package com.facebook.appevents;

import android.content.Context;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.internal.Utility;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

class AppEventStore {
    private static final String PERSISTED_EVENTS_FILENAME = "AppEventsLogger.persistedevents";
    private static final String TAG = AppEventStore.class.getName();

    AppEventStore() {
    }

    public static synchronized void persistEvents(AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState appEvents) {
        synchronized (AppEventStore.class) {
            AppEventUtility.assertIsNotMainThread();
            PersistedEvents persistedEvents = readAndClearStore();
            if (persistedEvents.containsKey(accessTokenAppIdPair)) {
                persistedEvents.get(accessTokenAppIdPair).addAll(appEvents.getEventsToPersist());
            } else {
                persistedEvents.addEvents(accessTokenAppIdPair, appEvents.getEventsToPersist());
            }
            saveEventsToDisk(persistedEvents);
        }
    }

    public static synchronized void persistEvents(AppEventCollection eventsToPersist) {
        synchronized (AppEventStore.class) {
            AppEventUtility.assertIsNotMainThread();
            PersistedEvents persistedEvents = readAndClearStore();
            for (AccessTokenAppIdPair accessTokenAppIdPair : eventsToPersist.keySet()) {
                persistedEvents.addEvents(accessTokenAppIdPair, eventsToPersist.get(accessTokenAppIdPair).getEventsToPersist());
            }
            saveEventsToDisk(persistedEvents);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.facebook.appevents.PersistedEvents} */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x007c, code lost:
        r8 = th;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0033 A[SYNTHETIC, Splitter:B:16:0x0033] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.facebook.appevents.PersistedEvents readAndClearStore() {
        /*
            java.lang.Class<com.facebook.appevents.AppEventStore> r9 = com.facebook.appevents.AppEventStore.class
            monitor-enter(r9)
            com.facebook.appevents.internal.AppEventUtility.assertIsNotMainThread()     // Catch:{ all -> 0x005b }
            r5 = 0
            r7 = 0
            android.content.Context r1 = com.facebook.FacebookSdk.getApplicationContext()     // Catch:{ all -> 0x005b }
            java.lang.String r8 = "AppEventsLogger.persistedevents"
            java.io.FileInputStream r4 = r1.openFileInput(r8)     // Catch:{ FileNotFoundException -> 0x0044, Exception -> 0x005e }
            com.facebook.appevents.AppEventStore$MovedClassObjectInputStream r6 = new com.facebook.appevents.AppEventStore$MovedClassObjectInputStream     // Catch:{ FileNotFoundException -> 0x0044, Exception -> 0x005e }
            java.io.BufferedInputStream r8 = new java.io.BufferedInputStream     // Catch:{ FileNotFoundException -> 0x0044, Exception -> 0x005e }
            r8.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0044, Exception -> 0x005e }
            r6.<init>(r8)     // Catch:{ FileNotFoundException -> 0x0044, Exception -> 0x005e }
            java.lang.Object r8 = r6.readObject()     // Catch:{ FileNotFoundException -> 0x0099, Exception -> 0x0096, all -> 0x0093 }
            r0 = r8
            com.facebook.appevents.PersistedEvents r0 = (com.facebook.appevents.PersistedEvents) r0     // Catch:{ FileNotFoundException -> 0x0099, Exception -> 0x0096, all -> 0x0093 }
            r7 = r0
            com.facebook.internal.Utility.closeQuietly(r6)     // Catch:{ all -> 0x005b }
            java.lang.String r8 = "AppEventsLogger.persistedevents"
            java.io.File r8 = r1.getFileStreamPath(r8)     // Catch:{ Exception -> 0x003a }
            r8.delete()     // Catch:{ Exception -> 0x003a }
            r5 = r6
        L_0x0031:
            if (r7 != 0) goto L_0x0038
            com.facebook.appevents.PersistedEvents r7 = new com.facebook.appevents.PersistedEvents     // Catch:{ all -> 0x005b }
            r7.<init>()     // Catch:{ all -> 0x005b }
        L_0x0038:
            monitor-exit(r9)
            return r7
        L_0x003a:
            r3 = move-exception
            java.lang.String r8 = TAG     // Catch:{ all -> 0x005b }
            java.lang.String r10 = "Got unexpected exception when removing events file: "
            android.util.Log.w(r8, r10, r3)     // Catch:{ all -> 0x005b }
            r5 = r6
            goto L_0x0031
        L_0x0044:
            r8 = move-exception
        L_0x0045:
            com.facebook.internal.Utility.closeQuietly(r5)     // Catch:{ all -> 0x005b }
            java.lang.String r8 = "AppEventsLogger.persistedevents"
            java.io.File r8 = r1.getFileStreamPath(r8)     // Catch:{ Exception -> 0x0052 }
            r8.delete()     // Catch:{ Exception -> 0x0052 }
            goto L_0x0031
        L_0x0052:
            r3 = move-exception
            java.lang.String r8 = TAG     // Catch:{ all -> 0x005b }
            java.lang.String r10 = "Got unexpected exception when removing events file: "
            android.util.Log.w(r8, r10, r3)     // Catch:{ all -> 0x005b }
            goto L_0x0031
        L_0x005b:
            r8 = move-exception
            monitor-exit(r9)
            throw r8
        L_0x005e:
            r2 = move-exception
        L_0x005f:
            java.lang.String r8 = TAG     // Catch:{ all -> 0x007c }
            java.lang.String r10 = "Got unexpected exception while reading events: "
            android.util.Log.w(r8, r10, r2)     // Catch:{ all -> 0x007c }
            com.facebook.internal.Utility.closeQuietly(r5)     // Catch:{ all -> 0x005b }
            java.lang.String r8 = "AppEventsLogger.persistedevents"
            java.io.File r8 = r1.getFileStreamPath(r8)     // Catch:{ Exception -> 0x0073 }
            r8.delete()     // Catch:{ Exception -> 0x0073 }
            goto L_0x0031
        L_0x0073:
            r3 = move-exception
            java.lang.String r8 = TAG     // Catch:{ all -> 0x005b }
            java.lang.String r10 = "Got unexpected exception when removing events file: "
            android.util.Log.w(r8, r10, r3)     // Catch:{ all -> 0x005b }
            goto L_0x0031
        L_0x007c:
            r8 = move-exception
        L_0x007d:
            com.facebook.internal.Utility.closeQuietly(r5)     // Catch:{ all -> 0x005b }
            java.lang.String r10 = "AppEventsLogger.persistedevents"
            java.io.File r10 = r1.getFileStreamPath(r10)     // Catch:{ Exception -> 0x008a }
            r10.delete()     // Catch:{ Exception -> 0x008a }
        L_0x0089:
            throw r8     // Catch:{ all -> 0x005b }
        L_0x008a:
            r3 = move-exception
            java.lang.String r10 = TAG     // Catch:{ all -> 0x005b }
            java.lang.String r11 = "Got unexpected exception when removing events file: "
            android.util.Log.w(r10, r11, r3)     // Catch:{ all -> 0x005b }
            goto L_0x0089
        L_0x0093:
            r8 = move-exception
            r5 = r6
            goto L_0x007d
        L_0x0096:
            r2 = move-exception
            r5 = r6
            goto L_0x005f
        L_0x0099:
            r8 = move-exception
            r5 = r6
            goto L_0x0045
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventStore.readAndClearStore():com.facebook.appevents.PersistedEvents");
    }

    private static void saveEventsToDisk(PersistedEvents eventsToPersist) {
        ObjectOutputStream oos = null;
        Context context = FacebookSdk.getApplicationContext();
        try {
            ObjectOutputStream oos2 = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput(PERSISTED_EVENTS_FILENAME, 0)));
            try {
                oos2.writeObject(eventsToPersist);
                Utility.closeQuietly(oos2);
                ObjectOutputStream objectOutputStream = oos2;
            } catch (Exception e) {
                e = e;
                oos = oos2;
                try {
                    Log.w(TAG, "Got unexpected exception while persisting events: ", e);
                    try {
                        context.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
                    } catch (Exception e2) {
                    }
                    Utility.closeQuietly(oos);
                } catch (Throwable th) {
                    th = th;
                    Utility.closeQuietly(oos);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                oos = oos2;
                Utility.closeQuietly(oos);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Log.w(TAG, "Got unexpected exception while persisting events: ", e);
            context.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
            Utility.closeQuietly(oos);
        }
    }

    private static class MovedClassObjectInputStream extends ObjectInputStream {
        private static final String ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair$SerializationProxyV1";
        private static final String APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AppEvent$SerializationProxyV1";

        public MovedClassObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        /* access modifiers changed from: protected */
        public ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
            ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();
            if (resultClassDescriptor.getName().equals(ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
                return ObjectStreamClass.lookup(AccessTokenAppIdPair.SerializationProxyV1.class);
            }
            if (resultClassDescriptor.getName().equals(APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME)) {
                return ObjectStreamClass.lookup(AppEvent.SerializationProxyV1.class);
            }
            return resultClassDescriptor;
        }
    }
}
