package com.google.android.gms.internal;

import java.io.IOException;

public final class adg {
    private final byte[] buffer;
    private int zzcse;
    private int zzcsf;
    private int zzcsg;
    private int zzcsh;
    private int zzcsi;
    private int zzcsj = Integer.MAX_VALUE;
    private int zzcsk;
    private int zzcsl = 64;
    private int zzcsm = 67108864;

    private adg(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzcse = i;
        this.zzcsf = i + i2;
        this.zzcsh = i;
    }

    public static adg zzH(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    private final void zzLJ() {
        this.zzcsf += this.zzcsg;
        int i = this.zzcsf;
        if (i > this.zzcsj) {
            this.zzcsg = i - this.zzcsj;
            this.zzcsf -= this.zzcsg;
            return;
        }
        this.zzcsg = 0;
    }

    private final byte zzLL() throws IOException {
        if (this.zzcsh == this.zzcsf) {
            throw ado.zzLQ();
        }
        byte[] bArr = this.buffer;
        int i = this.zzcsh;
        this.zzcsh = i + 1;
        return bArr[i];
    }

    public static adg zzb(byte[] bArr, int i, int i2) {
        return new adg(bArr, 0, i2);
    }

    private final void zzcq(int i) throws IOException {
        if (i < 0) {
            throw ado.zzLR();
        } else if (this.zzcsh + i > this.zzcsj) {
            zzcq(this.zzcsj - this.zzcsh);
            throw ado.zzLQ();
        } else if (i <= this.zzcsf - this.zzcsh) {
            this.zzcsh += i;
        } else {
            throw ado.zzLQ();
        }
    }

    public final int getPosition() {
        return this.zzcsh - this.zzcse;
    }

    public final byte[] readBytes() throws IOException {
        int zzLF = zzLF();
        if (zzLF < 0) {
            throw ado.zzLR();
        } else if (zzLF == 0) {
            return ads.zzcsI;
        } else {
            if (zzLF > this.zzcsf - this.zzcsh) {
                throw ado.zzLQ();
            }
            byte[] bArr = new byte[zzLF];
            System.arraycopy(this.buffer, this.zzcsh, bArr, 0, zzLF);
            this.zzcsh = zzLF + this.zzcsh;
            return bArr;
        }
    }

    public final String readString() throws IOException {
        int zzLF = zzLF();
        if (zzLF < 0) {
            throw ado.zzLR();
        } else if (zzLF > this.zzcsf - this.zzcsh) {
            throw ado.zzLQ();
        } else {
            String str = new String(this.buffer, this.zzcsh, zzLF, adn.UTF_8);
            this.zzcsh = zzLF + this.zzcsh;
            return str;
        }
    }

    public final int zzLA() throws IOException {
        if (this.zzcsh == this.zzcsf) {
            this.zzcsi = 0;
            return 0;
        }
        this.zzcsi = zzLF();
        if (this.zzcsi != 0) {
            return this.zzcsi;
        }
        throw new ado("Protocol message contained an invalid tag (zero).");
    }

    public final long zzLB() throws IOException {
        return zzLG();
    }

    public final int zzLC() throws IOException {
        return zzLF();
    }

    public final boolean zzLD() throws IOException {
        return zzLF() != 0;
    }

    public final long zzLE() throws IOException {
        long zzLG = zzLG();
        return (-(zzLG & 1)) ^ (zzLG >>> 1);
    }

    public final int zzLF() throws IOException {
        byte zzLL = zzLL();
        if (zzLL >= 0) {
            return zzLL;
        }
        byte b = zzLL & Byte.MAX_VALUE;
        byte zzLL2 = zzLL();
        if (zzLL2 >= 0) {
            return b | (zzLL2 << 7);
        }
        byte b2 = b | ((zzLL2 & Byte.MAX_VALUE) << 7);
        byte zzLL3 = zzLL();
        if (zzLL3 >= 0) {
            return b2 | (zzLL3 << 14);
        }
        byte b3 = b2 | ((zzLL3 & Byte.MAX_VALUE) << 14);
        byte zzLL4 = zzLL();
        if (zzLL4 >= 0) {
            return b3 | (zzLL4 << 21);
        }
        byte b4 = b3 | ((zzLL4 & Byte.MAX_VALUE) << 21);
        byte zzLL5 = zzLL();
        byte b5 = b4 | (zzLL5 << 28);
        if (zzLL5 >= 0) {
            return b5;
        }
        for (int i = 0; i < 5; i++) {
            if (zzLL() >= 0) {
                return b5;
            }
        }
        throw ado.zzLS();
    }

    public final long zzLG() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzLL = zzLL();
            j |= ((long) (zzLL & Byte.MAX_VALUE)) << i;
            if ((zzLL & 128) == 0) {
                return j;
            }
        }
        throw ado.zzLS();
    }

    public final int zzLH() throws IOException {
        return (zzLL() & 255) | ((zzLL() & 255) << 8) | ((zzLL() & 255) << 16) | ((zzLL() & 255) << 24);
    }

    public final long zzLI() throws IOException {
        byte zzLL = zzLL();
        byte zzLL2 = zzLL();
        return ((((long) zzLL2) & 255) << 8) | (((long) zzLL) & 255) | ((((long) zzLL()) & 255) << 16) | ((((long) zzLL()) & 255) << 24) | ((((long) zzLL()) & 255) << 32) | ((((long) zzLL()) & 255) << 40) | ((((long) zzLL()) & 255) << 48) | ((((long) zzLL()) & 255) << 56);
    }

    public final int zzLK() {
        if (this.zzcsj == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzcsj - this.zzcsh;
    }

    public final void zza(adp adp) throws IOException {
        int zzLF = zzLF();
        if (this.zzcsk >= this.zzcsl) {
            throw ado.zzLT();
        }
        int zzcn = zzcn(zzLF);
        this.zzcsk++;
        adp.zza(this);
        zzcl(0);
        this.zzcsk--;
        zzco(zzcn);
    }

    public final void zza(adp adp, int i) throws IOException {
        if (this.zzcsk >= this.zzcsl) {
            throw ado.zzLT();
        }
        this.zzcsk++;
        adp.zza(this);
        zzcl((i << 3) | 4);
        this.zzcsk--;
    }

    public final void zzcl(int i) throws ado {
        if (this.zzcsi != i) {
            throw new ado("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzcm(int i) throws IOException {
        int zzLA;
        switch (i & 7) {
            case 0:
                zzLF();
                return true;
            case 1:
                zzLI();
                return true;
            case 2:
                zzcq(zzLF());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzLH();
                return true;
            default:
                throw new ado("Protocol message tag had invalid wire type.");
        }
        do {
            zzLA = zzLA();
            if (zzLA == 0 || !zzcm(zzLA)) {
                zzcl(((i >>> 3) << 3) | 4);
                return true;
            }
            zzLA = zzLA();
            zzcl(((i >>> 3) << 3) | 4);
            return true;
        } while (!zzcm(zzLA));
        zzcl(((i >>> 3) << 3) | 4);
        return true;
    }

    public final int zzcn(int i) throws ado {
        if (i < 0) {
            throw ado.zzLR();
        }
        int i2 = this.zzcsh + i;
        int i3 = this.zzcsj;
        if (i2 > i3) {
            throw ado.zzLQ();
        }
        this.zzcsj = i2;
        zzLJ();
        return i3;
    }

    public final void zzco(int i) {
        this.zzcsj = i;
        zzLJ();
    }

    public final void zzcp(int i) {
        zzq(i, this.zzcsi);
    }

    public final byte[] zzp(int i, int i2) {
        if (i2 == 0) {
            return ads.zzcsI;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzcse + i, bArr, 0, i2);
        return bArr;
    }

    /* access modifiers changed from: package-private */
    public final void zzq(int i, int i2) {
        if (i > this.zzcsh - this.zzcse) {
            throw new IllegalArgumentException(new StringBuilder(50).append("Position ").append(i).append(" is beyond current ").append(this.zzcsh - this.zzcse).toString());
        } else if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(24).append("Bad position ").append(i).toString());
        } else {
            this.zzcsh = this.zzcse + i;
            this.zzcsi = i2;
        }
    }
}
