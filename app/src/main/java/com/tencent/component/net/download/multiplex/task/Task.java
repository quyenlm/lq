package com.tencent.component.net.download.multiplex.task;

import com.tencent.component.net.download.multiplex.DownloaderLog;
import com.tencent.component.net.download.multiplex.http.MttRequest;
import com.tencent.component.net.download.multiplex.http.MttResponse;
import com.tencent.component.net.download.multiplex.http.Requester;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Task implements Runnable {
    private static final String DEFAULT_TASK_URL = "";
    public static final int FAIL_TO_BROKER_TASK_MASK = 8;
    public static final int LOCAL_TASK_MASK = 4;
    public static final int LOGIN_TASK_MASK = 16;
    public static final int MAX_TRYING_TIME = 300;
    public static final byte METHOD_GET = 0;
    public static final byte METHOD_POST = 1;
    public static final byte NETWORK_STATUS_CONNECTED = 2;
    public static final byte NETWORK_STATUS_CREATED = 1;
    public static final byte NETWORK_STATUS_RECEIVED = 4;
    public static final byte NETWORK_STATUS_SENDED = 3;
    public static final byte NETWORK_STATUS_UNKNOW = -1;
    public static final int PREREAD_TASK_MASK = 1;
    public static final int RELOAD_TASK_MASK = 2;
    public static final int SWITCH_LOGIN_TASK_MASK = 32;
    private static final String TAG = "Task";
    public static final byte TASK_CSS = 2;
    public static final byte TASK_DOWNLOAD = 3;
    public static final byte TASK_EXT_EVENT_DELETED = 1;
    public static final byte TASK_IMAGE = 1;
    public static final byte TASK_JS = 4;
    public static final byte TASK_NONE = -1;
    public static final byte TASK_PAGE = 0;
    public static final byte TASK_STATUS_CANCELED = 6;
    public static final byte TASK_STATUS_CANCELING = 7;
    public static final byte TASK_STATUS_COMPLETED = 3;
    public static final byte TASK_STATUS_CREATED = 0;
    public static final byte TASK_STATUS_DELETED = 8;
    public static final byte TASK_STATUS_FAILED = 5;
    public static final byte TASK_STATUS_NONE = -1;
    public static final byte TASK_STATUS_PROGRESS = 2;
    public static final byte TASK_STATUS_STARTED = 1;
    public static final byte TASK_STATUS_TIMEOUT = 4;
    private int mApn;
    protected boolean mCanceled = false;
    private int mExtEvent;
    private ReadWriteLock mLock = new ReentrantReadWriteLock();
    protected MttRequest mMttRequest;
    protected MttResponse mMttResponse;
    private boolean mNeedNotifyCanceled = false;
    private boolean mNeedStatFlow = false;
    protected int mNetworkStatus = -1;
    protected List<TaskObserver> mObservers;
    protected Requester mRequester;
    public byte mStatus = 0;
    public int mTaskAttr = 0;
    public byte mTaskType;
    protected int mWasteTime;

    public abstract void cancel();

    public byte getStatus() {
        return this.mStatus;
    }

    public int getExtEvent() {
        return this.mExtEvent;
    }

    public void setNeedStatFlow(boolean need) {
        this.mNeedStatFlow = need;
    }

    public void setNeedNotfiyCanceled(boolean needNotifyCanceled) {
        this.mNeedNotifyCanceled = needNotifyCanceled;
    }

    public MttRequest getMttRequest() {
        return this.mMttRequest;
    }

    public MttResponse getMttResponse() {
        return this.mMttResponse;
    }

    public void setMttResponse(MttResponse mttResponse) {
        this.mMttResponse = mttResponse;
        if (this.mRequester != null) {
            this.mApn = this.mRequester.getApn();
        } else {
            this.mApn = 0;
        }
    }

    public void addTaskAttr(int attr) {
        this.mTaskAttr |= attr;
    }

    public void removeTaskAttr(int attr) {
        this.mTaskAttr &= attr ^ -1;
    }

    public boolean isTaskAttrAdded(int attr) {
        return (this.mTaskAttr & attr) != 0;
    }

    public int getFlow() {
        int i = 0;
        int flow = this.mMttRequest != null ? this.mMttRequest.getFlow() : 0;
        if (this.mMttResponse != null) {
            i = this.mMttResponse.getFlow();
        }
        return flow + i;
    }

    public int getApn() {
        return this.mApn;
    }

    public Requester getRequester() {
        return this.mRequester;
    }

    public String getTaskUrl() {
        return "";
    }

    public int getNetworkStatus() {
        return this.mNetworkStatus;
    }

    public boolean isCanceled() {
        return this.mCanceled;
    }

    /* access modifiers changed from: protected */
    public void closeQuietly() {
        if (this.mRequester != null && this.mRequester.mIsNeedBackWriteCookie) {
            DownloaderLog.d(TAG, "[Task] BackWrite Cookie");
        }
        if (this.mRequester != null) {
            this.mRequester.close();
            this.mRequester = null;
        }
    }

    /* access modifiers changed from: protected */
    public void fireObserverEvent() {
        if (this.mNeedStatFlow) {
        }
        fireObserverEvent(this.mStatus);
    }

    public void firePreDeletedEvent() {
        if (this.mObservers != null) {
            Iterator i$ = copyObserverList().iterator();
            while (i$.hasNext()) {
                i$.next().onTaskExtEvent(this);
            }
        }
    }

    private ArrayList<TaskObserver> copyObserverList() {
        ArrayList<TaskObserver> observerList = null;
        try {
            this.mLock.readLock().lock();
            ArrayList<TaskObserver> observerList2 = new ArrayList<>(this.mObservers);
            this.mLock.readLock().unlock();
            observerList = observerList2;
        } catch (Exception e) {
            DownloaderLog.e(TAG, e.getMessage(), e);
            this.mLock.readLock().unlock();
        } catch (Throwable th) {
            this.mLock.readLock().unlock();
            throw th;
        }
        if (observerList == null) {
            return new ArrayList<>();
        }
        return observerList;
    }

    public void fireExtEvent() {
        if (this.mObservers != null) {
            Iterator i$ = copyObserverList().iterator();
            while (i$.hasNext()) {
                i$.next().onTaskExtEvent(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void fireObserverEvent(int status) {
        if (this.mObservers != null) {
            try {
                DownloaderLog.i(TAG, "[Task] ObserverEvent status : " + status);
                ArrayList<TaskObserver> observerList = copyObserverList();
                switch (status) {
                    case 0:
                        Iterator i$ = observerList.iterator();
                        while (i$.hasNext()) {
                            i$.next().onTaskCreated(this);
                        }
                        return;
                    case 1:
                        Iterator<TaskObserver> listObservers = observerList.iterator();
                        while (listObservers.hasNext()) {
                            listObservers.next().onTaskStarted(this);
                        }
                        return;
                    case 2:
                        Iterator i$2 = observerList.iterator();
                        while (i$2.hasNext()) {
                            i$2.next().onTaskProgress(this);
                        }
                        return;
                    case 3:
                        Iterator i$3 = observerList.iterator();
                        while (i$3.hasNext()) {
                            i$3.next().onTaskCompleted(this);
                        }
                        return;
                    case 4:
                    case 5:
                        Iterator i$4 = observerList.iterator();
                        while (i$4.hasNext()) {
                            i$4.next().onTaskFailed(this);
                        }
                        return;
                    case 6:
                    case 7:
                        Iterator<TaskObserver> listObservers2 = observerList.iterator();
                        while (listObservers2.hasNext()) {
                            listObservers2.next().onTaskFailed(this);
                        }
                        return;
                    default:
                        return;
                }
            } catch (Exception e) {
                DownloaderLog.e(TAG, e.getMessage(), e);
            }
            DownloaderLog.e(TAG, e.getMessage(), e);
        }
    }

    public void addObserver(TaskObserver observer) {
        if (observer != null) {
            try {
                this.mLock.writeLock().lock();
                if (this.mObservers == null) {
                    this.mObservers = new ArrayList(3);
                }
                for (TaskObserver o : this.mObservers) {
                    if (o == observer) {
                        return;
                    }
                }
                this.mObservers.add(observer);
                this.mLock.writeLock().unlock();
            } catch (Exception e) {
                DownloaderLog.e(TAG, e.getMessage(), e);
            } finally {
                this.mLock.writeLock().unlock();
            }
        }
    }

    public void removeObserver(TaskObserver observer) {
        if (this.mObservers != null) {
            try {
                this.mLock.writeLock().lock();
                this.mObservers.remove(observer);
            } catch (Exception e) {
                DownloaderLog.e(TAG, e.getMessage(), e);
            } finally {
                this.mLock.writeLock().unlock();
            }
        }
    }

    public int getWasteTime() {
        return this.mWasteTime;
    }

    public void setConnectionClose() {
        this.mMttRequest.addHeader("Connection", "Close");
    }
}
