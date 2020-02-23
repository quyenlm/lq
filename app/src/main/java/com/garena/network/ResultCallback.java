package com.garena.network;

public interface ResultCallback<S, T> {
    void onCompleted(Exception exc, S s, T t);
}
