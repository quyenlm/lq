package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.HeaderIterator;
import org.apache.http.ParseException;
import org.apache.http.TokenIterator;

@Deprecated
public class BasicTokenIterator implements TokenIterator {
    public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
    protected String currentHeader;
    protected String currentToken;
    protected final HeaderIterator headerIt;
    protected int searchPos;

    public BasicTokenIterator(HeaderIterator headerIterator) {
        throw new RuntimeException("Stub!");
    }

    public boolean hasNext() {
        throw new RuntimeException("Stub!");
    }

    public String nextToken() throws NoSuchElementException, ParseException {
        throw new RuntimeException("Stub!");
    }

    public final Object next() throws NoSuchElementException, ParseException {
        throw new RuntimeException("Stub!");
    }

    public final void remove() throws UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int findNext(int from) throws ParseException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public String createToken(String value, int start, int end) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int findTokenStart(int from) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int findTokenSeparator(int from) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int findTokenEnd(int from) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean isTokenSeparator(char ch) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean isWhitespace(char ch) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean isTokenChar(char ch) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean isHttpSeparator(char ch) {
        throw new RuntimeException("Stub!");
    }
}
