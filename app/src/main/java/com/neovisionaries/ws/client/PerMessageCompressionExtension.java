package com.neovisionaries.ws.client;

abstract class PerMessageCompressionExtension extends WebSocketExtension {
    /* access modifiers changed from: protected */
    public abstract byte[] compress(byte[] bArr) throws WebSocketException;

    /* access modifiers changed from: protected */
    public abstract byte[] decompress(byte[] bArr) throws WebSocketException;

    public PerMessageCompressionExtension(String name) {
        super(name);
    }

    public PerMessageCompressionExtension(WebSocketExtension source) {
        super(source);
    }
}
