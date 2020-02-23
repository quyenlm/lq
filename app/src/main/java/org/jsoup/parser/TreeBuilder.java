package org.jsoup.parser;

import java.util.ArrayList;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Token;

abstract class TreeBuilder {
    protected String baseUri;
    protected Token currentToken;
    protected Document doc;
    private Token.EndTag end = new Token.EndTag();
    protected ParseErrorList errors;
    CharacterReader reader;
    protected ArrayList<Element> stack;
    private Token.StartTag start = new Token.StartTag();
    Tokeniser tokeniser;

    /* access modifiers changed from: protected */
    public abstract boolean process(Token token);

    TreeBuilder() {
    }

    /* access modifiers changed from: protected */
    public void initialiseParse(String input, String baseUri2, ParseErrorList errors2) {
        Validate.notNull(input, "String input must not be null");
        Validate.notNull(baseUri2, "BaseURI must not be null");
        this.doc = new Document(baseUri2);
        this.reader = new CharacterReader(input);
        this.errors = errors2;
        this.tokeniser = new Tokeniser(this.reader, errors2);
        this.stack = new ArrayList<>(32);
        this.baseUri = baseUri2;
    }

    /* access modifiers changed from: package-private */
    public Document parse(String input, String baseUri2) {
        return parse(input, baseUri2, ParseErrorList.noTracking());
    }

    /* access modifiers changed from: package-private */
    public Document parse(String input, String baseUri2, ParseErrorList errors2) {
        initialiseParse(input, baseUri2, errors2);
        runParser();
        return this.doc;
    }

    /* access modifiers changed from: protected */
    public void runParser() {
        Token token;
        do {
            token = this.tokeniser.read();
            process(token);
            token.reset();
        } while (token.type != Token.TokenType.EOF);
    }

    /* access modifiers changed from: protected */
    public boolean processStartTag(String name) {
        if (this.currentToken == this.start) {
            return process(new Token.StartTag().name(name));
        }
        return process(this.start.reset().name(name));
    }

    public boolean processStartTag(String name, Attributes attrs) {
        if (this.currentToken == this.start) {
            return process(new Token.StartTag().nameAttr(name, attrs));
        }
        this.start.reset();
        this.start.nameAttr(name, attrs);
        return process(this.start);
    }

    /* access modifiers changed from: protected */
    public boolean processEndTag(String name) {
        if (this.currentToken == this.end) {
            return process(new Token.EndTag().name(name));
        }
        return process(this.end.reset().name(name));
    }

    /* access modifiers changed from: protected */
    public Element currentElement() {
        int size = this.stack.size();
        if (size > 0) {
            return this.stack.get(size - 1);
        }
        return null;
    }
}
