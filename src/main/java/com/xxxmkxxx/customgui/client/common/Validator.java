package com.xxxmkxxx.customgui.client.common;

public class Validator {
    public static void checkArrayIndex(int index, Object[] array) {
        checkNullObject(index);

        boolean isIndexPositive = index >= 0;
        boolean isIndexLessArraySize = index < array.length;

        if (!isIndexPositive)
            throw new IllegalArgumentException("This index - " + index + " less 0!");
        if (!isIndexLessArraySize)
            throw new IndexOutOfBoundsException("Array size = " + array.length + " index = " + index);
    }

    public static void checkNullObject(Object object) {
        boolean isNotNullObject = object != null;

        if (!isNotNullObject)
            throw new NullPointerException();
    }

    public static void checkArraySize(int size) {
        checkNullObject(size);

        boolean isSizePositive = size >= 0;

        if (!isSizePositive)
            throw new IllegalArgumentException("This size - " + size + " less 0!");
    }
}
