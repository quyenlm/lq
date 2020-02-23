package com.google.android.gms.location.places.internal;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import java.util.Collection;
import java.util.List;

public final class zzg {
    public static CharSequence zza(String str, List<zzb> list, CharacterStyle characterStyle) {
        if (characterStyle == null) {
            return str;
        }
        SpannableString spannableString = new SpannableString(str);
        for (zzb next : list) {
            spannableString.setSpan(CharacterStyle.wrap(characterStyle), next.mOffset, next.mLength + next.mOffset, 0);
        }
        return spannableString;
    }

    public static String zzi(Collection<String> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        return TextUtils.join(", ", collection);
    }
}
