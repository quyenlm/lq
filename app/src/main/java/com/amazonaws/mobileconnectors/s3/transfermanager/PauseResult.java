package com.amazonaws.mobileconnectors.s3.transfermanager;

@Deprecated
public final class PauseResult<T> {
    private final T infoToResume;
    private final PauseStatus pauseStatus;

    public PauseResult(PauseStatus pauseStatus2, T infoToResume2) {
        if (pauseStatus2 == null) {
            throw new IllegalArgumentException();
        }
        this.pauseStatus = pauseStatus2;
        this.infoToResume = infoToResume2;
    }

    public PauseResult(PauseStatus pauseStatus2) {
        this(pauseStatus2, (Object) null);
    }

    public PauseStatus getPauseStatus() {
        return this.pauseStatus;
    }

    public T getInfoToResume() {
        return this.infoToResume;
    }
}
