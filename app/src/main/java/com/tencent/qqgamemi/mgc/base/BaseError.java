package com.tencent.qqgamemi.mgc.base;

public class BaseError {
    protected int code;
    protected String humanText;
    protected String name;

    public BaseError(int code2, String name2) {
        this(code2, name2, (String) null);
    }

    public BaseError(int code2, String name2, String humanText2) {
        this.code = code2;
        this.name = name2;
        this.humanText = humanText2;
    }

    public void setHumanText(String humanText2) {
        this.humanText = humanText2;
    }

    public int getCode() {
        return this.code;
    }

    public String getCodeHexString() {
        return Integer.toHexString(this.code);
    }

    public String getName() {
        return this.name;
    }

    public String getHumanText() {
        return this.humanText;
    }

    public String toString() {
        return "[Error: " + this.name + ", " + this.code + "]";
    }
}
