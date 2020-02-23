package com.amazonaws.http;

import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionManager;

public final class IdleConnectionReaper extends Thread {
    private static final int PERIOD_MILLISECONDS = 60000;
    private static final ArrayList<ClientConnectionManager> connectionManagers = new ArrayList<>();
    private static IdleConnectionReaper instance;
    static final Log log = LogFactory.getLog(IdleConnectionReaper.class);
    private volatile boolean shuttingDown;

    private IdleConnectionReaper() {
        super("java-sdk-http-connection-reaper");
        setDaemon(true);
    }

    public static synchronized boolean registerConnectionManager(ClientConnectionManager connectionManager) {
        boolean add;
        synchronized (IdleConnectionReaper.class) {
            if (instance == null) {
                instance = new IdleConnectionReaper();
                instance.start();
            }
            add = connectionManagers.add(connectionManager);
        }
        return add;
    }

    public static synchronized boolean removeConnectionManager(ClientConnectionManager connectionManager) {
        boolean b;
        synchronized (IdleConnectionReaper.class) {
            b = connectionManagers.remove(connectionManager);
            if (connectionManagers.isEmpty()) {
                shutdown();
            }
        }
        return b;
    }

    private void markShuttingDown() {
        this.shuttingDown = true;
    }

    public void run() {
        List<ClientConnectionManager> list;
        while (!this.shuttingDown) {
            try {
                Thread.sleep(Constants.WATCHDOG_WAKE_TIMER);
                synchronized (IdleConnectionReaper.class) {
                    list = (List) connectionManagers.clone();
                }
                for (ClientConnectionManager connectionManager : list) {
                    connectionManager.closeIdleConnections(60, TimeUnit.SECONDS);
                }
            } catch (Exception t) {
                log.warn("Unable to close idle connections", t);
            } catch (Throwable t2) {
                log.debug("Reaper thread: ", t2);
            }
        }
        log.debug("Shutting down reaper thread.");
    }

    public static synchronized boolean shutdown() {
        boolean z;
        synchronized (IdleConnectionReaper.class) {
            if (instance != null) {
                instance.markShuttingDown();
                instance.interrupt();
                connectionManagers.clear();
                instance = null;
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    static synchronized int size() {
        int size;
        synchronized (IdleConnectionReaper.class) {
            size = connectionManagers.size();
        }
        return size;
    }
}
