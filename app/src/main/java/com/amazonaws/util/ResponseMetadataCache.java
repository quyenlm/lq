package com.amazonaws.util;

import com.amazonaws.ResponseMetadata;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseMetadataCache {
    private final InternalCache internalCache;

    public ResponseMetadataCache(int maxEntries) {
        this.internalCache = new InternalCache(maxEntries);
    }

    public synchronized void add(Object obj, ResponseMetadata metadata) {
        if (obj != null) {
            this.internalCache.put(Integer.valueOf(System.identityHashCode(obj)), metadata);
        }
    }

    public ResponseMetadata get(Object obj) {
        return (ResponseMetadata) this.internalCache.get(Integer.valueOf(System.identityHashCode(obj)));
    }

    private static final class InternalCache extends LinkedHashMap<Integer, ResponseMetadata> {
        private int maxSize;

        public InternalCache(int maxSize2) {
            super(maxSize2);
            this.maxSize = maxSize2;
        }

        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Map.Entry eldest) {
            return size() > this.maxSize;
        }
    }
}
