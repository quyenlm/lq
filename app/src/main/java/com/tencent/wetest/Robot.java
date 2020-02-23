package com.tencent.wetest;

import android.os.Process;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

public class Robot extends Thread {
    public static int a = 5;
    public static String b = "wetest";
    public static boolean c = false;
    public byte[] d = new byte[4096];
    ByteBuffer e = ByteBuffer.allocate(4096);
    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
    private Socket g = null;
    private InputStream h = null;
    private OutputStream i = null;
    private int j = 0;
    private c k = new c(this);
    private final int l = 4096;
    private g m = null;

    public static void startAutomation() {
        try {
            if (c) {
                Log.i(b, "robot already running.");
                return;
            }
            new Robot().start();
            Log.i(b, "robot started.");
            c = true;
        } catch (Throwable th) {
            Log.i(b, th.getMessage(), th);
        }
    }

    public View a(d dVar) {
        if (this.m == null) {
            this.m = new g();
        }
        ArrayList<View> a2 = this.m.a(true);
        if (a2 == null) {
            Log.d(b, "views = null");
            return null;
        }
        int i2 = 0;
        for (View view : a2) {
            if (view != null && view.getVisibility() == 0 && view.isShown() && b(view, dVar) && a(view, dVar)) {
                if (i2 == dVar.c) {
                    return view;
                }
                i2++;
            }
        }
        return null;
    }

    public void a(int i2, String str) {
        XmlSerializer newSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        newSerializer.setOutput(stringWriter);
        newSerializer.startDocument("UTF-8", true);
        newSerializer.startTag("", "commandresult");
        newSerializer.startTag("", GGLiveConstants.PARAM.RESULT);
        newSerializer.text(String.valueOf(i2));
        newSerializer.endTag("", GGLiveConstants.PARAM.RESULT);
        newSerializer.startTag("", "sequence");
        newSerializer.text(this.k.c);
        newSerializer.endTag("", "sequence");
        newSerializer.startTag("", "description");
        if (str != null) {
            newSerializer.text(str);
        }
        newSerializer.endTag("", "description");
        newSerializer.endTag("", "commandresult");
        newSerializer.endDocument();
        byte[] bytes = stringWriter.toString().getBytes();
        this.e.clear();
        this.e.putInt(0);
        this.e.putInt(this.j);
        this.e.putInt(bytes.length);
        if (bytes.length > 0) {
            this.e.put(bytes);
        }
        int position = this.e.position();
        this.e.putInt(0, position);
        a("sendResult: " + stringWriter.toString());
        this.i.write(this.e.array(), 0, position);
    }

    public void a(String str) {
        if (a <= 0) {
            Log.d(b, str);
        }
    }

    public void a(String str, String str2) {
        if (str.equals("loglevel")) {
            try {
                a = Integer.parseInt(str2);
            } catch (NumberFormatException e2) {
            }
        } else {
            Log.i(b, String.valueOf(str) + HttpRequest.HTTP_REQ_ENTITY_MERGE + str2);
        }
    }

    /* access modifiers changed from: protected */
    public void a(Element element) {
        NodeList childNodes = element.getChildNodes();
        int length = childNodes.getLength();
        for (int i2 = 0; i2 < length; i2++) {
            Node item = childNodes.item(i2);
            if (item instanceof Element) {
                Element element2 = (Element) item;
                if (element2.getTagName().equals("containtext")) {
                    this.k.a.a = element2.getTextContent().trim();
                } else if (element2.getTagName().equals(FirebaseAnalytics.Param.INDEX)) {
                    try {
                        this.k.a.c = Integer.parseInt(element2.getTextContent().trim());
                    } catch (NumberFormatException e2) {
                        a("error index: " + element2.getTextContent());
                    }
                } else if (element2.getTagName().equals("classtype")) {
                    this.k.a.b = element2.getTextContent().trim();
                } else {
                    a("unknown find expr node: " + element2.getTagName());
                }
            }
        }
    }

