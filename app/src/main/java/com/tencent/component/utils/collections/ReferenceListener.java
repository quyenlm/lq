package com.tencent.component.utils.collections;

import java.util.EventListener;

public interface ReferenceListener extends EventListener {
    void referenceReleased(int i);
}
