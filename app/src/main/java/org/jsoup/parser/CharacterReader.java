package org.jsoup.parser;

import java.util.Arrays;
import java.util.Locale;
import org.jsoup.helper.Validate;

final class CharacterReader {
    static final char EOF = '￿';
    private static final int maxCacheLen = 12;
    private final char[] input;
    private final int length;
    private int mark = 0;
    private int pos = 0;
    private final String[] stringCache = new String[512];

    CharacterReader(String input2) {
        Validate.notNull(input2);
        this.input = input2.toCharArray();
        this.length = this.input.length;
    }

    /* access modifiers changed from: package-private */
    public int pos() {
        return this.pos;
    }

    /* access modifiers changed from: package-private */
    public boolean isEmpty() {
        return this.pos >= this.length;
    }

    /* access modifiers changed from: package-private */
    public char current() {
        return this.pos >= this.length ? EOF : this.input[this.pos];
    }

    /* access modifiers changed from: package-private */
    public char consume() {
        char val = this.pos >= this.length ? EOF : this.input[this.pos];
        this.pos++;
        return val;
    }

    /* access modifiers changed from: package-private */
    public void unconsume() {
        this.pos--;
    }

    /* access modifiers changed from: package-private */
    public void advance() {
        this.pos++;
    }

    /* access modifiers changed from: package-private */
    public void mark() {
        this.mark = this.pos;
    }

    /* access modifiers changed from: package-private */
    public void rewindToMark() {
        this.pos = this.mark;
    }

    /* access modifiers changed from: package-private */
    public String consumeAsString() {
        char[] cArr = this.input;
        int i = this.pos;
        this.pos = i + 1;
        return new String(cArr, i, 1);
    }

