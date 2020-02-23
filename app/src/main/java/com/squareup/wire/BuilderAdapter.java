package com.squareup.wire;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Builder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

final class BuilderAdapter<B extends Message.Builder> {
    private static final Comparator<Field> ORDER_BY_FIELD_NAME = new Comparator<Field>() {
        public int compare(Field field1, Field field2) {
            return field1.getName().compareTo(field2.getName());
        }
    };
    private static final int SUFFIX_LENGTH = "$Builder".length();
    private final List<Field> requiredFields = new ArrayList();

    public BuilderAdapter(Class<B> builderType) {
        String builderTypeName = builderType.getName();
        try {
            for (Field field : Class.forName(builderTypeName.substring(0, builderTypeName.length() - SUFFIX_LENGTH)).getDeclaredFields()) {
                ProtoField annotation = (ProtoField) field.getAnnotation(ProtoField.class);
                if (annotation != null && annotation.label() == Message.Label.REQUIRED) {
                    try {
                        this.requiredFields.add(builderType.getField(field.getName()));
                    } catch (NoSuchFieldException e) {
                        throw new AssertionError("No builder field found for message field " + field.getName());
                    }
                }
            }
            Collections.sort(this.requiredFields, ORDER_BY_FIELD_NAME);
        } catch (ClassNotFoundException e2) {
            throw new AssertionError("No message class found for builder type " + builderTypeName);
        }
    }

    public <B extends Message.Builder> void checkRequiredFields(B builder) {
        StringBuilder sb;
        String plural = "";
        int i = 0;
        try {
            int size = this.requiredFields.size();
            StringBuilder sb2 = null;
            while (i < size) {
                try {
                    Field f = this.requiredFields.get(i);
                    if (f.get(builder) == null) {
                        if (sb2 == null) {
                            sb = new StringBuilder();
                        } else {
                            plural = "s";
                            sb = sb2;
                        }
                        sb.append("\n  ");
                        sb.append(f.getName());
                    } else {
                        sb = sb2;
                    }
                    i++;
                    sb2 = sb;
                } catch (IllegalAccessException e) {
                    StringBuilder sb3 = sb2;
                    throw new AssertionError("Unable to access required fields");
                }
            }
            if (sb2 != null) {
                throw new IllegalStateException("Required field" + plural + " not set:" + sb2);
            }
        } catch (IllegalAccessException e2) {
        }
    }
}
