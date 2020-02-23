package org.jsoup.examples;

import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import java.io.IOException;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListLinks {
    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];
        print("Fetching %s...", url);
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        print("\nMedia: (%d)", Integer.valueOf(media.size()));
        Iterator it = media.iterator();
        while (it.hasNext()) {
            Element src = (Element) it.next();
            if (src.tagName().equals(IMFriendInfoExViber.IMG)) {
                print(" * %s: <%s> %sx%s (%s)", src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"), trim(src.attr("alt"), 20));
            } else {
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
            }
        }
        print("\nImports: (%d)", Integer.valueOf(imports.size()));
        Iterator it2 = imports.iterator();
        while (it2.hasNext()) {
            Element link = (Element) it2.next();
            print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
        }
        print("\nLinks: (%d)", Integer.valueOf(links.size()));
        Iterator it3 = links.iterator();
        while (it3.hasNext()) {
            Element link2 = (Element) it3.next();
            print(" * a: <%s>  (%s)", link2.attr("abs:href"), trim(link2.text(), 35));
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width) {
            return s.substring(0, width - 1) + ".";
        }
        return s;
    }
}
