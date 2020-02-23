package org.jsoup.parser;

import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Token;

public class XmlTreeBuilder extends TreeBuilder {
    public /* bridge */ /* synthetic */ boolean processStartTag(String str, Attributes attributes) {
        return super.processStartTag(str, attributes);
    }

    /* access modifiers changed from: protected */
    public void initialiseParse(String input, String baseUri, ParseErrorList errors) {
        super.initialiseParse(input, baseUri, errors);
        this.stack.add(this.doc);
        this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    }

    /* access modifiers changed from: protected */
    public boolean process(Token token) {
        switch (token.type) {
            case StartTag:
                insert(token.asStartTag());
                return true;
            case EndTag:
                popStackToClose(token.asEndTag());
                return true;
            case Comment:
                insert(token.asComment());
                return true;
            case Character:
                insert(token.asCharacter());
                return true;
            case Doctype:
                insert(token.asDoctype());
                return true;
            case EOF:
                return true;
            default:
                Validate.fail("Unexpected token type: " + token.type);
                return true;
        }
    }

    private void insertNode(Node node) {
        currentElement().appendChild(node);
    }

    /* access modifiers changed from: package-private */
    public Element insert(Token.StartTag startTag) {
        Tag tag = Tag.valueOf(startTag.name());
        Element el = new Element(tag, this.baseUri, startTag.attributes);
        insertNode(el);
        if (startTag.isSelfClosing()) {
            this.tokeniser.acknowledgeSelfClosingFlag();
            if (!tag.isKnownTag()) {
                tag.setSelfClosing();
            }
        } else {
            this.stack.add(el);
        }
        return el;
    }

    /* access modifiers changed from: package-private */
    public void insert(Token.Comment commentToken) {
        Comment comment = new Comment(commentToken.getData(), this.baseUri);
        Node insert = comment;
        if (commentToken.bogus) {
            String data = comment.getData();
            if (data.length() > 1 && (data.startsWith("!") || data.startsWith("?"))) {
                insert = new XmlDeclaration(data.substring(1), comment.baseUri(), data.startsWith("!"));
            }
        }
        insertNode(insert);
    }

    /* access modifiers changed from: package-private */
    public void insert(Token.Character characterToken) {
        insertNode(new TextNode(characterToken.getData(), this.baseUri));
    }

    /* access modifiers changed from: package-private */
    public void insert(Token.Doctype d) {
        insertNode(new DocumentType(d.getName(), d.getPublicIdentifier(), d.getSystemIdentifier(), this.baseUri));
    }

    private void popStackToClose(Token.EndTag endTag) {
        String elName = endTag.name();
        Element firstFound = null;
        int pos = this.stack.size() - 1;
        while (true) {
            if (pos < 0) {
                break;
            }
            Element next = (Element) this.stack.get(pos);
            if (next.nodeName().equals(elName)) {
                firstFound = next;
                break;
            }
            pos--;
        }
        if (firstFound != null) {
            int pos2 = this.stack.size() - 1;
            while (pos2 >= 0) {
                Element next2 = (Element) this.stack.get(pos2);
                this.stack.remove(pos2);
                if (next2 != firstFound) {
                    pos2--;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public List<Node> parseFragment(String inputFragment, String baseUri, ParseErrorList errors) {
        initialiseParse(inputFragment, baseUri, errors);
        runParser();
        return this.doc.childNodes();
    }
}
