package org.apache.commons.logging.impl;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;

@Deprecated
public class Jdk14Logger implements Serializable, Log {
    protected static final Level dummyLevel = null;
    protected transient Logger logger;
    protected String name;

    public Jdk14Logger(String name2) {
        throw new RuntimeException("Stub!");
    }

    public void debug(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void debug(Object message, Throwable exception) {
        throw new RuntimeException("Stub!");
    }

    public void error(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void error(Object message, Throwable exception) {
        throw new RuntimeException("Stub!");
    }

    public void fatal(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void fatal(Object message, Throwable exception) {
        throw new RuntimeException("Stub!");
    }

    public Logger getLogger() {
        throw new RuntimeException("Stub!");
    }

    public void info(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void info(Object message, Throwable exception) {
        throw new RuntimeException("Stub!");
    }

    public boolean isDebugEnabled() {
        throw new RuntimeException("Stub!");
    }

    public boolean isErrorEnabled() {
        throw new RuntimeException("Stub!");
    }

    public boolean isFatalEnabled() {
        throw new RuntimeException("Stub!");
    }

    public boolean isInfoEnabled() {
        throw new RuntimeException("Stub!");
    }

    public boolean isTraceEnabled() {
        throw new RuntimeException("Stub!");
    }

    public boolean isWarnEnabled() {
        throw new RuntimeException("Stub!");
    }

    public void trace(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void trace(Object message, Throwable exception) {
        throw new RuntimeException("Stub!");
    }

    public void warn(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void warn(Object message, Throwable exception) {
        throw new RuntimeException("Stub!");
    }
}
