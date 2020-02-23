package com.amazonaws.services.s3.model.transform;

import java.util.Iterator;
import java.util.LinkedList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

abstract class AbstractHandler extends DefaultHandler {
    private final LinkedList<String> context = new LinkedList<>();
    private final StringBuilder text = new StringBuilder();

    /* access modifiers changed from: protected */
    public abstract void doEndElement(String str, String str2, String str3);

    /* access modifiers changed from: protected */
    public abstract void doStartElement(String str, String str2, String str3, Attributes attributes);

    AbstractHandler() {
    }

    public final void startElement(String uri, String name, String qName, Attributes attrs) {
        this.text.setLength(0);
        doStartElement(uri, name, qName, attrs);
        this.context.add(name);
    }

    public final void endElement(String uri, String name, String qName) {
        this.context.removeLast();
        doEndElement(uri, name, qName);
    }

    public final void characters(char[] ch, int start, int length) {
        this.text.append(ch, start, length);
    }

    /* access modifiers changed from: protected */
    public final String getText() {
        return this.text.toString();
    }

    /* access modifiers changed from: protected */
    public final boolean atTopLevel() {
        return this.context.isEmpty();
    }

    /* access modifiers changed from: protected */
    public final boolean in(String... path) {
        if (path.length != this.context.size()) {
            return false;
        }
        int i = 0;
        Iterator it = this.context.iterator();
        while (it.hasNext()) {
            String element = (String) it.next();
            String pattern = path[i];
            if (!pattern.equals("*") && !pattern.equals(element)) {
                return false;
            }
            i++;
        }
        return true;
    }
}
