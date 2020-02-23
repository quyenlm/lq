package com.amazonaws.services.s3.internal;

import com.amazonaws.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class XmlWriter {
    static final /* synthetic */ boolean $assertionsDisabled = (!XmlWriter.class.desiredAssertionStatus());
    StringBuilder sb = new StringBuilder();
    List<String> tags = new ArrayList();

    public XmlWriter start(String name) {
        this.sb.append("<").append(name).append(">");
        this.tags.add(name);
        return this;
    }

    public XmlWriter start(String name, String attr, String value) {
        this.sb.append("<").append(name);
        writeAttr(attr, value);
        this.sb.append(">");
        this.tags.add(name);
        return this;
    }

    public XmlWriter start(String name, String[] attrs, String[] values) {
        this.sb.append("<").append(name);
        for (int i = 0; i < Math.min(attrs.length, values.length); i++) {
            writeAttr(attrs[i], values[i]);
        }
        this.sb.append(">");
        this.tags.add(name);
        return this;
    }

    public XmlWriter end() {
        if ($assertionsDisabled || this.tags.size() > 0) {
            this.sb.append("</").append(this.tags.remove(this.tags.size() - 1)).append(">");
            return this;
        }
        throw new AssertionError();
    }

    public byte[] getBytes() {
        if ($assertionsDisabled || this.tags.size() == 0) {
            return toString().getBytes(StringUtils.UTF8);
        }
        throw new AssertionError();
    }

    public String toString() {
        return this.sb.toString();
    }

    public XmlWriter value(String value) {
        appendEscapedString(value, this.sb);
        return this;
    }

    private void writeAttr(String name, String value) {
        this.sb.append(' ').append(name).append("=\"");
        appendEscapedString(value, this.sb);
        this.sb.append("\"");
    }

    private void appendEscapedString(String s, StringBuilder builder) {
        String escape;
        if (s == null) {
            s = "";
        }
        int start = 0;
        int len = s.length();
        int pos = 0;
        while (pos < len) {
            switch (s.charAt(pos)) {
                case 9:
                    escape = "&#9;";
                    break;
                case 10:
                    escape = "&#10;";
                    break;
                case 13:
                    escape = "&#13;";
                    break;
                case '\"':
                    escape = "&quot;";
                    break;
                case '&':
                    escape = "&amp;";
                    break;
                case '<':
                    escape = "&lt;";
                    break;
                case '>':
                    escape = "&gt;";
                    break;
                default:
                    escape = null;
                    break;
            }
            if (escape != null) {
                if (start < pos) {
                    builder.append(s, start, pos);
                }
                this.sb.append(escape);
                start = pos + 1;
            }
            pos++;
        }
        if (start < pos) {
            this.sb.append(s, start, pos);
        }
    }
}
