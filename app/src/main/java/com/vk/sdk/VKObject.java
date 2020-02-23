package com.vk.sdk;

import java.util.HashMap;
import java.util.Random;

public class VKObject {
    private static final HashMap<Long, VKObject> sRegisteredObjects = new HashMap<>();
    private long mRegisteredObjectId = 0;

    public static VKObject getRegisteredObject(long registeredObjectId) {
        return sRegisteredObjects.get(Long.valueOf(registeredObjectId));
    }

    public long registerObject() {
        if (sRegisteredObjects.containsKey(Long.valueOf(this.mRegisteredObjectId))) {
            return this.mRegisteredObjectId;
        }
        Random rand = new Random();
        while (true) {
            long nextRand = rand.nextLong();
            if (!sRegisteredObjects.containsKey(Long.valueOf(nextRand)) && nextRand != 0) {
                sRegisteredObjects.put(Long.valueOf(nextRand), this);
                this.mRegisteredObjectId = nextRand;
                return nextRand;
            }
        }
    }

    public void unregisterObject() {
        sRegisteredObjects.remove(Long.valueOf(this.mRegisteredObjectId));
        this.mRegisteredObjectId = 0;
    }
}
