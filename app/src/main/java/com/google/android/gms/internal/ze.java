package com.google.android.gms.internal;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

final class ze implements DatabaseReference.CompletionListener {
    private /* synthetic */ TaskCompletionSource zzaIk;

    ze(TaskCompletionSource taskCompletionSource) {
        this.zzaIk = taskCompletionSource;
    }

    public final void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        if (databaseError != null) {
            this.zzaIk.setException(databaseError.toException());
        } else {
            this.zzaIk.setResult(null);
        }
    }
}
