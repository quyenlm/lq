package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;

@Deprecated
public class BasicHeaderIterator implements HeaderIterator {
    protected final Header[] allHeaders = null;
    protected int currentIndex;
    protected String headerName;

    public BasicHeaderIterator(Header[] headers, String name) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int findNext(int from) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean filterHeader(int index) {
        throw new RuntimeException("Stub!");
    }

    public boolean hasNext() {
        throw new RuntimeException("Stub!");
    }

    public Header nextHeader() throws NoSuchElementException {
        throw new RuntimeException("Stub!");
    }

    public final Object next() throws NoSuchElementException {
        throw new RuntimeException("Stub!");
    }

    public void remove() throws UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
}
