package com.tencent.ieg.apollo.voice;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Config {
    private static String LOGTAG = "ApolloVoiceTag";
    private static final String cfgName = "apollo_voice/config.json";
    private static Context mainContext;

    public static void SetContext(Context ctxt) {
        mainContext = ctxt;
    }

    public static String JSONCfg() {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(mainContext.getResources().getAssets().open(cfgName)));
            String Result = "";
            while (true) {
                String line = bufReader.readLine();
                if (line != null) {
                    Result = Result + line;
                } else {
                    Log.i(LOGTAG, "Get config :" + Result);
                    return Result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