    /* access modifiers changed from: package-private */
    public int nextIndexOf(char c) {
        for (int i = this.pos; i < this.length; i++) {
            if (c == this.input[i]) {
                return i - this.pos;
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int nextIndexOf(CharSequence seq) {
        char startChar = seq.charAt(0);
        int offset = this.pos;
        while (offset < this.length) {
            if (startChar != this.input[offset]) {
                do {
                    offset++;
                    if (offset >= this.length) {
                        break;
                    }
                } while (startChar == this.input[offset]);
            }
            int i = offset + 1;
            int last = (seq.length() + i) - 1;
            if (offset < this.length && last <= this.length) {
                int j = 1;
                while (i < last && seq.charAt(j) == this.input[i]) {
                    i++;
                    j++;
                }
                if (i == last) {
                    return offset - this.pos;
                }
            }
            offset++;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public String consumeTo(char c) {
        int offset = nextIndexOf(c);
        if (offset == -1) {
            return consumeToEnd();
        }
        String consumed = cacheString(this.pos, offset);
        this.pos += offset;
        return consumed;
    }

    /* access modifiers changed from: package-private */
    public String consumeTo(String seq) {
        int offset = nextIndexOf((CharSequence) seq);
        if (offset == -1) {
            return consumeToEnd();
        }
        String consumed = cacheString(this.pos, offset);
        this.pos += offset;
        return consumed;
    }

    /* access modifiers changed from: package-private */
    public String consumeToAny(char... chars) {
        int start = this.pos;
        int remaining = this.length;
        loop0:
        while (this.pos < remaining) {
            for (char c : chars) {
                if (this.input[this.pos] == c) {
                    break loop0;
                }
            }
            this.pos++;
        }
        if (this.pos > start) {
            return cacheString(start, this.pos - start);
        }
        return "";
    }

    /* access modifiers changed from: package-private */
    public String consumeToAnySorted(char... chars) {
        int start = this.pos;
        int remaining = this.length;
        char[] val = this.input;
        while (this.pos < remaining && Arrays.binarySearch(chars, val[this.pos]) < 0) {
            this.pos++;
        }
        return this.pos > start ? cacheString(start, this.pos - start) : "";
    }

    /* access modifiers changed from: package-private */
    public String consumeData() {
        int start = this.pos;
        int remaining = this.length;
        char[] val = this.input;
        while (this.pos < remaining && (c = val[this.pos]) != '&' && c != '<' && c != 0) {
            this.pos++;
        }
        return this.pos > start ? cacheString(start, this.pos - start) : "";
    }

    /* access modifiers changed from: package-private */
    public String consumeTagName() {
        int start = this.pos;
        int remaining = this.length;
        char[] val = this.input;
        while (this.pos < remaining && (c = val[this.pos]) != 9 && c != 10 && c != 13 && c != 12 && c != ' ' && c != '/' && c != '>' && c != 0) {
            this.pos++;
        }
        return this.pos > start ? cacheString(start, this.pos - start) : "";
    }

    /* access modifiers changed from: package-private */
    public String consumeToEnd() {
        String data = cacheString(this.pos, this.length - this.pos);
        this.pos = this.length;
        return data;
    }

    /* access modifiers changed from: package-private */
    public String consumeLetterSequence() {
        int start = this.pos;
        while (this.pos < this.length && (((c = this.input[this.pos]) >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
            this.pos++;
        }
        return cacheString(start, this.pos - start);
    }

    /* access modifiers changed from: package-private */
    public String consumeLetterThenDigitSequence() {
        int start = this.pos;
        while (this.pos < this.length && (((c = this.input[this.pos]) >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
            this.pos++;
        }
        while (!isEmpty() && (c = this.input[this.pos]) >= '0' && c <= '9') {
            this.pos++;
        }
        return cacheString(start, this.pos - start);
    }

    /* access modifiers changed from: package-private */
    public String consumeHexSequence() {
        int start = this.pos;
        while (this.pos < this.length && (((c = this.input[this.pos]) >= '0' && c <= '9') || ((c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f')))) {
            this.pos++;
        }
        return cacheString(start, this.pos - start);
    }

    /* access modifiers changed from: package-private */
    public String consumeDigitSequence() {
        int start = this.pos;
        while (this.pos < this.length && (c = this.input[this.pos]) >= '0' && c <= '9') {
            this.pos++;
        }
        return cacheString(start, this.pos - start);
    }

    /* access modifiers changed from: package-private */
    public boolean matches(char c) {
        return !isEmpty() && this.input[this.pos] == c;
    }

    /* access modifiers changed from: package-private */
    public boolean matches(String seq) {
        int scanLength = seq.length();
        if (scanLength > this.length - this.pos) {
            return false;
        }
        for (int offset = 0; offset < scanLength; offset++) {
            if (seq.charAt(offset) != this.input[this.pos + offset]) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesIgnoreCase(String seq) {
        int scanLength = seq.length();
        if (scanLength > this.length - this.pos) {
            return false;
        }
        for (int offset = 0; offset < scanLength; offset++) {
            if (Character.toUpperCase(seq.charAt(offset)) != Character.toUpperCase(this.input[this.pos + offset])) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesAny(char... seq) {
        if (isEmpty()) {
            return false;
        }
        char c = this.input[this.pos];
        for (char seek : seq) {
            if (seek == c) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesAnySorted(char[] seq) {
        return !isEmpty() && Arrays.binarySearch(seq, this.input[this.pos]) >= 0;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesLetter() {
        if (isEmpty()) {
            return false;
        }
        char c = this.input[this.pos];
        if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesDigit() {
        char c;
        if (!isEmpty() && (c = this.input[this.pos]) >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean matchConsume(String seq) {
        if (!matches(seq)) {
            return false;
        }
        this.pos += seq.length();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean matchConsumeIgnoreCase(String seq) {
        if (!matchesIgnoreCase(seq)) {
            return false;
        }
        this.pos += seq.length();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean containsIgnoreCase(String seq) {
        return nextIndexOf((CharSequence) seq.toLowerCase(Locale.ENGLISH)) > -1 || nextIndexOf((CharSequence) seq.toUpperCase(Locale.ENGLISH)) > -1;
    }

    public String toString() {
        return new String(this.input, this.pos, this.length - this.pos);
    }

    private String cacheString(int start, int count) {
        char[] val = this.input;
        String[] cache = this.stringCache;
        if (count > 12) {
            return new String(val, start, count);
        }
        int hash = 0;
        int i = 0;
        int offset = start;
        while (i < count) {
            hash = (hash * 31) + val[offset];
            i++;
            offset++;
        }
        int index = hash & (cache.length - 1);
        String cached = cache[index];
        if (cached == null) {
            String cached2 = new String(val, start, count);
            cache[index] = cached2;
            return cached2;
        } else if (!rangeEquals(start, count, cached)) {
            return new String(val, start, count);
        } else {
            return cached;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean rangeEquals(int start, int count, String cached) {
        if (count != cached.length()) {
            return false;
        }
        char[] one = this.input;
        int i = start;
        int j = 0;
        while (true) {
            int j2 = j;
            int i2 = i;
            int count2 = count;
            count = count2 - 1;
            if (count2 == 0) {
                return true;
            }
            i = i2 + 1;
            j = j2 + 1;
            if (one[i2] != cached.charAt(j2)) {
                return false;
            }
        }
    }
}
