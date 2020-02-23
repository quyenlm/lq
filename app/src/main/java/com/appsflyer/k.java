package com.appsflyer;

final class k {

    /* renamed from: ˋ  reason: contains not printable characters */
    private boolean f132;

    /* renamed from: ˏ  reason: contains not printable characters */
    private b f133;

    /* renamed from: ॱ  reason: contains not printable characters */
    private String f134;

    k(b bVar, String str, boolean z) {
        this.f133 = bVar;
        this.f134 = str;
        this.f132 = z;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final String m95() {
        return this.f134;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final boolean m94() {
        return this.f132;
    }

    public final String toString() {
        return String.format("%s,%s", new Object[]{this.f134, Boolean.valueOf(this.f132)});
    }

    enum b {
        GOOGLE(0),
        AMAZON(1);
        

        /* renamed from: ˋ  reason: contains not printable characters */
        private int f138;

        private b(int i) {
            this.f138 = i;
        }

        public final String toString() {
            return String.valueOf(this.f138);
        }
    }
}
