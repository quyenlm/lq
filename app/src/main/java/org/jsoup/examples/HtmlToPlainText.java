package org.jsoup.examples;

import com.facebook.share.internal.ShareConstants;
import com.tencent.midas.oversea.comm.APDataReportManager;
import java.io.IOException;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class HtmlToPlainText {
    private static final int timeout = 5000;
    private static final String userAgent = "Mozilla/5.0 (jsoup)";

    public static void main(String... args) throws IOException {
        boolean z;
        if (args.length == 1 || args.length == 2) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "usage: java -cp jsoup.jar org.jsoup.examples.HtmlToPlainText url [selector]");
        String url = args[0];
        String selector = args.length == 2 ? args[1] : null;
        Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(5000).get();
        HtmlToPlainText formatter = new HtmlToPlainText();
        if (selector != null) {
            Iterator it = doc.select(selector).iterator();
            while (it.hasNext()) {
                System.out.println(formatter.getPlainText((Element) it.next()));
            }
            return;
        }
        System.out.println(formatter.getPlainText(doc));
    }

    public String getPlainText(Element element) {
        FormattingVisitor formatter = new FormattingVisitor();
        new NodeTraversor(formatter).traverse(element);
        return formatter.toString();
    }

    private class FormattingVisitor implements NodeVisitor {
        private static final int maxWidth = 80;
        private StringBuilder accum;
        private int width;

        private FormattingVisitor() {
            this.width = 0;
            this.accum = new StringBuilder();
        }

        public void head(Node node, int depth) {
            String name = node.nodeName();
            if (node instanceof TextNode) {
                append(((TextNode) node).text());
            } else if (name.equals("li")) {
                append("\n * ");
            } else if (name.equals("dt")) {
                append("  ");
            } else {
                if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5", "tr")) {
                    append("\n");
                }
            }
        }

        public void tail(Node node, int depth) {
            String name = node.nodeName();
            if (StringUtil.in(name, "br", "dd", "dt", "p", "h1", "h2", "h3", "h4", "h5")) {
                append("\n");
            } else if (name.equals(APDataReportManager.GAMEANDMONTHSLIST_PRE)) {
                append(String.format(" <%s>", new Object[]{node.absUrl(ShareConstants.WEB_DIALOG_PARAM_HREF)}));
            }
        }

        private void append(String text) {
            boolean last;
            if (text.startsWith("\n")) {
                this.width = 0;
            }
            if (text.equals(" ")) {
                if (this.accum.length() != 0) {
                    if (StringUtil.in(this.accum.substring(this.accum.length() - 1), " ", "\n")) {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (text.length() + this.width > 80) {
                String[] words = text.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    if (i == words.length - 1) {
                        last = true;
                    } else {
                        last = false;
                    }
                    if (!last) {
                        word = word + " ";
                    }
                    if (word.length() + this.width > 80) {
                        this.accum.append("\n").append(word);
                        this.width = word.length();
                    } else {
                        this.accum.append(word);
                        this.width += word.length();
                    }
                }
                return;
            }
            this.accum.append(text);
            this.width += text.length();
        }

        public String toString() {
            return this.accum.toString();
        }
    }
}
