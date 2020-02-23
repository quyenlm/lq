package com.beetalk.sdk.cache;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PersistentCache extends StorageCache {
    private static final String EXTERNAL_APP_FOLDER_PATH = "/com.garena.msdk";
    private static final String FALLBACK_SHARED_PREF_FILE_NAME = "com.garena.msdk.persist.fallback";
    private static final String GUEST_ACCOUNT_FILE_NAME_PREFIX = "guest";
    private static final String GUEST_ACCOUNT_INFO_JSON_KEY = "guest_account_info";
    private static volatile PersistentCache sInstance = null;
    private final SharedPrefStorage mFallbackStorage = new SharedPrefStorage(GGLoginSession.getApplicationContext(), FALLBACK_SHARED_PREF_FILE_NAME);

    public static PersistentCache getInstance() {
        if (sInstance == null) {
            synchronized (PersistentCache.class) {
                if (sInstance == null) {
                    sInstance = new PersistentCache();
                }
            }
        }
        return sInstance;
    }

    private PersistentCache() {
    }

    public void save(Map<String, String> map) {
        this.mFallbackStorage.save(map);
        if (isExternalAccessible()) {
            HashMap<String, String> oldMap = (HashMap) load();
            for (String key : map.keySet()) {
                oldMap.put(key, map.get(key));
            }
            saveExternal(oldMap);
            return;
        }
        BBLogger.d("external storage not accessible, save in internal storage only", new Object[0]);
    }

    public void clear() {
        this.mFallbackStorage.clear();
        if (isExternalAccessible()) {
            File dir = new File(getAppFolderAbsolutePath());
            if (dir.exists()) {
                new File(dir, getGuestAccountFileName()).delete();
            }
        }
    }

    public Map load() {
        Map<String, String> data = this.mFallbackStorage.load();
        if (isExternalAccessible()) {
            sync(data, loadFromFile());
        }
        return data;
    }

    public void remove(String key) {
        this.mFallbackStorage.remove(key);
        if (isExternalAccessible()) {
            Map<String, String> map = loadFromFile();
            if (map.remove(key) != null) {
                saveExternal(map);
            }
        }
    }

    private void saveExternal(Map<String, String> map) {
        saveToFile(mapToStr(map));
    }

    private String mapToStr(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(GUEST_ACCOUNT_INFO_JSON_KEY, new JSONObject(map));
        } catch (JSONException e) {
            BBLogger.e(e);
        }
        return jsonObject.toString();
    }

    private Map<String, String> strToMap(String content) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject guestJSON = new JSONObject(content).getJSONObject(GUEST_ACCOUNT_INFO_JSON_KEY);
            Iterator<String> keysItr = guestJSON.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                map.put(key, guestJSON.getString(key));
            }
        } catch (JSONException e) {
            BBLogger.e(e);
        }
        return map;
    }

    private void saveToFile(String strToSave) {
        File dir = new File(getAppFolderAbsolutePath());
        if (dir.exists() || dir.mkdir()) {
            try {
                FileOutputStream f = new FileOutputStream(new File(dir, getGuestAccountFileName()));
                PrintWriter pw = new PrintWriter(f);
                pw.write(strToSave);
                pw.flush();
                pw.close();
                f.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                BBLogger.e("******* File not found. Did you add a WRITE_EXTERNAL_STORAGE permission to the   manifest?", new Object[0]);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } else {
            BBLogger.e("can't create dir: " + dir.getAbsolutePath(), new Object[0]);
        }
    }

    private String getAppFolderAbsolutePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + EXTERNAL_APP_FOLDER_PATH;
    }

    private String getGuestAccountFileName() {
        Integer appId = Helper.getIntegerAppId(GGLoginSession.getApplicationContext());
        if (appId.intValue() == -1 || appId.intValue() == 100006) {
            return "guest.dat";
        }
        return GUEST_ACCOUNT_FILE_NAME_PREFIX + Helper.getIntegerAppId(GGLoginSession.getApplicationContext()) + ".dat";
    }

    private Map<String, String> loadFromFile() {
        Map<String, String> map = new HashMap<>();
        File dir = new File(getAppFolderAbsolutePath());
        if (!dir.exists()) {
            return map;
        }
        File file = new File(dir, getGuestAccountFileName());
        if (!file.exists()) {
            return map;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = br.readLine();
                    if (line != null) {
                        sb.append(line);
                    } else {
                        map.putAll(strToMap(sb.toString()));
                        return map;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                BBLogger.e("read failed from file: " + file.getAbsolutePath(), new Object[0]);
                return null;
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            BBLogger.e("fail to read from file: " + file.getAbsolutePath(), new Object[0]);
            return map;
        }
    }

    private void sync(Map<String, String> internal, Map<String, String> external) {
        syncToExternal(internal, external);
        syncToInternal(external, internal);
    }

    private void syncToInternal(Map<String, String> external, Map<String, String> internal) {
        boolean needSave = false;
        for (Map.Entry<String, String> e : external.entrySet()) {
            if (!(e == null || e.getKey() == null || e.getValue() == null || internal.containsKey(e.getKey()))) {
                needSave = true;
                internal.put(e.getKey(), e.getValue());
            }
        }
        if (needSave) {
            this.mFallbackStorage.save(internal);
        }
    }

    private void syncToExternal(Map<String, String> internal, Map<String, String> external) {
        boolean needSave = false;
        for (Map.Entry<String, String> e : internal.entrySet()) {
            if (!(e == null || e.getKey() == null || e.getValue() == null)) {
                if (!external.containsKey(e.getKey()) || !e.getValue().equals(external.get(e.getKey()))) {
                    needSave = true;
                    external.put(e.getKey(), e.getValue());
                }
            }
        }
        if (needSave) {
            saveExternal(external);
        }
    }

    private boolean isExternalAccessible() {
        if (!isAndroidM()) {
            return true;
        }
        Context context = GGLoginSession.getApplicationContext();
        if (context == null || context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    private boolean isAndroidM() {
        return Build.VERSION.SDK_INT >= 23;
    }
}
