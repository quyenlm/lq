package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.subao.common.intf.GameInformation;
import com.subao.common.intf.InstalledApplication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: SupportGameList */
public class al implements Iterable<ak> {
    private final List<ak> a;

    /* compiled from: SupportGameList */
    public interface a<T> {
        @NonNull
        Iterator<T> a(@NonNull ak akVar);
    }

    public al(List<ak> list) {
        this.a = list;
    }

    @Nullable
    public static al a(@Nullable List<b> list, @Nullable Iterable<InstalledApplication> iterable) {
        if (list == null || list.isEmpty()) {
            Log.w("SubaoData", "SupportGameList.build(): List<AccelGame> is empty");
            return null;
        } else if (iterable == null) {
            Log.w("SubaoData", "SupportGameList.build(): installedApplications is null");
            return null;
        } else {
            c cVar = new c(list);
            ArrayList arrayList = new ArrayList(16);
            int i = 0;
            for (InstalledApplication next : iterable) {
                int i2 = i + 1;
                String packageName = next.getPackageName();
                String label = next.getLabel();
                b a2 = cVar.a(packageName, label);
                if (a2 != null) {
                    arrayList.add(new ak(next.getUid(), a2.d(), packageName, label, a2.j, a2.e(), a2.f(), a2.g(), a2.a()));
                }
                i = i2;
            }
            if (!arrayList.isEmpty()) {
                return new al(arrayList);
            }
            Log.w("SubaoData", String.format(n.b, "SupportGameList.build(%d, %d) return empty", new Object[]{Integer.valueOf(list.size()), Integer.valueOf(i)}));
            return null;
        }
    }

    public int a() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    @NonNull
    public Iterator<ak> iterator() {
        return new e(this.a == null ? null : this.a.iterator());
    }

    public <T> List<T> a(a<T> aVar, boolean z) {
        int a2 = a();
        if (a2 == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(a2);
        for (ak next : this.a) {
            if (z || !next.b()) {
                Iterator<T> a3 = aVar.a(next);
                while (a3.hasNext()) {
                    T next2 = a3.next();
                    if (next2 != null) {
                        arrayList.add(next2);
                    }
                }
            }
        }
        return arrayList;
    }

    /* compiled from: SupportGameList */
    private static class e implements Iterator<ak> {
        private final Iterator<ak> a;

        private e(Iterator<ak> it) {
            this.a = it;
        }

        public boolean hasNext() {
            return this.a != null && this.a.hasNext();
        }

        /* renamed from: a */
        public ak next() {
            if (this.a != null) {
                return this.a.next();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: SupportGameList */
    private static class d<T> implements Iterator<T> {
        private T a;

        d(T t) {
            this.a = t;
        }

        public boolean hasNext() {
            return this.a != null;
        }

        public T next() {
            T t = this.a;
            if (t != null) {
                this.a = null;
                return t;
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: SupportGameList */
    public static class c implements a<String> {
        @NonNull
        public Iterator<String> a(@NonNull ak akVar) {
            return new d(akVar.b);
        }
    }

    /* compiled from: SupportGameList */
    public static class b implements a<GameInformation> {
        @NonNull
        public Iterator<GameInformation> a(@NonNull ak akVar) {
            return new a(akVar);
        }

        /* compiled from: SupportGameList */
        private static class a implements Iterator<GameInformation> {
            @NonNull
            private final ak a;
            @Nullable
            private final Iterator<p> b;
            private int c;

            public a(@NonNull ak akVar) {
                this.a = akVar;
                Iterable<p> d = akVar.d();
                this.b = d == null ? null : d.iterator();
            }

            public boolean hasNext() {
                if (this.b == null) {
                    return this.c == 0;
                }
                return this.b.hasNext();
            }

            /* renamed from: a */
            public GameInformation next() {
                if (this.b != null) {
                    p next = this.b.next();
                    if (next == null) {
                        return b();
                    }
                    return GameInformation.create(this.a.a, this.a.b, this.a.c, next.c(), next.a(), next.b(), next.d());
                } else if (this.c == 0) {
                    this.c++;
                    return b();
                } else {
                    throw new NoSuchElementException();
                }
            }

            private GameInformation b() {
                return GameInformation.create(this.a.a, this.a.b, this.a.c, (String) null, (String) null, (String) null, this.a.a());
            }
        }
    }
}
