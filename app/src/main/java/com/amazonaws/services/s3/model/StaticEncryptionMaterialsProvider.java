package com.amazonaws.services.s3.model;

import java.util.Map;

public class StaticEncryptionMaterialsProvider implements EncryptionMaterialsProvider {
    private final EncryptionMaterials materials;

    public StaticEncryptionMaterialsProvider(EncryptionMaterials materials2) {
        this.materials = materials2;
    }

    public EncryptionMaterials getEncryptionMaterials() {
        return this.materials;
    }

    public void refresh() {
    }

    public EncryptionMaterials getEncryptionMaterials(Map<String, String> materialDescIn) {
        boolean noMaterialDescIn;
        EncryptionMaterials accessorMaterials;
        boolean noMaterialDesc = false;
        Map<String, String> materialDesc = this.materials.getMaterialsDescription();
        if (materialDescIn != null && materialDescIn.equals(materialDesc)) {
            return this.materials;
        }
        EncryptionMaterialsAccessor accessor = this.materials.getAccessor();
        if (accessor != null && (accessorMaterials = accessor.getEncryptionMaterials(materialDescIn)) != null) {
            return accessorMaterials;
        }
        if (materialDescIn == null || materialDescIn.size() == 0) {
            noMaterialDescIn = true;
        } else {
            noMaterialDescIn = false;
        }
        if (materialDesc == null || materialDesc.size() == 0) {
            noMaterialDesc = true;
        }
        return (!noMaterialDescIn || !noMaterialDesc) ? null : this.materials;
    }
}
