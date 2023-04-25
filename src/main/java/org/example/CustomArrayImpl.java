package org.example;

import java.lang.reflect.Array;
import java.util.Collection;

class CustomArrayImpl <T> implements CustomArray<T>{

    private T[] array; //Обобщенный массив
    private int size; //Количество элементов в массиве
    private static final int DEFAULT_CAPACITY = 10; //Стандартный размер массива
    private final T[] DEFAULTCAPACITY_EMPTY_ARRAY= (T[]) new Object[]{}; //Пустой массив стандартного размера



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

    /**
     * Add single item.
     */
    @Override
    public boolean add (T item) {
        addItem(item);
        return true;
    }

    /**
     * Add all items.
     *
     * @throws IllegalArgumentException if parameter items is null
     */
    @Override
    public boolean addAll(T[] items) {
        isIllegalArgumentNull(items);
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

    /**
     * Add all items.
     *
     * @throws IllegalArgumentException if parameter items is null
     */
    @Override
    public boolean addAll(Collection<T> items) {
        Object[] array = items.toArray();
        isIllegalArgumentNull((T[]) array);
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

    /**
     * Add items to current place in array.
     *
     * @param index - index
     * @param items - items for insert
     * @throws ArrayIndexOutOfBoundsException if index is out of bounds
     * @throws IllegalArgumentException       if parameter items is null
     */
    @Override
    public boolean addAll(int index, T[] items) {
        isIllegalArgumentNull(items);
        isArrayIndexOutOfBoundsException(index);
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

    /**
     * Grow current capacity to store new elements if needed.
     *
     * @param minCapacity - new elements count
     */
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
        trimToSize();
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Get item by index.
     *
     * @param index - index
     * @throws ArrayIndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public T get(int index) {
        isArrayIndexOutOfBoundsException(index);
        return array[index];
    }

    /**
     * Set item by index.
     *
     * @param index - index
     * @return old value
     * @throws ArrayIndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public T set(int index, T item) {
        isArrayIndexOutOfBoundsException(index);
        T oldValue = array[index];
        array[index] = item;
        return oldValue;
    }

    /**
     * Remove item by index.
     *
     * @param index - index
     * @throws ArrayIndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public void remove(int index) {
        isArrayIndexOutOfBoundsException(index);
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

    /**
     * Remove item by value. Remove first item occurrence.
     *
     * @param item - item
     * @return true if item was removed
     */
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

    /**
     * Reverse list.
     */
    @Override
    public void reverse() {
        T[] tempArray = (T[]) new Object[array.length];
        tempArray = copy(array,tempArray);
        for (int i = 0; i < tempArray.length; i++) {
            array[i] = tempArray[tempArray.length - 1 - i];
        }
    }

    /**
     * Get current capacity.
     */
    @Override
    public int getCapacity() {
        return array.length;
    }

    /**
     * Index of item.
     *
     * @param item - item
     * @return index of element or -1 of list doesn't contain element
     */
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

    /**
     * Checks if item exists.
     *
     * @param item - item
     * @return true or false
     */
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

    /**
     * Get copy of current array.
     */
    @Override
    public Object[] toArray() {

        return copy(array, size);

    }

    /**
     * Get content in format '[ element1 element2 ... elenentN ] or [ ] if empty.
     */
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

    /**
     * Support methods for work CustomArrayImpl
     */

    /**
     *
     checks before adding the size of the array and,
     if necessary, increases it,
     also counts the number of elements
     * @param item
     */
    private void addItem(T item) {
        if (size >= array.length) {
            ensureCapacity(array.length);
        }
        array[size] = item;
        size++;
    }


    /**
     * method to increase array
     * size increase factor 1.5
     * @param minCapacity
     * @return array
     */
    private T[] grow(int minCapacity) {
        int oldCapacity = array.length;
        if (oldCapacity > 0 || array != DEFAULTCAPACITY_EMPTY_ARRAY) {
            int newCapacity = (int) (array.length * 1.5);
            return array = copy(array, newCapacity);
        } else {
            return array = (T[]) new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }


    /**
     * Copying an array
     * @param originalArray
     * @param tempArray
     * @return
     */
    private T[] copy(T[] originalArray, T[] tempArray) {
        for (int i = 0; i < originalArray.length; i++) {
            tempArray[i] = originalArray[i];
        }
        return tempArray;
    }

    /**
     * Copying an array from a specified index
     * @param originalArray
     * @param tempArray
     * @return
     * @throws ArrayIndexOutOfBoundsException if index is out of bounds
     */
    private T[] copy(T[] originalArray, T[] tempArray, int index) {
        isArrayIndexOutOfBoundsException(index);
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = originalArray[i + index];
        }
        return tempArray;
    }

    /**
     * Copies the specified array, truncating or padding with nulls (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical values. F
     * or any indices that are valid in the copy but not the original, the copy will contain null. Such indices will exist
     * if and only if the specified length is greater than that of the original array. The resulting array is of the class newType.
     * Params:
     * original – the array to be copied newLength – the length of the copy to be returned newType – the class of the copy to be returned
     * Returns:
     * a copy of the original array, truncated or padded with nulls to obtain the specified length
     * Throws:
     * NegativeArraySizeException – if newLength is negative
     * NullPointerException – if original is null
     * ArrayStoreException – if an element copied from original is not of a runtime type that can be stored in an array of class newType
     */
    private static <T,U> T[] copy(U[] originalArray, int newLength, Class<? extends T[]> newType) {

        T[] copy = ((Object)newType == (Object)Object[].class)
                ? (T[]) new Object[newLength]
                : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(originalArray, 0, copy, 0,
                Math.min(originalArray.length, newLength));
        return copy;
    }

    /**
     * Copies the specified array, truncating or padding with nulls (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical values.
     * For any indices that are valid in the copy but not the original, the copy will contain null.
     * Such indices will exist if and only if the specified length is greater than that of the original array.
     * The resulting array is of exactly the same class as the original array.
     * Params:
     * original – the array to be copied newLength – the length of the copy to be returned
     * Returns:
     * a copy of the original array, truncated or padded with nulls to obtain the specified length
     * Throws:
     * NegativeArraySizeException – if newLength is negative
     * NullPointerException – if original is null
     */
    private static <T> T[] copy(T[] original, int newLength) {
        return (T[]) copy(original, newLength, original.getClass());
    }

    /**
     * Copies to an array from the specified index
     * @param originalArray
     * @param index
     * @param tempArray
     * @return
     */
    private T[] copyOfRange(T[] originalArray, int index, T[] tempArray) {
        isArrayIndexOutOfBoundsException(index);
        if (array.length < originalArray.length + tempArray.length) {
            grow(originalArray.length + tempArray.length);
        }
        for (int i = 0; i < originalArray.length; i++) {
            tempArray[i + index] = originalArray[i];
        }
        return tempArray;
    }

    /**
     * Used to glue an array when an element is removed
     * @param originalArray
     * @param index
     * @param tempArray
     * @return
     */
    private T[] copyOfRemove(T[] originalArray, int index, T[] tempArray) {
        isArrayIndexOutOfBoundsException(index);
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = originalArray[i + index];
        }
        return tempArray;
    }


    /**
     * * Trims null elements from an array
     */
    protected void trim() {
        trimToSize();
    }
    /**
     * Trims null elements from an array
     */
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

    /**
     * @throws IllegalArgumentException
     * @param items
     */
    private void isIllegalArgumentNull (T[] items) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                throw new IllegalArgumentException("Element can't be equal null. Index element null=" + i);
            }
        }
    }

    /**
     * @throws ArrayIndexOutOfBoundsException
     * @param index
     */
    private void isArrayIndexOutOfBoundsException(int index) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index can't be less than zero, index=" + index);
        } else if (index > array.length) {
            throw new ArrayIndexOutOfBoundsException("Index can't be greater than array length, index=" + index);
        }
    }

}