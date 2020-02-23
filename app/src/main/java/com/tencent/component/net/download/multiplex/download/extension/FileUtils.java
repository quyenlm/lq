package com.tencent.component.net.download.multiplex.download.extension;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.appsflyer.share.Constants;
import com.google.android.gms.drive.DriveFile;
import com.tencent.component.net.download.multiplex.DownloaderLog;
import com.tencent.component.net.download.multiplex.FileDownload;
import com.tencent.component.net.download.multiplex.download.DownloadTask;
import com.tencent.tp.a.h;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    public static final String APP_DEFAULT_FOLDER_NAME = ".Application";
    private static final String DIR_ACCOUNTS = "accounts";
    private static final String DIR_CACHE = ".cache";
    public static final String DIR_CHANNEL = "channel_conf";
    private static final String DIR_COOKIES = "cookies";
    private static final String DIR_CORE = ".core";
    private static final String DIR_DATA = "data";
    public static final String DIR_DYNAMIC_JAR_OUTPUT = "dynamic_jar_output";
    public static final String DIR_EXCULDE = "exclude";
    private static final String DIR_EXT_MAIN = "DownloadDemo";
    private static final String DIR_FREQUENT_FAVICON = "frequent_favicon";
    private static final String DIR_IMAGES = "images";
    private static final String DIR_MARKET = "market";
    private static final String DIR_PAGES = "pages";
    private static final String DIR_PLUGIN = "plugin";
    private static final String DIR_PUSH = "push";
    private static final String DIR_PUSH_APP_LIST = "push_app_list";
    private static final String DIR_READ_CSS = "read_css";
    private static final String DIR_RECENT_CACHE = "recent";
    private static final String DIR_SKINS = "skins";
    private static final String DIR_SKIN_CONFIG_CACHE = "skin_config_cache";
    public static final String DIR_SNAPSHOT = "snapshot";
    public static final String DIR_STARTPAGE = "startpage";
    private static final String DIR_UPDATER = ".incrupdate";
    private static final String DIR_WEBVIEW = "webviewCache";
    private static final String FILE_APPCENTER_ADV = "acadv.dat";
    private static final String FILE_APPCENTER_TAB = "actab";
    private static final String FILE_APPCENTER_WORDS = "acpromptword.dat";
    public static final String FILE_BOOT_STAT = "boot_stat.dat";
    public static final String FILE_CHANNEL = "channel.ini";
    public static final String FILE_CMD_RESULTS = "cmd_results.data";
    public static final String FILE_ENTRY_STAT = "entrystat.dat";
    private static final String FILE_FLASH_VERSION = "flashversion";
    private static final String FILE_LBS_DOMAIN = "lbsdomain";
    private static final String FILE_LOG = "log.dat";
    public static final String FILE_MTT_APP_START = "mttappstart.dat";
    private static Pattern FILE_NAME_PATTERN = Pattern.compile("^(.*)\\((\\d+)\\)$", 2);
    private static Pattern FILE_NAME_VALID_PATTERN = Pattern.compile("[\\\\\\/\\:\\*\\?\\\"\\|\\<\\>]", 2);
    private static final String FILE_PARTICULAR_URLS = "particularurls.dat";
    private static final String FILE_PLUGIN_FLASH = "flash.inf";
    private static final String FILE_PLUGIN_INFO = "plugin";
    private static final String FILE_PUSH_BANNER = "pushbanner";
    private static final String FILE_QAB = "qab";
    private static final String FILE_QQMARKET = "qqmaket.dat";
    public static final String FILE_QQMARKET_ADV = "qqmarketadv.dat";
    public static final String FILE_QQMARKET_UPDATE = "qqmarketupdate_4_1.dat";
    public static final String FILE_QQMKT_CACHE_DIR = "qqmkt";
    private static final String FILE_SAFE_DOMAIN = "safedomain";
    private static final String FILE_SAFE_DOMAIN_V2 = "safedomain_2";
    private static final String FILE_SEARCH = "search.dat";
    public static final String FILE_SEARCH_ENGINE_ICON = "search_engine_icon";
    private static final String FILE_SHARE_ICON = "shareicon";
    public static final String FILE_SPREAD_DEVICE = ".spreaddevice";
    private static final String FILE_START_PAGE = "startpage.dat";
    private static final String FILE_STAT = "stat.dat";
    private static final String FILE_STAT_PLUGIN = "statthirdplugin.dat";
    private static final String FILE_STAT_SERVICE = "statservice.dat";
    private static final String FILE_SYNC_MERGER = "mttsyncmerger";
    private static final String FILE_USER_INFO = "user.inf";
    private static final String FILE_USER_LEVEL = "mttuserlevel";
    private static final String FILE_USER_SPLASH_INFO = "splash.inf";
    private static final String FILE_WUP = "wup.dat";
    private static final String HOME_SNAPSHOT_FILENAME = "home.dat";
    private static final String SAVE_TO_NET_SERVER = "http://disk.imtt.qq.com/u?action=upload";
    private static final String TAG = "FileUtils";
    public static final int TYPE_DATA = 2;
    public static final int TYPE_DEFAUT = 0;
    public static final int TYPE_SDCARD = 1;
    private static Lock fileLock = new ReentrantLock();
    private static HashMap<String, Integer> mFileIconMap = new HashMap<>();

    public static String getDownloadFilePath(String fileName) {
        File f = getExternalRootDir();
        if (f != null) {
            return f.getAbsolutePath();
        }
        return null;
    }

    public static int getIconTypeId(String fileName) {
        int dotIndex;
        Integer id;
        if (fileName == null || (dotIndex = fileName.lastIndexOf(46)) <= 0 || (id = mFileIconMap.get(fileName.substring(dotIndex + 1).toLowerCase())) == null) {
            return -1;
        }
        return id.intValue();
    }

    public static File getFilesDir() {
        return FileDownload.context.getFilesDir();
    }

    public static File getCacheDir() {
        try {
            return FileDownload.context.getCacheDir();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getDataDir() {
        return getDir(getFilesDir(), "data");
    }

    public static File getPluginDir() {
        return getDir(getFilesDir(), "plugin");
    }

    public static String getChannelConfigPath() {
        return "exclude/channel_conf/";
    }

    public static String getChannelConfigFile() {
        return getChannelConfigPath() + FILE_CHANNEL;
    }

    public static File getDir(File parent, String dirName) {
        if (parent == null || dirName == null || dirName.length() == 0) {
            return null;
        }
        File childDir = new File(parent, dirName);
        if (childDir.exists()) {
            return childDir;
        }
        childDir.mkdirs();
        return childDir;
    }

    public static File getImageCacheDir() {
        return getDir(getCacheDir(), DIR_IMAGES);
    }

    public static File getSkinDir() {
        File dir = getDir(getDataDir(), DIR_SKINS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static boolean hasEmptySpaceInDataDir() {
        if (getDataFreeSpace() > PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            return true;
        }
        return false;
    }

    public static File getExternalRootDir() {
        File childDir = new File(Environment.getExternalStorageDirectory(), DIR_EXT_MAIN);
        if (!childDir.exists()) {
            childDir.mkdirs();
        }
        return childDir;
    }

    public static File getDownloadDir() {
        File childDir = new File(getExternalRootDir(), ".Download");
        if (!childDir.exists()) {
            childDir.mkdirs();
        }
        return childDir;
    }

    public static File getApkDir() {
        File childDir = new File(getExternalRootDir(), "apk");
        if (!childDir.exists()) {
            childDir.mkdirs();
        }
        return childDir;
    }

    public static String getMediaDirPath() {
        return getExternalRootDir().getAbsolutePath() + Constants.URL_PATH_DELIMITER + "Media";
    }

    public static Bitmap getDownloadTypeIcon(String fileName, String filePath) {
        File file;
        Bitmap icon = null;
        if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file2 = new File(filePath, "." + fileName + ".png.tmp");
        if (file2 != null) {
            icon = getImage(file2);
        }
        if (icon != null || (file = new File(filePath, "." + fileName + ".png")) == null) {
            return icon;
        }
        return getImage(file);
    }

    public static void deleteDownloadTypeIconFile(String fileName, String filePath) {
        if (!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(filePath)) {
            File f = new File(filePath, "." + fileName + ".png");
            if (f != null && f.exists()) {
                f.delete();
            }
            File f2 = new File(filePath, "." + fileName + ".png.tmp");
            if (f2 != null && f2.exists()) {
                f2.delete();
            }
        }
    }

    public static void saveDownloadFileTypeIcon(String fileName, String filePath, Bitmap typeIcon) {
        if (!TextUtils.isEmpty(fileName) && typeIcon != null) {
            saveImage(new File(filePath, "." + fileName + ".png.tmp"), typeIcon);
        }
    }

    public static void saveImage(File oriImageFile, File desImageFile, boolean showNotify) {
    }

    public static void saveImage(String url, boolean showNotify) {
    }

    public static boolean saveImage(File imageFile, Bitmap bitmapImage) {
        return true;
    }

    public static boolean isExternalFile(File imageFile) {
        return imageFile != null && imageFile.getAbsolutePath().contains(getExternalStorageDirectory().getAbsolutePath()) && !hasExternalStorage();
    }

    public static Bitmap getImage(File imageFile) {
        Bitmap image = null;
        InputStream in = null;
        if (imageFile != null) {
            try {
                if (imageFile.exists()) {
                    in = openInputStream(imageFile);
                    image = BitmapFactory.decodeStream(in);
                }
            } catch (OutOfMemoryError e) {
                DownloaderLog.d(TAG, "Alert! FileUtils getImage() error! OutOfMemoryError occured!");
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Exception e3) {
                DownloaderLog.e(TAG, "FileUtils getImage() error!", e3);
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                throw th;
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
        }
        return image;
    }

    public static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    public static boolean hasExternalStorage() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static FileInputStream openInputStream(String filePath) throws IOException {
        return openInputStream(new File(filePath));
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        } else if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        } else if (file.canRead()) {
            return new FileInputStream(file);
        } else {
            throw new IOException("File '" + file + "' cannot be read");
        }
    }

    public static boolean isValidExtensionFileName(String extensionName) {
        Integer id = mFileIconMap.get(extensionName);
        if (id == null || id.intValue() == 0) {
            return false;
        }
        return true;
    }

    public static long getSdcardFreeSpace() {
        StatFs sf = new StatFs(getExternalStorageDirectory().getAbsolutePath());
        return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
    }

    public static long getDataFreeSpace() {
        String dataDir = getDataDir().getAbsolutePath();
        try {
            StatFs fileStats = new StatFs(dataDir);
            try {
                fileStats.restat(dataDir);
                StatFs statFs = fileStats;
                return ((long) fileStats.getAvailableBlocks()) * ((long) fileStats.getBlockSize());
            } catch (IllegalArgumentException e) {
                StatFs statFs2 = fileStats;
                return 0;
            }
        } catch (IllegalArgumentException e2) {
            return 0;
        }
    }

    public static String renameFileIfExist(String folderPath, String fileName) {
        String mainName;
        String extName;
        int num;
        if (TextUtils.isEmpty(folderPath) || TextUtils.isEmpty(fileName)) {
            String str = fileName;
            return fileName;
        }
        if (!checkFileName(fileName)) {
            String[] collection = FILE_NAME_VALID_PATTERN.split(fileName);
            StringBuilder name = new StringBuilder();
            for (String str2 : collection) {
                name.append(str2);
            }
            fileName = name.toString();
        }
        if (new File(folderPath, fileName).exists() || new File(folderPath, fileName + DownloadTask.DL_FILE_SUFFIX).exists()) {
            int dotIndex = fileName.lastIndexOf(46);
            if (dotIndex > -1) {
                mainName = fileName.substring(0, dotIndex);
                extName = fileName.substring(dotIndex);
            } else {
                mainName = fileName;
                extName = "";
            }
            String str3 = fileName;
            Matcher matcher = FILE_NAME_PATTERN.matcher(mainName);
            if (matcher.find()) {
                mainName = matcher.group(1);
                num = Integer.parseInt(matcher.group(2));
            } else {
                num = 0;
            }
            while (true) {
                num++;
                String newName = mainName + h.a + num + h.b + extName;
                File file = new File(folderPath, newName);
                File file2 = new File(folderPath, newName + DownloadTask.DL_FILE_SUFFIX);
                if (!file.exists() && !file2.exists()) {
                    String str4 = fileName;
                    return newName;
                }
            }
        } else {
            String str5 = fileName;
            return fileName;
        }
    }

    public static boolean checkFileName(String fileName) {
        return !FILE_NAME_VALID_PATTERN.matcher(fileName).find();
    }

    public static void openLocalFile(Context context, String fileFolder, String filename, String extraForApk) {
        DownloaderLog.d(TAG, "openLocalFile");
        if (!hasExternalStorage()) {
            DownloaderLog.e(TAG, "SD卡没找到!");
        } else if (!TextUtils.isEmpty(fileFolder) && !TextUtils.isEmpty(filename)) {
            File file = new File(fileFolder, filename);
            if (!file.exists()) {
                DownloaderLog.e(TAG, "文件不存在");
                return;
            }
            int start = file.getName().lastIndexOf(46) + 1;
            if (start == -1) {
                start = filename.length();
            }
            String extension = filename.substring(start);
            if (!filename.toLowerCase().endsWith("pdf") || !openPdfFile(file)) {
                openFileBySystem(file.getAbsolutePath(), extraForApk, extension);
            }
        }
    }

    public static void openLocalFile(Context context, String fileFolder, String filename) {
        openLocalFile(context, fileFolder, filename, (String) null);
    }

    private static boolean openPdfFile(File f) {
        return false;
    }

    private static void openFileBySystem(String filePath, String extraForApk, String extension) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (!file.exists()) {
                DownloaderLog.e(TAG, "文件没找到");
                return;
            }
            Intent i = new Intent("android.intent.action.VIEW");
            i.setFlags(DriveFile.MODE_READ_ONLY);
            i.addFlags(1073741824);
            if (!TextUtils.isEmpty(extraForApk)) {
                i.putExtra("android.intent.extra.INSTALLER_PACKAGE_NAME", extraForApk);
            }
            if (TextUtils.isEmpty(extension)) {
                int index = filePath.lastIndexOf(46);
                if (index > -1) {
                    extension = filePath.substring(index + 1);
                } else {
                    extension = "";
                }
            }
            i.setDataAndType(Uri.fromFile(file), MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension));
            final Context context = FileDownload.context;
            try {
                context.startActivity(i);
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        FileUtils.showNotSupportedDialog(context);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public static void showNotSupportedDialog(Context context) {
    }
}
