package com.amazonaws.util;

import com.appsflyer.share.Constants;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

public enum Classes {
    ;

    public static Class<?> childClassOf(Class<?> parentClass, Object instance) {
        if (instance == null || instance == Object.class) {
            return null;
        }
        if (parentClass != null && parentClass.isInterface()) {
            return null;
        }
        Class<?> childClass = instance.getClass();
        while (true) {
            Class<? super Object> superclass = childClass.getSuperclass();
            if (superclass == parentClass) {
                return childClass;
            }
            if (superclass == null) {
                return null;
            }
            childClass = superclass;
        }
    }

    public static JarFile jarFileOf(Class<?> klass) {
        URL url = klass.getResource(Constants.URL_PATH_DELIMITER + klass.getName().replace('.', '/') + ".class");
        if (url == null) {
            return null;
        }
        String s = url.getFile();
        int beginIndex = s.indexOf("file:") + "file:".length();
        int endIndex = s.indexOf(".jar!");
        if (endIndex == -1) {
            return null;
        }
        File file = new File(s.substring(beginIndex, endIndex + ".jar".length()));
        try {
            if (file.exists()) {
                return new JarFile(file);
            }
            return null;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
