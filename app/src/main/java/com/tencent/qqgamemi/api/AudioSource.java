package com.tencent.qqgamemi.api;

public enum AudioSource {
    SYSTEM(0),
    IN_GAME_AUDIO(1);
    
    private int value;

    private AudioSource(int value2) {
        this.value = value2;
    }

    public int intValue() {
        return this.value;
    }
}
