package org.jsoup.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class FormElement extends Element {
    private final Elements elements = new Elements();

    public FormElement(Tag tag, String baseUri, Attributes attributes) {
        super(tag, baseUri, attributes);
    }

    public Elements elements() {
        return this.elements;
    }

    public FormElement addElement(Element element) {
        this.elements.add(element);
        return this;
    }

    public Connection submit() {
        String action = hasAttr("action") ? absUrl("action") : baseUri();
        Validate.notEmpty(action, "Could not determine a form action URL for submit. Ensure you set a base URI when parsing.");
        return Jsoup.connect(action).data((Collection<Connection.KeyVal>) formData()).method(attr("method").toUpperCase().equals("POST") ? Connection.Method.POST : Connection.Method.GET);
    }

    public List<Connection.KeyVal> formData() {
        Element option;
        ArrayList<Connection.KeyVal> data = new ArrayList<>();
        Iterator it = this.elements.iterator();
        while (it.hasNext()) {
            Element el = (Element) it.next();
            if (el.tag().isFormSubmittable() && !el.hasAttr("disabled")) {
                String name = el.attr("name");
                if (name.length() != 0) {
                    String type = el.attr("type");
                    if ("select".equals(el.tagName())) {
                        boolean set = false;
                        Iterator it2 = el.select("option[selected]").iterator();
                        while (it2.hasNext()) {
                            data.add(HttpConnection.KeyVal.create(name, ((Element) it2.next()).val()));
                            set = true;
                        }
                        if (!set && (option = el.select("option").first()) != null) {
                            data.add(HttpConnection.KeyVal.create(name, option.val()));
                        }
                    } else if (!"checkbox".equalsIgnoreCase(type) && !"radio".equalsIgnoreCase(type)) {
                        data.add(HttpConnection.KeyVal.create(name, el.val()));
                    } else if (el.hasAttr("checked")) {
                        data.add(HttpConnection.KeyVal.create(name, el.val().length() > 0 ? el.val() : "on"));
                    }
                }
            }
        }
        return data;
    }
}
