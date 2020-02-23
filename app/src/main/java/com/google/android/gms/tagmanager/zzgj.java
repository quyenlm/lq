package com.google.android.gms.tagmanager;

final class zzgj extends Number implements Comparable<zzgj> {
    private double zzbHb;
    private long zzbHc;
    private boolean zzbHd = false;

    private zzgj(double d) {
        this.zzbHb = d;
    }

    private zzgj(long j) {
        this.zzbHc = j;
    }

    public static zzgj zza(Double d) {
        return new zzgj(d.doubleValue());
    }

    public static zzgj zzai(long j) {
        return new zzgj(j);
    }

    public static zzgj zzfx(String str) throws NumberFormatException {
        try {
            return new zzgj(Long.parseLong(str));
        } catch (NumberFormatException e) {
            try {
                return new zzgj(Double.parseDouble(str));
            } catch (NumberFormatException e2) {
                throw new NumberFormatException(String.valueOf(str).concat(" is not a valid TypedNumber"));
            }
        }
    }

    public final byte byteValue() {
        return (byte) ((int) longValue());
    }

    public final double doubleValue() {
        return this.zzbHd ? (double) this.zzbHc : this.zzbHb;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof zzgj) && compareTo((zzgj) obj) == 0;
    }

    public final float floatValue() {
        return (float) doubleValue();
    }

    public final int hashCode() {
        return new Long(longValue()).hashCode();
    }

    public final int intValue() {
        return (int) longValue();
    }

    public final long longValue() {
        return this.zzbHd ? this.zzbHc : (long) this.zzbHb;
    }

    public final short shortValue() {
        return (short) ((int) longValue());
    }

    public final String toString() {
        return this.zzbHd ? Long.toString(this.zzbHc) : Double.toString(this.zzbHb);
    }

    public final boolean zzBZ() {
        return !this.zzbHd;
    }

    public final boolean zzCa() {
        return this.zzbHd;
    }

    /* renamed from: zza */
    public final int compareTo(zzgj zzgj) {
        return (!this.zzbHd || !zzgj.zzbHd) ? Double.compare(doubleValue(), zzgj.doubleValue()) : new Long(this.zzbHc).compareTo(Long.valueOf(zzgj.zzbHc));
    }
}
