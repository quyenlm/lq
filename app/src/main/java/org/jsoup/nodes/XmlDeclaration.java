package org.jsoup.nodes;

import org.jsoup.nodes.Document;

public class XmlDeclaration extends Node {
    static final String DECL_KEY = "declaration";
    private final boolean isProcessingInstruction;

    public XmlDeclaration(String data, String baseUri, boolean isProcessingInstruction2) {
        super(baseUri);
        this.attributes.put(DECL_KEY, data);
        this.isProcessingInstruction = isProcessingInstruction2;
    }

    public String nodeName() {
        return "#declaration";
    }

    public String getWholeDeclaration() {
        String decl = this.attributes.get(DECL_KEY);
        if (!decl.equals("xml") || this.attributes.size() <= 1) {
            return this.attributes.get(DECL_KEY);
        }
        StringBuilder sb = new StringBuilder(decl);
        String version = this.attributes.get("version");
        if (version != null) {
            sb.append(" version=\"").append(version).append("\"");
        }
        String encoding = this.attributes.get("encoding");
        if (encoding != null) {
            sb.append(" encoding=\"").append(encoding).append("\"");
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlHead(StringBuilder accum, int depth, Document.OutputSettings out) {
        accum.append("<").append(this.isProcessingInstruction ? "!" : "?").append(getWholeDeclaration()).append(">");
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlTail(StringBuilder accum, int depth, Document.OutputSettings out) {
    }

    public String toString() {
        return outerHtml();
    }
}
