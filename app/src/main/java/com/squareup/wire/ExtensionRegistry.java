package com.squareup.wire;

import java.util.LinkedHashMap;
import java.util.Map;

final class ExtensionRegistry {
    private final Map<Class<? extends ExtendableMessage>, Map<String, Extension<?, ?>>> extensionsByName = new LinkedHashMap();
    private final Map<Class<? extends ExtendableMessage>, Map<Integer, Extension<?, ?>>> extensionsByTag = new LinkedHashMap();

    ExtensionRegistry() {
    }

    public <T extends ExtendableMessage<?>, E> void add(Extension<T, E> extension) {
        Class<T> extendedType = extension.getExtendedType();
        Map<Integer, Extension<?, ?>> tagMap = this.extensionsByTag.get(extendedType);
        Map<String, Extension<?, ?>> nameMap = this.extensionsByName.get(extendedType);
        if (tagMap == null) {
            tagMap = new LinkedHashMap<>();
            nameMap = new LinkedHashMap<>();
            this.extensionsByTag.put(extendedType, tagMap);
            this.extensionsByName.put(extendedType, nameMap);
        }
        tagMap.put(Integer.valueOf(extension.getTag()), extension);
        nameMap.put(extension.getName(), extension);
    }

    public <T extends ExtendableMessage<?>, E> Extension<T, E> getExtension(Class<T> messageClass, int tag) {
        Map<Integer, Extension<?, ?>> map = this.extensionsByTag.get(messageClass);
        if (map == null) {
            return null;
        }
        return map.get(Integer.valueOf(tag));
    }

    public <T extends ExtendableMessage<?>, E> Extension<T, E> getExtension(Class<T> messageClass, String name) {
        Map<String, Extension<?, ?>> nameMap = this.extensionsByName.get(messageClass);
        if (nameMap == null) {
            return null;
        }
        return nameMap.get(name);
    }
}
