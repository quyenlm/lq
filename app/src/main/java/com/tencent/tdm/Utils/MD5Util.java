package com.tencent.tdm.Utils;

import android.content.Context;
import com.tencent.tdm.system.TXLog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static String TAG = "TUtils";

    public static byte[] encode(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            return digest.digest();
        } catch (Exception e) {
            return null;
        }
    }

    public static String encode2HexStr(byte[] bytes) {
        return HexUtil.bytes2HexStr(encode(bytes));
    }

    public static String encode2Base64(byte[] bytes) {
        return Base64Util.encode(encode(bytes));
    }

    public static byte[] encodeFile(String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            while (true) {
                try {
                    int readed = fis.read(buffer);
                    if (readed != -1) {
                        digest.update(buffer, 0, readed);
                    } else {
                        byte[] digesta = digest.digest();
                        try {
                            fis.close();
                            return digesta;
                        } catch (IOException e) {
                            TXLog.d(TAG, "IOException: " + e.getMessage());
                            return digesta;
                        }
                    }
                } catch (IOException e2) {
                    TXLog.d(TAG, "IOEXCeption: " + e2.getMessage());
                    try {
                        fis.close();
                        return null;
                    } catch (IOException e3) {
                        TXLog.d(TAG, "IOException: " + e3.getMessage());
                        return null;
                    }
                } catch (Throwable th) {
                    try {
                        fis.close();
                    } catch (IOException e4) {
                        TXLog.d(TAG, "IOException: " + e4.getMessage());
                    }
                    throw th;
                }
            }
        } catch (FileNotFoundException e5) {
            TXLog.d(TAG, "FileNotFoundException : " + e5.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e6) {
            TXLog.d(TAG, "NoSuchAlgorithmException : " + e6.getMessage());
            return null;
        }
    }

    public static byte[] encodeAssetsFile(Context context, String assetsFilePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            InputStream is = null;
            try {
                is = context.getAssets().open(assetsFilePath);
                while (true) {
                    int readed = is.read(buffer);
                    if (readed == -1) {
                        break;
                    }
                    digest.update(buffer, 0, readed);
                }
                byte[] digesta = digest.digest();
                if (is == null) {
                    return digesta;
                }
                try {
                    is.close();
                    return digesta;
                } catch (IOException e) {
                    TXLog.i(TAG, "IOEXCeption: " + e.getMessage());
                    TXLog.e(TAG, "IOEXCeption");
                    return digesta;
                }
            } catch (FileNotFoundException e2) {
                TXLog.i(TAG, "FileNotFoundException，may not be noticed if not using the encrypt function");
                if (is == null) {
                    return null;
                }
                try {
                    is.close();
                    return null;
                } catch (IOException e3) {
                    TXLog.i(TAG, "IOEXCeption: " + e3.getMessage());
                    TXLog.e(TAG, "IOEXCeption");
                    return null;
                }
            } catch (IOException e4) {
                TXLog.i(TAG, "IOEXCeption: " + e4.getMessage());
                TXLog.e(TAG, "IOEXCeption");
                if (is == null) {
                    return null;
                }
                try {
                    is.close();
                    return null;
                } catch (IOException e5) {
                    TXLog.i(TAG, "IOEXCeption: " + e5.getMessage());
                    TXLog.e(TAG, "IOEXCeption");
                    return null;
                }
            } catch (Throwable th) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e6) {
                        TXLog.i(TAG, "IOEXCeption: " + e6.getMessage());
                        TXLog.e(TAG, "IOEXCeption");
                    }
                }
                throw th;
            }
        } catch (NoSuchAlgorithmException e7) {
            TXLog.i(TAG, "NoSuchAlgorithmException: " + e7.getMessage());
            TXLog.e(TAG, "NoSuchAlgorithmException");
            return null;
        }
    }

    public static String encodeFile2HexStr(String filePath) {
        return HexUtil.bytes2HexStr(encodeFile(filePath));
    }

    public static String encodeAssets2HexStr(Context context, String assetsFilePath) {
        return HexUtil.bytes2HexStr(encodeAssetsFile(context, assetsFilePath));
    }

    public static String encodeFile2Base64(String filePath) {
        byte[] bytes = encodeFile(filePath);
        if (bytes == null) {
            return null;
        }
        return Base64Util.encode(bytes);
    }
}
