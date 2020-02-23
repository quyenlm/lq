package com.google.android.gms.games.quest;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzg;

public final class QuestBuffer extends zzg<Quest> {
    public QuestBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zzi(int i, int i2) {
        return new QuestRef(this.zzaCX, i, i2);
    }

    /* access modifiers changed from: protected */
    public final String zzqS() {
        return "external_quest_id";
    }
}
