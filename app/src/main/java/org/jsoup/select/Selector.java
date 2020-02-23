package org.jsoup.select;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;

public class Selector {
    private final Evaluator evaluator;
    private final Element root;

    private Selector(String query, Element root2) {
        Validate.notNull(query);
        String query2 = query.trim();
        Validate.notEmpty(query2);
        Validate.notNull(root2);
        this.evaluator = QueryParser.parse(query2);
        this.root = root2;
    }

    private Selector(Evaluator evaluator2, Element root2) {
        Validate.notNull(evaluator2);
        Validate.notNull(root2);
        this.evaluator = evaluator2;
        this.root = root2;
    }

    public static Elements select(String query, Element root2) {
        return new Selector(query, root2).select();
    }

    public static Elements select(Evaluator evaluator2, Element root2) {
        return new Selector(evaluator2, root2).select();
    }

    public static Elements select(String query, Iterable<Element> roots) {
        Validate.notEmpty(query);
        Validate.notNull(roots);
        Evaluator evaluator2 = QueryParser.parse(query);
        LinkedHashSet<Element> elements = new LinkedHashSet<>();
        for (Element root2 : roots) {
            elements.addAll(select(evaluator2, root2));
        }
        return new Elements((Collection<Element>) elements);
    }

    private Elements select() {
        return Collector.collect(this.evaluator, this.root);
    }

    static Elements filterOut(Collection<Element> elements, Collection<Element> outs) {
        Elements output = new Elements();
        for (Element el : elements) {
            boolean found = false;
            Iterator<Element> it = outs.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (el.equals(it.next())) {
                        found = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!found) {
                output.add(el);
            }
        }
        return output;
    }

    public static class SelectorParseException extends IllegalStateException {
        public SelectorParseException(String msg, Object... params) {
            super(String.format(msg, params));
        }
    }
}
