package com.google.firebase.appindexing.builders;

import android.support.annotation.NonNull;
import com.vk.sdk.api.model.VKAttachments;

public final class MusicGroupBuilder extends IndexableBuilder<MusicGroupBuilder> {
    MusicGroupBuilder() {
        super("MusicGroup");
    }

    public final MusicGroupBuilder setAlbum(@NonNull MusicAlbumBuilder... musicAlbumBuilderArr) {
        return (MusicGroupBuilder) put(VKAttachments.TYPE_ALBUM, (S[]) musicAlbumBuilderArr);
    }

    public final MusicGroupBuilder setGenre(@NonNull String str) {
        return (MusicGroupBuilder) put("genre", str);
    }

    public final MusicGroupBuilder setTrack(@NonNull MusicRecordingBuilder... musicRecordingBuilderArr) {
        return (MusicGroupBuilder) put("track", (S[]) musicRecordingBuilderArr);
    }
}