    public boolean a() {
        try {
            File file = new File("/data/local/tmp/qqlogin.cfg");
            if (!file.isFile() || !file.exists()) {
                System.out.println("can't find /data/local/tmp/qqlogin.cfg");
                return false;
            }
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    inputStreamReader.close();
                    return true;
                }
                System.out.println(readLine);
                b(readLine);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009c, code lost:
        r1 = ((android.widget.EditText) r5).getText();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.view.View r5, com.tencent.wetest.d r6) {
        /*
            r4 = this;
            r0 = 1
            java.lang.String r1 = r6.a
            int r1 = r1.length()
            if (r1 != 0) goto L_0x000a
        L_0x0009:
            return r0
        L_0x000a:
            java.lang.CharSequence r1 = r5.getContentDescription()
            if (r1 == 0) goto L_0x0020
            java.lang.CharSequence r1 = r5.getContentDescription()
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = r6.a
            boolean r1 = r1.contains(r2)
            if (r1 != 0) goto L_0x0009
        L_0x0020:
            boolean r1 = r5 instanceof android.widget.TextView
            if (r1 == 0) goto L_0x00b0
            android.widget.TextView r5 = (android.widget.TextView) r5
            java.lang.CharSequence r1 = r5.getText()
            if (r1 == 0) goto L_0x0046
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "TEXT: "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r2 = r2.toString()
            r4.a((java.lang.String) r2)
            java.lang.String r2 = r6.a
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0009
        L_0x0046:
            java.lang.CharSequence r1 = r5.getHint()
            if (r1 == 0) goto L_0x006a
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Hint:"
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r2 = r2.toString()
            r4.a((java.lang.String) r2)
            java.lang.String r2 = r6.a
            boolean r1 = r1.contains(r2)
            if (r1 != 0) goto L_0x0009
        L_0x006a:
            java.lang.String r1 = r6.a
            java.lang.String r2 = "密码"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x007e
            java.lang.String r1 = r6.a
            java.lang.String r2 = "password"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0098
        L_0x007e:
            int r1 = r5.getInputType()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "InputType: "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r2 = r2.toString()
            r4.a((java.lang.String) r2)
            r1 = r1 & 240(0xf0, float:3.36E-43)
            if (r1 != 0) goto L_0x0009
        L_0x0098:
            boolean r1 = r5 instanceof android.widget.EditText
            if (r1 == 0) goto L_0x00b0
            android.widget.EditText r5 = (android.widget.EditText) r5
            android.text.Editable r1 = r5.getText()
            if (r1 == 0) goto L_0x00b0
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = r6.a
            boolean r1 = r1.contains(r2)
            if (r1 != 0) goto L_0x0009
        L_0x00b0:
            r0 = 0
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wetest.Robot.a(android.view.View, com.tencent.wetest.d):boolean");
    }

    public boolean a(Class cls, String str) {
        Class superclass;
        String simpleName = cls.getSimpleName();
        if (simpleName.equals(str)) {
            return true;
        }
        if (simpleName.equals("View") || (superclass = cls.getSuperclass()) == null) {
            return false;
        }
        return a(superclass, str);
    }

    /* access modifiers changed from: protected */
    public void b() {
        try {
            if (this.g != null) {
                this.g.close();
                this.g = null;
            }
            if (this.h != null) {
                this.h.close();
                this.h = null;
            }
            if (this.i != null) {
                this.i.close();
                this.i = null;
            }
        } catch (IOException e2) {
        }
    }

    public void b(String str) {
        int indexOf;
        String trim = str.trim();
        if (trim != null && trim.length() != 0 && (indexOf = trim.indexOf(61)) != -1) {
            String substring = trim.substring(0, indexOf);
            String substring2 = trim.substring(indexOf + 1);
            if (substring2 == null) {
                substring2 = "";
            }
            a(substring.trim(), substring2.trim());
        }
    }

    public boolean b(View view, d dVar) {
        if (dVar.b.length() == 0) {
            return true;
        }
        return a((Class) view.getClass(), dVar.b);
    }

    /* access modifiers changed from: protected */
    public void c() {
        this.g = new Socket();
        this.g.connect(new InetSocketAddress("127.0.0.1", 26560));
        this.h = this.g.getInputStream();
        this.i = this.g.getOutputStream();
    }

    public int d() {
        DataInputStream dataInputStream = new DataInputStream(this.h);
        int readInt = dataInputStream.readInt();
        if (readInt <= 0) {
            throw new RuntimeException("recv 4 bytes fail, len:" + readInt);
        }
        int i2 = readInt - 4;
        int i3 = 0;
        while (i2 > 0) {
            int read = dataInputStream.read(this.d, i3, i2);
            if (read <= 0) {
                throw new RuntimeException("recv " + i2 + " bytes fail");
            }
            i2 -= read;
            i3 += read;
        }
        return readInt - 4;
    }

    /* access modifiers changed from: protected */
    public void e() {
        this.e.clear();
        this.e.putInt(0);
        this.e.putShort(169);
        this.e.put((byte) 1);
        this.e.putInt(0);
        this.e.putInt(0);
        this.e.putInt(Process.myPid());
        int position = this.e.position();
        this.e.putInt(0, position);
        this.i.write(this.e.array(), 0, position);
        a("register to wetest...");
        ByteBuffer wrap = ByteBuffer.wrap(this.d, 0, d());
        short s = wrap.getShort();
        wrap.get();
        wrap.getInt();
        int i2 = wrap.getInt();
        if (s != 170) {
            throw new RuntimeException("register to wetest but get unexpected response: " + s);
        } else if (i2 != 0) {
            throw new RuntimeException("register to wetest but get error: " + i2);
        } else {
            a("register to wetest ok!");
        }
    }

    /* access modifiers changed from: protected */
    public void f() {
        a("parseCommand");
        ByteBuffer wrap = ByteBuffer.wrap(this.d, 0, d());
        this.j = wrap.getInt();
        int i2 = wrap.getInt();
        if (i2 > 2048) {
            throw new RuntimeException("xml is too long: " + i2);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.d, 8, i2);
        a("parseCommand:" + new String(this.d, 8, i2));
        NodeList childNodes = this.f.newDocumentBuilder().parse(byteArrayInputStream).getDocumentElement().getChildNodes();
        this.k.a();
        int length = childNodes.getLength();
        for (int i3 = 0; i3 < length; i3++) {
            Node item = childNodes.item(i3);
            if (item instanceof Element) {
                Element element = (Element) item;
                if (element.getTagName().equals("action")) {
                    this.k.b = element.getTextContent().trim();
                } else if (element.getTagName().equals("findexpr")) {
                    a(element);
                } else if (element.getTagName().equals("sequence")) {
                    this.k.c = element.getTextContent().trim();
                } else if (element.getTagName().equals("param")) {
                    this.k.d = element;
                } else {
                    a("unknown node: " + element.getTagName());
                }
            }
        }
    }

    public void g() {
        if (this.k.b()) {
            View a2 = a(this.k.a);
            if ("touch".equals(this.k.b)) {
                if (a2 == null) {
                    a(1, "can't found: " + this.k.a);
                    return;
                }
                a2.post(new a(this, a2));
                a(0, "");
            } else if ("input".equals(this.k.b)) {
                if (a2 == null) {
                    a(1, "can't found: " + this.k.a);
                } else if (a2 instanceof EditText) {
                    EditText editText = (EditText) a2;
                    editText.post(new b(this, editText, this.k.d.getAttribute(ContentType.TYPE_TEXT)));
                    a(0, "");
                } else {
                    a(3, "not inputable.");
                }
            } else if ("exist".equals(this.k.b)) {
                a(a2 == null ? 1 : 0, "");
            } else {
                a(2, "unknown action: " + this.k.b);
            }
        }
    }

    public void h() {
        while (true) {
            f();
            g();
        }
    }

    public void run() {
        try {
            a();
        } catch (Throwable th) {
            Log.e(b, th.getMessage(), th);
        }
        while (true) {
            try {
                c();
                e();
                h();
            } catch (Throwable th2) {
            }
            b();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e2) {
            }
        }
    }
}
