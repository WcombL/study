package com.lei.ch1;

public class UnsafeSequence {

    private int value;

    /** Returns a unique value. */
    public int getNext() {
        return value++;
    }
}
