package com.tencent.component.data;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SafeStringQueue implements Iterable<String> {
    private ConcurrentLinkedQueue<String> bufferQueue;
    private AtomicInteger bufferSize;

    public SafeStringQueue() {
        this.bufferQueue = null;
        this.bufferSize = null;
        this.bufferQueue = new ConcurrentLinkedQueue<>();
        this.bufferSize = new AtomicInteger(0);
    }

    public int addToBuffer(String str) {
        int dataLen = str.length();
        this.bufferQueue.add(str);
        return this.bufferSize.addAndGet(dataLen);
    }

    public void writeAndFlush(Writer writer, char[] buffer) throws IOException {
        if (writer != null && buffer != null && buffer.length != 0) {
            int bufferLen = buffer.length;
            int bufferRestLen = bufferLen;
            int bufferPos = 0;
            try {
                Iterator<String> it = iterator();
                while (it.hasNext()) {
                    String str = it.next();
                    int strPos = 0;
                    int strRestLen = str.length();
                    while (strRestLen > 0) {
                        int writeLen = bufferRestLen > strRestLen ? strRestLen : bufferRestLen;
                        str.getChars(strPos, strPos + writeLen, buffer, bufferPos);
                        bufferRestLen -= writeLen;
                        bufferPos += writeLen;
                        strRestLen -= writeLen;
                        strPos += writeLen;
                        if (bufferRestLen == 0) {
                            writer.write(buffer, 0, bufferLen);
                            bufferPos = 0;
                            bufferRestLen = bufferLen;
                        }
                    }
                }
                if (bufferPos > 0) {
                    writer.write(buffer, 0, bufferPos);
                }
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getBufferSize() {
        return this.bufferSize.get();
    }

    public void clear() {
        this.bufferQueue.clear();
        this.bufferSize.set(0);
    }

    public Iterator<String> iterator() {
        return this.bufferQueue.iterator();
    }
}
