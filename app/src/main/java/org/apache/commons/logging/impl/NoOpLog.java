package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;

@Deprecated
public class NoOpLog implements Serializable, Log {
    public NoOpLog() {
        throw new RuntimeException("Stub!");
    }

    public NoOpLog(String name) {
        throw new RuntimeException("Stub!");
    }

    public void trace(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void trace(Object message, Throwable t) {
        throw new RuntimeException("Stub!");
    }

    public void debug(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void debug(Object message, Throwable t) {
        throw new RuntimeException("Stub!");
    }

    public void info(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void info(Object message, Throwable t) {
        throw new RuntimeException("Stub!");
    }

    public void warn(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void warn(Object message, Throwable t) {
        throw new RuntimeException("Stub!");
    }

    public void error(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void error(Object message, Throwable t) {
        throw new RuntimeException("Stub!");
    }

    public void fatal(Object message) {
        throw new RuntimeException("Stub!");
    }

    public void fatal(Object message, Throwable t) {
        throw new RuntimeException("Stub!");
    }

    public final boolean isDebugEnabled() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isErrorEnabled() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isFatalEnabled() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isInfoEnabled() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isTraceEnabled() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isWarnEnabled() {
        throw new RuntimeException("Stub!");
    }
}
