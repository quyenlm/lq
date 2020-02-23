package com.tencent.mid.local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class a {
    static List<String> a(File file) {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                fileReader.close();
                bufferedReader.close();
                return arrayList;
            }
            arrayList.add(readLine.trim());
        }
    }
}
