package org.dynjs.runtime;

import java.util.Arrays;

import static org.dynjs.runtime.DynThreadContext.UNDEFINED;

public class DynArray {

    public static final int DEFAULT_ARRAY_SIZE = 16;
    public static final Object[] UNDEFINED_PREFILLED_ARRAY;

    static {
        Object[] prefilledArray = new Object[DEFAULT_ARRAY_SIZE * 8];
        for (int i = 0; i < prefilledArray.length; i++) {
            prefilledArray[i] = UNDEFINED;
        }
        UNDEFINED_PREFILLED_ARRAY = prefilledArray;
    }

    private Object[] array;

    public DynArray() {
        this.array = new Object[DEFAULT_ARRAY_SIZE];
        fillUndefinedArray(this.array, 0, this.array.length);
    }

    private void fillUndefinedArray(Object[] array, int from, int to) {
        int i;
        for (i = from; i + UNDEFINED_PREFILLED_ARRAY.length < to; i += UNDEFINED_PREFILLED_ARRAY.length) {
            System.arraycopy(UNDEFINED_PREFILLED_ARRAY, 0, array, i, UNDEFINED_PREFILLED_ARRAY.length);
        }
        System.arraycopy(UNDEFINED_PREFILLED_ARRAY, 0, array, i, to - i);
    }

    public void set(int index, Object value) {
        growIfNeeded(index);
        array[index] = value;
    }

    private void growIfNeeded(int index) {
        if (!checkBounds(index)) {
            Object[] reallocated = new Object[index + 1];
            System.arraycopy(this.array, 0, reallocated, 0, this.array.length);
            fillUndefinedArray(reallocated, this.array.length, index);
            this.array = reallocated;
        }
    }

    public Object get(int index) {
        if (checkBounds(index)) {
            return array[index];
        }
        return UNDEFINED;
    }

    private boolean checkBounds(int index) {
        return index < array.length;
    }

    public int length() {
        return array.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.array);
    }
}
