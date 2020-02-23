package com.amazonaws.auth;

import android.support.v4.media.session.PlaybackStateCompat;
import com.amazonaws.AmazonClientException;
import com.amazonaws.internal.SdkInputStream;
import com.amazonaws.util.BinaryUtils;
import com.amazonaws.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class AwsChunkedEncodingInputStream extends SdkInputStream {
    private static final String CHUNK_SIGNATURE_HEADER = ";chunk-signature=";
    private static final String CHUNK_STRING_TO_SIGN_PREFIX = "AWS4-HMAC-SHA256-PAYLOAD";
    private static final String CLRF = "\r\n";
    private static final int DEFAULT_BUFFER_SIZE = 262144;
    private static final int DEFAULT_CHUNK_SIZE = 131072;
    protected static final String DEFAULT_ENCODING = "UTF-8";
    private static final byte[] FINAL_CHUNK = new byte[0];
    private static final int SIGNATURE_LENGTH = 64;
    private static final Log log = LogFactory.getLog(AwsChunkedEncodingInputStream.class);
    private final AWS4Signer aws4Signer;
    private ChunkContentIterator currentChunkIterator;
    private final String dateTime;
    private DecodedStreamBuffer decodedStreamBuffer;
    private final String headerSignature;
    private InputStream is;
    private boolean isAtStart;
    private boolean isTerminating;
    private final byte[] kSigning;
    private final String keyPath;
    private final int maxBufferSize;
    private String priorChunkSignature;

    public AwsChunkedEncodingInputStream(InputStream in, byte[] kSigning2, String datetime, String keyPath2, String headerSignature2, AWS4Signer aws4Signer2) {
        this(in, 262144, kSigning2, datetime, keyPath2, headerSignature2, aws4Signer2);
    }

    public AwsChunkedEncodingInputStream(InputStream in, int maxBufferSize2, byte[] kSigning2, String datetime, String keyPath2, String headerSignature2, AWS4Signer aws4Signer2) {
        this.is = null;
        this.isAtStart = true;
        this.isTerminating = false;
        if (in instanceof AwsChunkedEncodingInputStream) {
            AwsChunkedEncodingInputStream originalChunkedStream = (AwsChunkedEncodingInputStream) in;
            maxBufferSize2 = Math.max(originalChunkedStream.maxBufferSize, maxBufferSize2);
            this.is = originalChunkedStream.is;
            this.decodedStreamBuffer = originalChunkedStream.decodedStreamBuffer;
        } else {
            this.is = in;
            this.decodedStreamBuffer = null;
        }
        if (maxBufferSize2 < 131072) {
            throw new IllegalArgumentException("Max buffer size should not be less than chunk size");
        }
        this.maxBufferSize = maxBufferSize2;
        this.kSigning = kSigning2;
        this.dateTime = datetime;
        this.keyPath = keyPath2;
        this.headerSignature = headerSignature2;
        this.priorChunkSignature = headerSignature2;
        this.aws4Signer = aws4Signer2;
    }

    public int read() throws IOException {
        byte[] tmp = new byte[1];
        int count = read(tmp, 0, 1);
        if (count == -1) {
            return count;
        }
        if (log.isDebugEnabled()) {
            log.debug("One byte read from the stream.");
        }
        return tmp[0] & 255;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        abortIfNeeded();
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        } else {
            if (this.currentChunkIterator == null || !this.currentChunkIterator.hasNext()) {
                if (this.isTerminating) {
                    return -1;
                }
                this.isTerminating = setUpNextChunk();
            }
            int count = this.currentChunkIterator.read(b, off, len);
            if (count <= 0) {
                return count;
            }
            this.isAtStart = false;
            if (!log.isDebugEnabled()) {
                return count;
            }
            log.debug(count + " byte read from the stream.");
            return count;
        }
    }

    public long skip(long n) throws IOException {
        int count;
        if (n <= 0) {
            return 0;
        }
        long remaining = n;
        int toskip = (int) Math.min(PlaybackStateCompat.ACTION_SET_REPEAT_MODE, n);
        byte[] temp = new byte[toskip];
        while (remaining > 0 && (count = read(temp, 0, toskip)) >= 0) {
            remaining -= (long) count;
        }
        return n - remaining;
    }

    public boolean markSupported() {
        return true;
    }

    public synchronized void mark(int readlimit) {
        abortIfNeeded();
        if (!this.isAtStart) {
            throw new UnsupportedOperationException("Chunk-encoded stream only supports mark() at the start of the stream.");
        } else if (this.is.markSupported()) {
            if (log.isDebugEnabled()) {
                log.debug("AwsChunkedEncodingInputStream marked at the start of the stream (will directly mark the wrapped stream since it's mark-supported).");
            }
            this.is.mark(Integer.MAX_VALUE);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("AwsChunkedEncodingInputStream marked at the start of the stream (initializing the buffer since the wrapped stream is not mark-supported).");
            }
            this.decodedStreamBuffer = new DecodedStreamBuffer(this.maxBufferSize);
        }
    }

    public synchronized void reset() throws IOException {
        abortIfNeeded();
        this.currentChunkIterator = null;
        this.priorChunkSignature = this.headerSignature;
        if (this.is.markSupported()) {
            if (log.isDebugEnabled()) {
                log.debug("AwsChunkedEncodingInputStream reset (will reset the wrapped stream because it is mark-supported).");
            }
            this.is.reset();
        } else {
            if (log.isDebugEnabled()) {
                log.debug("AwsChunkedEncodingInputStream reset (will use the buffer of the decoded stream).");
            }
            if (this.decodedStreamBuffer == null) {
                throw new IOException("Cannot reset the stream because the mark is not set.");
            }
            this.decodedStreamBuffer.startReadBuffer();
        }
        this.currentChunkIterator = null;
        this.isAtStart = true;
        this.isTerminating = false;
    }

    public static long calculateStreamContentLength(long originalLength) {
        long j;
        if (originalLength < 0) {
            throw new IllegalArgumentException("Nonnegative content length expected.");
        }
        long maxSizeChunks = originalLength / PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
        long remainingBytes = originalLength % PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
        long calculateSignedChunkLength = maxSizeChunks * calculateSignedChunkLength(PlaybackStateCompat.ACTION_PREPARE_FROM_URI);
        if (remainingBytes > 0) {
            j = calculateSignedChunkLength(remainingBytes);
        } else {
            j = 0;
        }
        return j + calculateSignedChunkLength + calculateSignedChunkLength(0);
    }

    private static long calculateSignedChunkLength(long chunkDataSize) {
        return ((long) (Long.toHexString(chunkDataSize).length() + CHUNK_SIGNATURE_HEADER.length() + 64 + CLRF.length())) + chunkDataSize + ((long) CLRF.length());
    }

    private boolean setUpNextChunk() throws IOException {
        byte[] chunkData = new byte[131072];
        int chunkSizeInBytes = 0;
        while (chunkSizeInBytes < 131072) {
            if (this.decodedStreamBuffer == null || !this.decodedStreamBuffer.hasNext()) {
                int count = this.is.read(chunkData, chunkSizeInBytes, 131072 - chunkSizeInBytes);
                if (count == -1) {
                    break;
                }
                if (this.decodedStreamBuffer != null) {
                    this.decodedStreamBuffer.buffer(chunkData, chunkSizeInBytes, count);
                }
                chunkSizeInBytes += count;
            } else {
                chunkData[chunkSizeInBytes] = this.decodedStreamBuffer.next();
                chunkSizeInBytes++;
            }
        }
        if (chunkSizeInBytes == 0) {
            this.currentChunkIterator = new ChunkContentIterator(createSignedChunk(FINAL_CHUNK));
            return true;
        }
        if (chunkSizeInBytes < chunkData.length) {
            byte[] temp = new byte[chunkSizeInBytes];
            System.arraycopy(chunkData, 0, temp, 0, chunkSizeInBytes);
            chunkData = temp;
        }
        this.currentChunkIterator = new ChunkContentIterator(createSignedChunk(chunkData));
        return false;
    }

    private byte[] createSignedChunk(byte[] chunkData) {
        StringBuilder chunkHeader = new StringBuilder();
        chunkHeader.append(Integer.toHexString(chunkData.length));
        String chunkSignature = BinaryUtils.toHex(this.aws4Signer.sign("AWS4-HMAC-SHA256-PAYLOAD\n" + this.dateTime + "\n" + this.keyPath + "\n" + this.priorChunkSignature + "\n" + BinaryUtils.toHex(this.aws4Signer.hash("")) + "\n" + BinaryUtils.toHex(this.aws4Signer.hash(chunkData)), this.kSigning, SigningAlgorithm.HmacSHA256));
        this.priorChunkSignature = chunkSignature;
        chunkHeader.append("" + CHUNK_SIGNATURE_HEADER + chunkSignature);
        chunkHeader.append(CLRF);
        try {
            byte[] header = chunkHeader.toString().getBytes(StringUtils.UTF8);
            byte[] trailer = CLRF.getBytes(StringUtils.UTF8);
            byte[] signedChunk = new byte[(header.length + chunkData.length + trailer.length)];
            System.arraycopy(header, 0, signedChunk, 0, header.length);
            System.arraycopy(chunkData, 0, signedChunk, header.length, chunkData.length);
            System.arraycopy(trailer, 0, signedChunk, header.length + chunkData.length, trailer.length);
            return signedChunk;
        } catch (Exception e) {
            throw new AmazonClientException("Unable to sign the chunked data. " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public InputStream getWrappedInputStream() {
        return this.is;
    }
}
