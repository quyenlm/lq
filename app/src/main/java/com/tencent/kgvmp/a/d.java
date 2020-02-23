package com.tencent.kgvmp.a;

public enum d {
    NO_SET(-1),
    DEFAULT(0),
    START(1),
    UPDATE(2),
    LOGIN_LOAD(3),
    MAIN_UI(4),
    SCENE_LOAD(5),
    SCENE_LOAD_WAIT(6),
    PLAYING(7),
    WATCH_MODE(8);
    
    private int id;

    private d(int i) {
        this.id = i;
    }

    public int getID() {
        return this.id;
    }

    public String getSceneID() {
        return String.valueOf(this.id);
    }
}
