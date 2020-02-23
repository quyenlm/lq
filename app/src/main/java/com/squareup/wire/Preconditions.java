package com.squareup.wire;

final class Preconditions {
    private Preconditions() {
    }

    static void checkNotNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " == null");
        }
    }

    static void checkArgument(boolean assertion, String message) {
        if (!assertion) {
            throw new IllegalArgumentException(message);
        }
    }
}
