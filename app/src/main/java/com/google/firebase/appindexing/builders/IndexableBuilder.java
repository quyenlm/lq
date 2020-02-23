package com.google.firebase.appindexing.builders;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.IndexableBuilder;
import com.google.firebase.appindexing.internal.Thing;
import com.google.firebase.appindexing.internal.zzw;
import java.util.Arrays;

public class IndexableBuilder<T extends IndexableBuilder<?>> {
    private String zzD;
    private final String zzVB;
    private final Bundle zzajQ = new Bundle();
    private Thing.zza zzbVH;

    protected IndexableBuilder(@NonNull String str) {
        zzbo.zzu(str);
        zzbo.zzcF(str);
        this.zzVB = str;
    }

    private final T zza(@NonNull String str, @NonNull Thing... thingArr) {
        zzbo.zzu(str);
        zzbo.zzu(thingArr);
        if (thingArr.length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < thingArr.length; i2++) {
                thingArr[i] = thingArr[i2];
                if (thingArr[i2] == null) {
                    zzw.zzgo(new StringBuilder(58).append("Thing at ").append(i2).append(" is null and is ignored by put method.").toString());
                } else {
                    i++;
                }
            }
            if (i > 0) {
                this.zzajQ.putParcelableArray(str, (Parcelable[]) zzc((Thing[]) Arrays.copyOfRange(thingArr, 0, i)));
            }
        } else {
            zzw.zzgo("Thing array is empty and is ignored by put method.");
        }
        return this;
    }

    private static <S> S[] zzc(S[] sArr) {
        if (sArr.length < 100) {
            return sArr;
        }
        zzw.zzgo("Input Array of elements is too big, cutting off.");
        return Arrays.copyOf(sArr, 100);
    }

    public final Indexable build() {
        return new Thing(new Bundle(this.zzajQ), this.zzbVH == null ? Indexable.Metadata.zzbVD : this.zzbVH, this.zzD, this.zzVB);
    }

    public T put(@NonNull String str, @NonNull long... jArr) {
        Bundle bundle = this.zzajQ;
        zzbo.zzu(str);
        zzbo.zzu(jArr);
        if (jArr.length > 0) {
            if (jArr.length >= 100) {
                zzw.zzgo("Input Array of elements is too big, cutting off.");
                jArr = Arrays.copyOf(jArr, 100);
            }
            bundle.putLongArray(str, jArr);
        } else {
            zzw.zzgo("Long array is empty and is ignored by put method.");
        }
        return this;
    }

    public T put(@NonNull String str, @NonNull Indexable... indexableArr) throws FirebaseAppIndexingInvalidArgumentException {
        zzbo.zzu(str);
        zzbo.zzu(indexableArr);
        Thing[] thingArr = new Thing[indexableArr.length];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= indexableArr.length) {
                zza(str, thingArr);
                return this;
            } else if (indexableArr[i2] == null || (indexableArr[i2] instanceof Thing)) {
                thingArr[i2] = indexableArr[i2];
                i = i2 + 1;
            } else {
                throw new FirebaseAppIndexingInvalidArgumentException("Invalid Indexable encountered. Use Indexable.Builder or convenience methods under Indexables to create the Indexable.");
            }
        }
    }

    /* access modifiers changed from: protected */
    public <S extends IndexableBuilder> T put(@NonNull String str, @NonNull S... sArr) {
        zzbo.zzu(str);
        zzbo.zzu(sArr);
        if (sArr.length > 0) {
            Thing[] thingArr = new Thing[sArr.length];
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= sArr.length) {
                    break;
                }
                if (sArr[i2] == null) {
                    zzw.zzgo(new StringBuilder(60).append("Builder at ").append(i2).append(" is null and is ignored by put method.").toString());
                } else {
                    thingArr[i2] = (Thing) sArr[i2].build();
                }
                i = i2 + 1;
            }
            if (thingArr.length > 0) {
                zza(str, thingArr);
            }
        } else {
            zzw.zzgo("Builder array is empty and is ignored by put method.");
        }
        return this;
    }

    public T put(@NonNull String str, @NonNull String... strArr) {
        String substring;
        Bundle bundle = this.zzajQ;
        zzbo.zzu(str);
        zzbo.zzu(strArr);
        String[] strArr2 = (String[]) Arrays.copyOf(strArr, strArr.length);
        if (strArr2.length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < Math.min(strArr2.length, 100); i2++) {
                strArr2[i] = strArr2[i2];
                if (strArr2[i2] == null) {
                    zzw.zzgo(new StringBuilder(59).append("String at ").append(i2).append(" is null and is ignored by put method.").toString());
                } else {
                    if (strArr2[i].length() > 20000) {
                        zzw.zzgo(new StringBuilder(53).append("String at ").append(i2).append(" is too long, truncating string.").toString());
                        String str2 = strArr2[i];
                        if (str2.length() <= 20000) {
                            substring = str2;
                        } else {
                            substring = str2.substring(0, (!Character.isHighSurrogate(str2.charAt(19999)) || !Character.isLowSurrogate(str2.charAt(20000))) ? 20000 : 19999);
                        }
                        strArr2[i] = substring;
                    }
                    i++;
                }
            }
            if (i > 0) {
                bundle.putStringArray(str, (String[]) zzc((String[]) Arrays.copyOfRange(strArr2, 0, i)));
            }
        } else {
            zzw.zzgo("String array is empty and is ignored by put method.");
        }
        return this;
    }

    public T put(@NonNull String str, @NonNull boolean... zArr) {
        Bundle bundle = this.zzajQ;
        zzbo.zzu(str);
        zzbo.zzu(zArr);
        if (zArr.length > 0) {
            if (zArr.length >= 100) {
                zzw.zzgo("Input Array of elements is too big, cutting off.");
                zArr = Arrays.copyOf(zArr, 100);
            }
            bundle.putBooleanArray(str, zArr);
        } else {
            zzw.zzgo("Boolean array is empty and is ignored by put method.");
        }
        return this;
    }

    public final T setDescription(@NonNull String str) {
        zzbo.zzu(str);
        return put("description", str);
    }

    public final T setImage(@NonNull String str) {
        zzbo.zzu(str);
        return put("image", str);
    }

    public T setMetadata(@NonNull Indexable.Metadata.Builder builder) {
        zzbo.zza(this.zzbVH == null, (Object) "setMetadata may only be called once");
        zzbo.zzu(builder);
        this.zzbVH = builder.zzEA();
        return this;
    }

    public final T setName(@NonNull String str) {
        zzbo.zzu(str);
        return put("name", str);
    }

    public final T setSameAs(@NonNull String str) {
        zzbo.zzu(str);
        return put("sameAs", str);
    }

    public final T setUrl(@NonNull String str) {
        zzbo.zzu(str);
        this.zzD = str;
        return this;
    }
}
