package com.tencent.ieg.apollo.voice;

import android.content.Context;

public class ApolloVoice {
    private static ApolloVoice instance;

    public static synchronized ApolloVoice Instance() {
        ApolloVoice apolloVoice;
        synchronized (ApolloVoice.class) {
            if (instance == null) {
                instance = new ApolloVoice();
            }
            apolloVoice = instance;
        }
        return apolloVoice;
    }

    public Boolean Init(Context ctxt) {
        Config.SetContext(ctxt);
        return true;
    }
}
