package org.jsoup.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public abstract class Node implements Cloneable {
    private static final List<Node> EMPTY_NODES = Collections.emptyList();
    Attributes attributes;
    String baseUri;
    List<Node> childNodes;
    Node parentNode;
    int siblingIndex;

    public abstract String nodeName();

    /* access modifiers changed from: package-private */
    public abstract void outerHtmlHead(StringBuilder sb, int i, Document.OutputSettings outputSettings);

    /* access modifiers changed from: package-private */
    public abstract void outerHtmlTail(StringBuilder sb, int i, Document.OutputSettings outputSettings);

    protected Node(String baseUri2, Attributes attributes2) {
        Validate.notNull(baseUri2);
        Validate.notNull(attributes2);
        this.childNodes = EMPTY_NODES;
        this.baseUri = baseUri2.trim();
        this.attributes = attributes2;
    }

    protected Node(String baseUri2) {
        this(baseUri2, new Attributes());
    }

    protected Node() {
        this.childNodes = EMPTY_NODES;
        this.attributes = null;
    }

    public String attr(String attributeKey) {
        Validate.notNull(attributeKey);
        if (this.attributes.hasKey(attributeKey)) {
            return this.attributes.get(attributeKey);
        }
        if (attributeKey.toLowerCase().startsWith("abs:")) {
            return absUrl(attributeKey.substring("abs:".length()));
        }
        return "";
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public Node attr(String attributeKey, String attributeValue) {
        this.attributes.put(attributeKey, attributeValue);
        return this;
    }

    public boolean hasAttr(String attributeKey) {
        Validate.notNull(attributeKey);
        if (attributeKey.startsWith("abs:")) {
            String key = attributeKey.substring("abs:".length());
            if (this.attributes.hasKey(key) && !absUrl(key).equals("")) {
                return true;
            }
        }
        return this.attributes.hasKey(attributeKey);
    }

    public Node removeAttr(String attributeKey) {
        Validate.notNull(attributeKey);
        this.attributes.remove(attributeKey);
        return this;
    }

    public String baseUri() {
        return this.baseUri;
    }

    public void setBaseUri(final String baseUri2) {
        Validate.notNull(baseUri2);
        traverse(new NodeVisitor() {
            public void head(Node node, int depth) {
                node.baseUri = baseUri2;
            }

            public void tail(Node node, int depth) {
            }
        });
    }

    public String absUrl(String attributeKey) {
        Validate.notEmpty(attributeKey);
        if (!hasAttr(attributeKey)) {
            return "";
        }
        return StringUtil.resolve(this.baseUri, attr(attributeKey));
    }

    public Node childNode(int index) {
        return this.childNodes.get(index);
    }

    public List<Node> childNodes() {
        return Collections.unmodifiableList(this.childNodes);
    }

    public List<Node> childNodesCopy() {
        List<Node> children = new ArrayList<>(this.childNodes.size());
        for (Node node : this.childNodes) {
            children.add(node.clone());
        }
        return children;
    }

    public final int childNodeSize() {
        return this.childNodes.size();
    }

    /* access modifiers changed from: protected */
    public Node[] childNodesAsArray() {
        return (Node[]) this.childNodes.toArray(new Node[childNodeSize()]);
    }

    public Node parent() {
        return this.parentNode;
    }

    public final Node parentNode() {
        return this.parentNode;
    }

    /* Debug info: failed to restart local var, previous not found, register: 1 */
    public Document ownerDocument() {
        if (this instanceof Document) {
            return (Document) this;
        }
        if (this.parentNode == null) {
            return null;
        }
        return this.parentNode.ownerDocument();
    }

    public void remove() {
        Validate.notNull(this.parentNode);
        this.parentNode.removeChild(this);
    }

    public Node before(String html) {
        addSiblingHtml(this.siblingIndex, html);
        return this;
    }

    public Node before(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex, node);
        return this;
    }

    public Node after(String html) {
        addSiblingHtml(this.siblingIndex + 1, html);
        return this;
    }

    public Node after(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex + 1, node);
        return this;
    }

    private void addSiblingHtml(int index, String html) {
        Validate.notNull(html);
        Validate.notNull(this.parentNode);
        List<Node> nodes = Parser.parseFragment(html, parent() instanceof Element ? (Element) parent() : null, baseUri());
        this.parentNode.addChildren(index, (Node[]) nodes.toArray(new Node[nodes.size()]));
    }

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    public Node wrap(String html) {
        Element context;
        Validate.notEmpty(html);
        if (parent() instanceof Element) {
            context = (Element) parent();
        } else {
            context = null;
        }
        List<Node> wrapChildren = Parser.parseFragment(html, context, baseUri());
        Node wrapNode = wrapChildren.get(0);
        if (wrapNode == null || !(wrapNode instanceof Element)) {
            return null;
        }
        Element wrap = (Element) wrapNode;
        Element deepest = getDeepChild(wrap);
        this.parentNode.replaceChild(this, wrap);
        deepest.addChildren(this);
        if (wrapChildren.size() <= 0) {
            return this;
        }
        for (int i = 0; i < wrapChildren.size(); i++) {
            Node remainder = wrapChildren.get(i);
            remainder.parentNode.removeChild(remainder);
            wrap.appendChild(remainder);
        }
        return this;
    }

    public Node unwrap() {
        Validate.notNull(this.parentNode);
        Node firstChild = this.childNodes.size() > 0 ? this.childNodes.get(0) : null;
        this.parentNode.addChildren(this.siblingIndex, childNodesAsArray());
        remove();
        return firstChild;
    }

    private Element getDeepChild(Element el) {
        List<Element> children = el.children();
        if (children.size() > 0) {
            return getDeepChild(children.get(0));
        }
        return el;
    }

    public void replaceWith(Node in) {
        Validate.notNull(in);
        Validate.notNull(this.parentNode);
        this.parentNode.replaceChild(this, in);
    }

    /* access modifiers changed from: protected */
    public void setParentNode(Node parentNode2) {
        if (this.parentNode != null) {
            this.parentNode.removeChild(this);
        }
        this.parentNode = parentNode2;
    }

    /* access modifiers changed from: protected */
    public void replaceChild(Node out, Node in) {
        Validate.isTrue(out.parentNode == this);
        Validate.notNull(in);
        if (in.parentNode != null) {
            in.parentNode.removeChild(in);
        }
        int index = out.siblingIndex;
        this.childNodes.set(index, in);
        in.parentNode = this;
        in.setSiblingIndex(index);
        out.parentNode = null;
    }

    /* access modifiers changed from: protected */
    public void removeChild(Node out) {
        Validate.isTrue(out.parentNode == this);
        int index = out.siblingIndex;
        this.childNodes.remove(index);
        reindexChildren(index);
        out.parentNode = null;
    }

    /* access modifiers changed from: protected */
    public void addChildren(Node... children) {
        for (Node child : children) {
            reparentChild(child);
            ensureChildNodes();
            this.childNodes.add(child);
            child.setSiblingIndex(this.childNodes.size() - 1);
        }
    }

    /* access modifiers changed from: protected */
    public void addChildren(int index, Node... children) {
        Validate.noNullElements(children);
        for (int i = children.length - 1; i >= 0; i--) {
            Node in = children[i];
            reparentChild(in);
            ensureChildNodes();
            this.childNodes.add(index, in);
        }
        reindexChildren(index);
    }

    /* access modifiers changed from: protected */
    public void ensureChildNodes() {
        if (this.childNodes == EMPTY_NODES) {
            this.childNodes = new ArrayList(4);
        }
    }

    /* access modifiers changed from: protected */
    public void reparentChild(Node child) {
        if (child.parentNode != null) {
            child.parentNode.removeChild(child);
        }
        child.setParentNode(this);
    }

    private void reindexChildren(int start) {
        for (int i = start; i < this.childNodes.size(); i++) {
            this.childNodes.get(i).setSiblingIndex(i);
        }
    }

    public List<Node> siblingNodes() {
        if (this.parentNode == null) {
            return Collections.emptyList();
        }
        List<Node> nodes = this.parentNode.childNodes;
        List<Node> siblings = new ArrayList<>(nodes.size() - 1);
        for (Node node : nodes) {
            if (node != this) {
                siblings.add(node);
            }
        }
        return siblings;
    }

    public Node nextSibling() {
        if (this.parentNode == null) {
            return null;
        }
        List<Node> siblings = this.parentNode.childNodes;
        int index = this.siblingIndex + 1;
        if (siblings.size() > index) {
            return siblings.get(index);
        }
        return null;
    }

    public Node previousSibling() {
        if (this.parentNode != null && this.siblingIndex > 0) {
            return this.parentNode.childNodes.get(this.siblingIndex - 1);
        }
        return null;
    }

    public int siblingIndex() {
        return this.siblingIndex;
    }

    /* access modifiers changed from: protected */
    public void setSiblingIndex(int siblingIndex2) {
        this.siblingIndex = siblingIndex2;
    }

    public Node traverse(NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        new NodeTraversor(nodeVisitor).traverse(this);
        return this;
    }

    public String outerHtml() {
        StringBuilder accum = new StringBuilder(128);
        outerHtml(accum);
        return accum.toString();
    }

    /* access modifiers changed from: protected */
    public void outerHtml(StringBuilder accum) {
        new NodeTraversor(new OuterHtmlVisitor(accum, getOutputSettings())).traverse(this);
    }

    /* access modifiers changed from: package-private */
    public Document.OutputSettings getOutputSettings() {
        return ownerDocument() != null ? ownerDocument().outputSettings() : new Document("").outputSettings();
    }

    public String toString() {
        return outerHtml();
    }

    /* access modifiers changed from: protected */
    public void indent(StringBuilder accum, int depth, Document.OutputSettings out) {
        accum.append("\n").append(StringUtil.padding(out.indentAmount() * depth));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        if (this.childNodes == null ? node.childNodes != null : !this.childNodes.equals(node.childNodes)) {
            return false;
        }
        if (this.attributes != null) {
            if (this.attributes.equals(node.attributes)) {
                return true;
            }
        } else if (node.attributes == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.childNodes != null) {
            result = this.childNodes.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.attributes != null) {
            i = this.attributes.hashCode();
        }
        return i2 + i;
    }

    public Node clone() {
        Node thisClone = doClone((Node) null);
        LinkedList<Node> nodesToProcess = new LinkedList<>();
        nodesToProcess.add(thisClone);
        while (!nodesToProcess.isEmpty()) {
            Node currParent = nodesToProcess.remove();
            for (int i = 0; i < currParent.childNodes.size(); i++) {
                Node childClone = currParent.childNodes.get(i).doClone(currParent);
                currParent.childNodes.set(i, childClone);
                nodesToProcess.add(childClone);
            }
        }
        return thisClone;
    }

    /* access modifiers changed from: protected */
    public Node doClone(Node parent) {
        try {
            Node clone = (Node) super.clone();
            clone.parentNode = parent;
            clone.siblingIndex = parent == null ? 0 : this.siblingIndex;
            clone.attributes = this.attributes != null ? this.attributes.clone() : null;
            clone.baseUri = this.baseUri;
            clone.childNodes = new ArrayList(this.childNodes.size());
            for (Node child : this.childNodes) {
                clone.childNodes.add(child);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class OuterHtmlVisitor implements NodeVisitor {
        private StringBuilder accum;
        private Document.OutputSettings out;

        OuterHtmlVisitor(StringBuilder accum2, Document.OutputSettings out2) {
            this.accum = accum2;
            this.out = out2;
        }

        public void head(Node node, int depth) {
            node.outerHtmlHead(this.accum, depth, this.out);
        }

        public void tail(Node node, int depth) {
            if (!node.nodeName().equals("#text")) {
                node.outerHtmlTail(this.accum, depth, this.out);
            }
        }
    }
}
