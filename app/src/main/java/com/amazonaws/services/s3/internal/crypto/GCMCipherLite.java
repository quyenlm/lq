package com.amazonaws.services.s3.internal.crypto;

import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

final class GCMCipherLite extends CipherLite {
    private static final int TAG_LENGTH = (ContentCryptoScheme.AES_GCM.getTagLengthInBits() / 8);
    private CipherLite aux;
    private long currentCount;
    private boolean doneFinal;
    private byte[] finalBytes;
    private boolean invisiblyProcessed;
    private long markedCount;
    private long outputByteCount;
    private boolean securityViolated;
    private final int tagLen;

    GCMCipherLite(Cipher cipher, SecretKey secreteKey, int cipherMode) {
        super(cipher, ContentCryptoScheme.AES_GCM, secreteKey, cipherMode);
        this.tagLen = cipherMode == 1 ? TAG_LENGTH : 0;
        if (cipherMode != 1 && cipherMode != 2) {
            throw new IllegalArgumentException();
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] doFinal() throws IllegalBlockSizeException, BadPaddingException {
        if (!this.doneFinal) {
            this.doneFinal = true;
            this.finalBytes = super.doFinal();
            if (this.finalBytes == null) {
                return null;
            }
            this.outputByteCount += (long) checkMax(this.finalBytes.length - this.tagLen);
            return (byte[]) this.finalBytes.clone();
        } else if (this.securityViolated) {
            throw new SecurityException();
        } else if (this.finalBytes == null) {
            return null;
        } else {
            return (byte[]) this.finalBytes.clone();
        }
    }

    /* access modifiers changed from: package-private */
    public final byte[] doFinal(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
        return doFinal0(input, 0, input.length);
    }

    /* access modifiers changed from: package-private */
    public final byte[] doFinal(byte[] input, int inputOffset, int inputLen) throws IllegalBlockSizeException, BadPaddingException {
        return doFinal0(input, inputOffset, inputLen);
    }

    private final byte[] doFinal0(byte[] input, int inputOffset, int inputLen) throws IllegalBlockSizeException, BadPaddingException {
        if (!this.doneFinal) {
            this.doneFinal = true;
            this.finalBytes = super.doFinal(input, inputOffset, inputLen);
            if (this.finalBytes == null) {
                return null;
            }
            this.outputByteCount += (long) checkMax(this.finalBytes.length - this.tagLen);
            return (byte[]) this.finalBytes.clone();
        } else if (this.securityViolated) {
            throw new SecurityException();
        } else if (2 != getCipherMode()) {
            int finalDataLen = this.finalBytes.length - this.tagLen;
            if (inputLen == finalDataLen) {
                return (byte[]) this.finalBytes.clone();
            }
            if (inputLen >= finalDataLen || ((long) inputLen) + this.currentCount != this.outputByteCount) {
                throw new IllegalStateException("Inconsistent re-rencryption");
            }
            return Arrays.copyOfRange(this.finalBytes, (this.finalBytes.length - this.tagLen) - inputLen, this.finalBytes.length);
        } else if (this.finalBytes == null) {
            return null;
        } else {
            return (byte[]) this.finalBytes.clone();
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] update(byte[] input, int inputOffset, int inputLen) {
        byte[] out;
        if (this.aux == null) {
            out = super.update(input, inputOffset, inputLen);
            if (out == null) {
                this.invisiblyProcessed = input.length > 0;
                byte[] bArr = out;
                return null;
            }
            this.outputByteCount += (long) checkMax(out.length);
            this.invisiblyProcessed = out.length == 0 && inputLen > 0;
        } else {
            out = this.aux.update(input, inputOffset, inputLen);
            if (out == null) {
                byte[] bArr2 = out;
                return null;
            }
            this.currentCount += (long) out.length;
            if (this.currentCount == this.outputByteCount) {
                this.aux = null;
            } else if (this.currentCount > this.outputByteCount) {
                if (1 == getCipherMode()) {
                    throw new IllegalStateException("currentCount=" + this.currentCount + " > outputByteCount=" + this.outputByteCount);
                }
                int finalBytesLen = this.finalBytes == null ? 0 : this.finalBytes.length;
                long diff = (this.outputByteCount - (this.currentCount - ((long) out.length))) - ((long) finalBytesLen);
                this.currentCount = this.outputByteCount - ((long) finalBytesLen);
                this.aux = null;
                byte[] bArr3 = out;
                return Arrays.copyOf(out, (int) diff);
            }
        }
        byte[] bArr4 = out;
        return out;
    }

    private int checkMax(int delta) {
        if (this.outputByteCount + ((long) delta) <= 68719476704L) {
            return delta;
        }
        this.securityViolated = true;
        throw new SecurityException("Number of bytes processed has exceeded the maximum allowed by AES/GCM; [outputByteCount=" + this.outputByteCount + ", delta=" + delta + "]");
    }

    /* access modifiers changed from: package-private */
    public long mark() {
        long j = this.aux == null ? this.outputByteCount : this.currentCount;
        this.markedCount = j;
        return j;
    }

    /* access modifiers changed from: package-private */
    public boolean markSupported() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        if (this.markedCount < this.outputByteCount || this.invisiblyProcessed) {
            try {
                this.aux = createAuxiliary(this.markedCount);
                this.currentCount = this.markedCount;
            } catch (Exception e) {
                throw (e instanceof RuntimeException ? (RuntimeException) e : new IllegalStateException(e));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] getFinalBytes() {
        if (this.finalBytes == null) {
            return null;
        }
        return (byte[]) this.finalBytes.clone();
    }

    /* access modifiers changed from: package-private */
    public byte[] getTag() {
        if (getCipherMode() != 1 || this.finalBytes == null) {
            return null;
        }
        return Arrays.copyOfRange(this.finalBytes, this.finalBytes.length - this.tagLen, this.finalBytes.length);
    }

    /* access modifiers changed from: package-private */
    public long getOutputByteCount() {
        return this.outputByteCount;
    }

    /* access modifiers changed from: package-private */
    public long getCurrentCount() {
        return this.currentCount;
    }

    /* access modifiers changed from: package-private */
    public long getMarkedCount() {
        return this.markedCount;
    }
}
