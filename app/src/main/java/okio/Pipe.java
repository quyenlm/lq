package okio;

import java.io.IOException;

public final class Pipe {
    final Buffer buffer = new Buffer();
    final long maxBufferSize;
    private final Sink sink = new PipeSink();
    boolean sinkClosed;
    private final Source source = new PipeSource();
    boolean sourceClosed;

    public Pipe(long maxBufferSize2) {
        if (maxBufferSize2 < 1) {
            throw new IllegalArgumentException("maxBufferSize < 1: " + maxBufferSize2);
        }
        this.maxBufferSize = maxBufferSize2;
    }

    public Source source() {
        return this.source;
    }

    public Sink sink() {
        return this.sink;
    }

    final class PipeSink implements Sink {
        final Timeout timeout = new Timeout();

        PipeSink() {
        }

        /* JADX WARNING: CFG modification limit reached, blocks count: 127 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void write(okio.Buffer r13, long r14) throws java.io.IOException {
            /*
                r12 = this;
                r10 = 0
                okio.Pipe r4 = okio.Pipe.this
                okio.Buffer r5 = r4.buffer
                monitor-enter(r5)
                okio.Pipe r4 = okio.Pipe.this     // Catch:{ all -> 0x0015 }
                boolean r4 = r4.sinkClosed     // Catch:{ all -> 0x0015 }
                if (r4 == 0) goto L_0x0033
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0015 }
                java.lang.String r6 = "closed"
                r4.<init>(r6)     // Catch:{ all -> 0x0015 }
                throw r4     // Catch:{ all -> 0x0015 }
            L_0x0015:
                r4 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x0015 }
                throw r4
            L_0x0018:
                okio.Pipe r4 = okio.Pipe.this     // Catch:{ all -> 0x0015 }
                long r6 = r4.maxBufferSize     // Catch:{ all -> 0x0015 }
                okio.Pipe r4 = okio.Pipe.this     // Catch:{ all -> 0x0015 }
                okio.Buffer r4 = r4.buffer     // Catch:{ all -> 0x0015 }
                long r8 = r4.size()     // Catch:{ all -> 0x0015 }
                long r0 = r6 - r8
                int r4 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
                if (r4 != 0) goto L_0x0045
                okio.Timeout r4 = r12.timeout     // Catch:{ all -> 0x0015 }
                okio.Pipe r6 = okio.Pipe.this     // Catch:{ all -> 0x0015 }
                okio.Buffer r6 = r6.buffer     // Catch:{ all -> 0x0015 }
                r4.waitUntilNotified(r6)     // Catch:{ all -> 0x0015 }
            L_0x0033:
                int r4 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
                if (r4 <= 0) goto L_0x0059
                okio.Pipe r4 = okio.Pipe.this     // Catch:{ all -> 0x0015 }
                boolean r4 = r4.sourceClosed     // Catch:{ all -> 0x0015 }
                if (r4 == 0) goto L_0x0018
                java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x0015 }
                java.lang.String r6 = "source is closed"
                r4.<init>(r6)     // Catch:{ all -> 0x0015 }
                throw r4     // Catch:{ all -> 0x0015 }
            L_0x0045:
                long r2 = java.lang.Math.min(r0, r14)     // Catch:{ all -> 0x0015 }
                okio.Pipe r4 = okio.Pipe.this     // Catch:{ all -> 0x0015 }
                okio.Buffer r4 = r4.buffer     // Catch:{ all -> 0x0015 }
                r4.write((okio.Buffer) r13, (long) r2)     // Catch:{ all -> 0x0015 }
                long r14 = r14 - r2
                okio.Pipe r4 = okio.Pipe.this     // Catch:{ all -> 0x0015 }
                okio.Buffer r4 = r4.buffer     // Catch:{ all -> 0x0015 }
                r4.notifyAll()     // Catch:{ all -> 0x0015 }
                goto L_0x0033
            L_0x0059:
                monitor-exit(r5)     // Catch:{ all -> 0x0015 }
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Pipe.PipeSink.write(okio.Buffer, long):void");
        }

        public void flush() throws IOException {
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                }
                while (Pipe.this.buffer.size() > 0) {
                    if (Pipe.this.sourceClosed) {
                        throw new IOException("source is closed");
                    }
                    this.timeout.waitUntilNotified(Pipe.this.buffer);
                }
            }
        }

        public void close() throws IOException {
            synchronized (Pipe.this.buffer) {
                if (!Pipe.this.sinkClosed) {
                    try {
                        flush();
                    } finally {
                        Pipe.this.sinkClosed = true;
                        Pipe.this.buffer.notifyAll();
                    }
                }
            }
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }

    final class PipeSource implements Source {
        final Timeout timeout = new Timeout();

        PipeSource() {
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            long read;
            synchronized (Pipe.this.buffer) {
                if (!Pipe.this.sourceClosed) {
                    while (true) {
                        if (Pipe.this.buffer.size() != 0) {
                            read = Pipe.this.buffer.read(sink, byteCount);
                            Pipe.this.buffer.notifyAll();
                            break;
                        } else if (Pipe.this.sinkClosed) {
                            read = -1;
                            break;
                        } else {
                            this.timeout.waitUntilNotified(Pipe.this.buffer);
                        }
                    }
                } else {
                    throw new IllegalStateException("closed");
                }
            }
            return read;
        }

        public void close() throws IOException {
            synchronized (Pipe.this.buffer) {
                Pipe.this.sourceClosed = true;
                Pipe.this.buffer.notifyAll();
            }
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }
}
