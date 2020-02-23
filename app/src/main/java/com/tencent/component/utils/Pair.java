package com.tencent.component.utils;

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F first2, S second2) {
        this.first = first2;
        this.second = second2;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        try {
            Pair<F, S> other = (Pair) o;
            if (!this.first.equals(other.first) || !this.second.equals(other.second)) {
                return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return ((this.first.hashCode() + 527) * 31) + this.second.hashCode();
    }

    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<>(a, b);
    }
}
