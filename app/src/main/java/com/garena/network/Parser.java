package com.garena.network;

public interface Parser<S, T> {
    T parse(S s);
}
