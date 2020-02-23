package com.tencent.ugc;

import android.os.AsyncTask;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TXUGCPartsManager {
    private ArrayList<a> iPartsManagerObservers = new ArrayList<>();
    private int mDuration;
    private CopyOnWriteArrayList<PartInfo> mPartsList = new CopyOnWriteArrayList<>();

    public interface a {
        void a();

        void b();
    }

    public synchronized void setPartsManagerObserver(a aVar) {
        if (aVar != null) {
            if (!this.iPartsManagerObservers.contains(aVar)) {
                this.iPartsManagerObservers.add(aVar);
            }
        }
    }

    public synchronized void removePartsManagerObserver(a aVar) {
        if (aVar != null) {
            this.iPartsManagerObservers.remove(aVar);
        }
    }

    public void addClipInfo(PartInfo partInfo) {
        this.mPartsList.add(partInfo);
        this.mDuration = (int) (((long) this.mDuration) + partInfo.getDuration());
    }

    public int getDuration() {
        return this.mDuration;
    }

    public List<String> getPartsPathList() {
        ArrayList arrayList = new ArrayList();
        Iterator<PartInfo> it = this.mPartsList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getPath());
        }
        return arrayList;
    }

    public void deleteLastPart() {
        if (this.mPartsList.size() != 0) {
            PartInfo remove = this.mPartsList.remove(this.mPartsList.size() - 1);
            this.mDuration = (int) (((long) this.mDuration) - remove.getDuration());
            deleteFile(remove.getPath());
            callbackDeleteLastPart();
        }
    }

    public void deletePart(int i) {
        if (i > 0 && this.mPartsList.size() != 0) {
            PartInfo remove = this.mPartsList.remove(i - 1);
            this.mDuration = (int) (((long) this.mDuration) - remove.getDuration());
            deleteFile(remove.getPath());
        }
    }

    public void deleteAllParts() {
        Iterator<PartInfo> it = this.mPartsList.iterator();
        while (it.hasNext()) {
            deleteFile(it.next().getPath());
        }
        this.mPartsList.clear();
        this.mDuration = 0;
        callbackDeleteAllParts();
    }

    private void callbackDeleteLastPart() {
        synchronized (this) {
            Iterator<a> it = this.iPartsManagerObservers.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        }
    }

    private void callbackDeleteAllParts() {
        synchronized (this) {
            Iterator<a> it = this.iPartsManagerObservers.iterator();
            while (it.hasNext()) {
                it.next().b();
            }
        }
    }

    private void deleteFile(final String str) {
        new AsyncTask() {
            /* access modifiers changed from: protected */
            public Object doInBackground(Object[] objArr) {
                File file = new File(str);
                if (!file.exists()) {
                    return null;
                }
                file.delete();
                return null;
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Object[0]);
    }
}
