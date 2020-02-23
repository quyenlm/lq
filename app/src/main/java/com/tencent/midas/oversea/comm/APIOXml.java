package com.tencent.midas.oversea.comm;

import android.os.Environment;
import android.util.Xml;
import com.vk.sdk.api.model.VKApiUserFull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

public class APIOXml {
    public static File getFilePath(String str, String str2) {
        makeRootDirectory(str);
        try {
            return new File(str + str2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void makeRootDirectory(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }
    }

    public String readInfoFromXML(String str, String str2) {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                NodeList elementsByTagName = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(Environment.getExternalStorageDirectory().getCanonicalPath() + "/Tencent/" + str)).getElementsByTagName(VKApiUserFull.GAMES);
                if (elementsByTagName != null) {
                    return ((Element) ((Element) elementsByTagName.item(0)).getElementsByTagName(str2).item(0)).getFirstChild().getNodeValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String writeInfoToString(String str, String str2) {
        XmlSerializer newSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        try {
            newSerializer.setOutput(stringWriter);
            newSerializer.startDocument("utf-8", true);
            newSerializer.startTag("", VKApiUserFull.GAMES);
            newSerializer.startTag("", str);
            newSerializer.text(str2);
            newSerializer.endTag("", str);
            newSerializer.endTag("", VKApiUserFull.GAMES);
            newSerializer.endDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public boolean writeToXml(String str, String str2) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getFilePath(Environment.getExternalStorageDirectory().toString() + "/Tencent/", str2));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            try {
                outputStreamWriter.write(str);
                outputStreamWriter.close();
                fileOutputStream.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e2) {
            return false;
        }
    }
}
