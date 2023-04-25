package org.example;

import java.lang.reflect.Array;
import java.util.Collection;

class CustomArrayImpl <T> implements CustomArray<T>{

    private T[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private final T[] DEFAULTCAPACITY_EMPTY_ARRAY= (T[]) new Object[]{};
    public CustomArrayImpl() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayImpl(int capacity) {
        this.array = (T[]) new Object[capacity];
    }

    public CustomArrayImpl(Collection<T> c) {
        T[] tempArray = (T[])new Object[c.size()];
        if ((size = tempArray.length) != 0) {
            array = tempArray;
        } else {
            array = (T[]) new Object[DEFAULT_CAPACITY];
        }
    }

    @Override
    public boolean add (T item) {
        addItem(item);

        return true;

    }

    @Override
    public void ensureCapacity(int minCapacity) {
        if (minCapacity >= array.length
                && !(array == DEFAULTCAPACITY_EMPTY_ARRAY
                && minCapacity <= DEFAULT_CAPACITY)) {
            grow(minCapacity);
        }
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public T set(int index, T item) {
        T oldValue = array[index];
        array[index] = item;
        return oldValue;
    }

    @Override
    public void remove(int index) {
        T[] tempArrayOne = (T[]) new Object[index];
        T[] tempArrayTwo = (T[]) new Object[array.length - index - 1];
        T[] tempArrayFinish = (T[]) new Object[tempArrayOne.length + tempArrayTwo.length];
        tempArrayOne = copyOfRemove(array, 0, tempArrayOne);
        tempArrayTwo = copyOfRemove(array, index + 1, tempArrayTwo);
        tempArrayFinish = copy(tempArrayOne, tempArrayFinish);
        tempArrayFinish = copyOfRange(tempArrayTwo, index, tempArrayFinish);
        array = tempArrayFinish;
        size = array.length;
    }

    @Override
    public boolean remove(T item) {
        trimToSize();
        boolean removeFlag = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                remove(i);
                removeFlag = true;
            }
        }
        return removeFlag;
    }

    @Override
    public void reverse() {
        T[] tempArray = (T[]) new Object[array.length];
        tempArray = copy(array,tempArray);
        for (int i = 0; i < tempArray.length; i++) {
            array[i] = tempArray[tempArray.length - 1 - i];
        }
    }

    @Override
    public int getCapacity() {
        return array.length;
    }

    @Override
    public int indexOf(T item) {
        int value = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                value = i;
                break;
            }
        }
        return value;
    }
    @Override
    public boolean contains(T item) {
        boolean containsFlag = false;
        for (int i = 0; i < array.length; i++) {
            if (item.equals(array[i])) {
                containsFlag = true;
                break;
            }
        }
        return containsFlag;
    }

    @Override
    public boolean addAll(T[] items) {
        Object[] array = items;
        int numNew = array.length;
        if (numNew == 0)
            return false;
        Object[] tempArray;
        final int s;
        if (numNew > (tempArray = this.array).length - (s = size)) {
            tempArray = grow(s + numNew);
        }
        System.arraycopy(array, 0, tempArray, s, numNew);
        size = array.length;
        return true;
    }

    @Override
    public boolean addAll(Collection<T> items) {
        Object[] array = items.toArray();
        int numNew = array.length;
        if (numNew == 0)
            return false;
        Object[] tempArray;
        final int s;
        if (numNew > (tempArray = this.array).length - (s = size)) {
            tempArray = grow(s + numNew);
        }
        System.arraycopy(array, 0, tempArray, s, numNew);
        size = array.length;
        return true;
    }

    @Override
    public boolean addAll(int index, T[] items) {
        int numNew = array.length;
        if (numNew == 0)
            return false;
        T[] tempArray;
        final int s;
        if (numNew > (tempArray = this.array).length - (s = size)) {
            tempArray = grow(s + numNew);
        }
        int numMoved = s - index;
        if (numMoved > 0) {
            array = copyOfRange(items, index, array);
        }
        array = copyOfRange(items, index, array);
        trimToSize();
        size = array.length;
        return true;
    }

    @Override
    public Object[] toArray() {

        return copy(array, size);

    }
    @Override
    public  String toString() {
//        trimToSize();
        if (array == null)
            return "null";

        int iMax = array.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.valueOf(array[i]));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    private void addItem(T item) {
        if (size >= array.length) {
            ensureCapacity(array.length);
        }
        array[size] = item;
        size++;
    }

    private T[] grow(int minCapacity) {
        int oldCapacity = array.length;
        if (oldCapacity > 0 || array != DEFAULTCAPACITY_EMPTY_ARRAY) {
            int newCapacity = DEFAULT_CAPACITY + minCapacity;
            return array = copy(array, newCapacity);
        } else {
            return array = (T[]) new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }


    private T[] copy(T[] originalArray, T[] tempArray) {
        for (int i = 0; i < originalArray.length; i++) {
            tempArray[i] = originalArray[i];
        }
        return tempArray;
    }

    private T[] copy(T[] originalArray, T[] tempArray, int index) {

        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = originalArray[i + index];
        }
        return tempArray;
    }
    private static <T,U> T[] copy(U[] original, int newLength, Class<? extends T[]> newType) {

        T[] copy = ((Object)newType == (Object)Object[].class)
                ? (T[]) new Object[newLength]
                : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }
    private static <T> T[] copy(T[] original, int newLength) {
        return (T[]) copy(original, newLength, original.getClass());
    }
    private T[] copyOfRange(T[] originalArray, int index, T[] tempArray) {
        if (array.length < originalArray.length + tempArray.length) {
            grow(originalArray.length + tempArray.length);
        }
        for (int i = 0; i < originalArray.length; i++) {
            tempArray[i + index] = originalArray[i];
        }
        return tempArray;
    }
    private T[] copyOfRemove(T[] originalArray, int index, T[] tempArray) {
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = originalArray[i + index];
        }
        return tempArray;
    }

    private void trimToSize() {
        int trimCount = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                trimCount++;
            }
        }
        T[] tempArray = (T[]) new Object[trimCount];
        for (int i = 0; i < tempArray.length; i++) {
            if (array[i] != null) {
                tempArray[i] = array[i];
            }
        }
        array = tempArray;
    }

}
