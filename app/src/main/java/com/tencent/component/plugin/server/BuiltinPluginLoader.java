package com.tencent.component.plugin.server;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.utils.DebugUtil;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.UniqueLock;
import com.tencent.component.utils.log.LogUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class BuiltinPluginLoader {
    private static final String TAG = "BuiltinPluginLoader";
    private final Context mContext;
    private final PlatformServerContext mPlatformServerContext;
    private final PluginInstaller mPluginInstaller;
    private final PluginManagerServer mPluginManagerServer;
    private final LinkedHashMap<String, PluginRecord> mPluginRecords = new LinkedHashMap<>();
    private final UniqueLock<String> mUniqueLock = new UniqueLock<>();

    BuiltinPluginLoader(PlatformServerContext platformServerContext) {
        this.mPlatformServerContext = platformServerContext;
        this.mContext = this.mPlatformServerContext.getContext();
        this.mPluginInstaller = platformServerContext.getPluginInstaller();
        this.mPluginManagerServer = platformServerContext.getPluginManagerServer();
        init();
    }

    private void init() {
        Collection<PluginRecord> pluginRecords = parseXml(this.mContext, PluginConstant.getBuiltinConfigFilePath(this.mPlatformServerContext));
        if (pluginRecords != null) {
            for (PluginRecord record : pluginRecords) {
                if (record != null) {
                    this.mPluginRecords.put(record.id, record);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void load() {
        Collection<PluginRecord> pluginRecords = this.mPluginRecords.values();
        if (pluginRecords != null) {
            for (PluginRecord record : pluginRecords) {
                performLoad(record);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void load(String id) {
        PluginRecord record = this.mPluginRecords.get(id);
        if (record != null) {
            performLoad(record);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isNewer(PluginInfo pluginInfo) {
        PluginRecord record;
        if (pluginInfo == null || (record = this.mPluginRecords.get(pluginInfo.pluginId)) == null || record.version <= pluginInfo.version) {
            return false;
        }
        return true;
    }

    private void performLoad(PluginRecord record) {
        boolean succeed = true;
        if (record != null && record.isValid()) {
            Lock lock = this.mUniqueLock.obtain(record.id);
            lock.lock();
            try {
                if (!record.loaded) {
                    PluginInfo installed = this.mPluginManagerServer.getPluginInfo(record.id);
                    LogUtil.i(TAG, "plugin is debug =" + DebugUtil.isDebuggable(this.mContext));
                    if (installed == null) {
                        LogUtil.i(TAG, "installed is null ");
                    } else {
                        LogUtil.i(TAG, "plugin version =" + installed.version + ":" + record.version);
                    }
                    if (DebugUtil.isDebuggable(this.mContext) || installed == null || installed.version < record.version) {
                        LogUtil.i(TAG, "plugin is remove========== ");
                        File tmpFile = copyAssetsTmpSafely(this.mContext, record.path);
                        if (!isFileValid(tmpFile) || this.mPluginInstaller.install(tmpFile) != PluginInstaller.INSTALL_SUCCEED) {
                            succeed = false;
                        }
                        if (!succeed) {
                            LogUtil.d(TAG, "fail to copy assets to tmp or perform install, record:" + record + " installed:" + installed);
                        }
                        record.loaded = true;
                        lock.unlock();
                        return;
                    }
                    LogUtil.i(TAG, "plugin " + installed + " is already up to date");
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private static Collection<PluginRecord> parseXml(Context context, String xml) {
        if (!TextUtils.isEmpty(xml)) {
            try {
                PluginContentHandler contentHandler = new PluginContentHandler();
                XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                xmlReader.setContentHandler(contentHandler);
                xmlReader.parse(new InputSource(context.getAssets().open(xml)));
                return contentHandler.getPluginRecords();
            } catch (Throwable e) {
                LogUtil.i(TAG, "fail to parse xml " + xml, e);
            }
        }
        return null;
    }

    private static File copyAssetsTmpSafely(Context context, String assetsPath) {
        if (assetsPath == null) {
            return null;
        }
        File tmp = generateTmpFile(context, true);
        if (tmp != null) {
            if (tmp.isDirectory()) {
                FileUtil.delete(tmp);
            }
            FileUtil.copyAssets(context, assetsPath, tmp.getAbsolutePath());
            if (isFileValid(tmp)) {
                return tmp;
            }
        }
        File tmp2 = generateTmpFile(context, false);
        if (tmp2 != null) {
            if (tmp2.isDirectory()) {
                FileUtil.delete(tmp2);
            }
            FileUtil.copyAssets(context, assetsPath, tmp2.getAbsolutePath());
            if (isFileValid(tmp2)) {
                return tmp2;
            }
        }
        return null;
    }

    private static File generateTmpFile(Context context, boolean external) {
        String path = PluginConstant.getPluginTmpDir(context, UUID.randomUUID().toString(), external, true);
        if (path != null) {
            return new File(path);
        }
        return null;
    }

    private static boolean isFileValid(File file) {
        return file != null && file.isFile() && file.length() > 0;
    }

    static final class PluginRecord {
        String id;
        boolean loaded = false;
        String path;
        String uri;
        int version;

        PluginRecord() {
        }

        public boolean isValid() {
            return !TextUtils.isEmpty(this.id) && !TextUtils.isEmpty(this.path) && this.version >= 0;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.id == null ? 0 : this.id.hashCode()) + 527) * 31;
            if (this.path != null) {
                i = this.path.hashCode();
            }
            return ((hashCode + i) * 31) + this.version;
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof PluginRecord)) {
                return false;
            }
            PluginRecord cmp = (PluginRecord) obj;
            if (!equals(this.id, cmp.id) || !equals(this.path, cmp.path) || this.version != cmp.version) {
                return false;
            }
            return true;
        }

        private static boolean equals(String str1, String str2) {
            if (str1 == null) {
                return str2 == null;
            }
            return str1.equals(str2);
        }

        public String toString() {
            return "PluginRecord{" + this.id + " " + this.version + " " + this.path + "}";
        }
    }

    static final class PluginContentHandler extends DefaultHandler {
        private static final String ATTRIBUTE_ID = "id";
        private static final String ATTRIBUTE_PATH = "path";
        private static final String ATTRIBUTE_URI = "uri";
        private static final String ATTRIBUTE_VERSION = "version";
        private static final String TAG_ITEM = "item";
        private static final String TAG_PLUGIN = "plugin";
        private boolean mPluginDomain = false;
        private final ArrayList<PluginRecord> mPluginRecords = new ArrayList<>();

        PluginContentHandler() {
        }

        public Collection<PluginRecord> getPluginRecords() {
            return this.mPluginRecords;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (TAG_PLUGIN.equalsIgnoreCase(localName)) {
                this.mPluginDomain = true;
            }
            if (this.mPluginDomain && TAG_ITEM.equalsIgnoreCase(localName)) {
                PluginRecord record = new PluginRecord();
                record.id = attributes.getValue("id");
                record.path = attributes.getValue("path");
                record.uri = attributes.getValue("uri");
                record.version = toInteger(attributes.getValue("version"), -1);
                if (record.isValid()) {
                    this.mPluginRecords.add(record);
                }
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (TAG_PLUGIN.equalsIgnoreCase(localName)) {
                this.mPluginDomain = false;
            }
        }

        private static int toInteger(String str, int defaultValue) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }
}
