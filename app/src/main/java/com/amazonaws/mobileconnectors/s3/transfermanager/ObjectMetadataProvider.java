package com.amazonaws.mobileconnectors.s3.transfermanager;

import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.File;

@Deprecated
public interface ObjectMetadataProvider {
    void provideObjectMetadata(File file, ObjectMetadata objectMetadata);
}
