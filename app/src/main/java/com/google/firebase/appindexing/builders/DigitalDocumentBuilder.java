package com.google.firebase.appindexing.builders;

import android.support.annotation.NonNull;
import com.tencent.component.net.download.multiplex.http.ContentType;
import java.util.Date;

public final class DigitalDocumentBuilder extends IndexableBuilder<DigitalDocumentBuilder> {
    DigitalDocumentBuilder() {
        super("DigitalDocument");
    }

    DigitalDocumentBuilder(String str) {
        super(str);
    }

    public final DigitalDocumentBuilder setAuthor(@NonNull PersonBuilder... personBuilderArr) {
        return (DigitalDocumentBuilder) put("author", (S[]) personBuilderArr);
    }

    public final DigitalDocumentBuilder setDateCreated(@NonNull Date date) {
        return (DigitalDocumentBuilder) put("dateCreated", date.getTime());
    }

    public final DigitalDocumentBuilder setDateModified(@NonNull Date date) {
        return (DigitalDocumentBuilder) put("dateModified", date.getTime());
    }

    public final DigitalDocumentBuilder setHasDigitalDocumentPermission(@NonNull DigitalDocumentPermissionBuilder... digitalDocumentPermissionBuilderArr) {
        return (DigitalDocumentBuilder) put("hasDigitalDocumentPermission", (S[]) digitalDocumentPermissionBuilderArr);
    }

    public final DigitalDocumentBuilder setText(@NonNull String str) {
        return (DigitalDocumentBuilder) put(ContentType.TYPE_TEXT, str);
    }
}
