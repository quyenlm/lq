package com.amazonaws.services.s3.model;

public interface EncryptionMaterialsProvider extends EncryptionMaterialsAccessor {
    EncryptionMaterials getEncryptionMaterials();

    void refresh();
}
