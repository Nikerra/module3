package org.example;



import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class CustomArrayImplTest {
    CustomArrayImpl<Integer> array = new CustomArrayImpl<>();


    @Test
    public void testAdd() {
        Assert.assertEquals(true, array.add(1));
    }

    @Test
    public void testAddAll1() {
        Integer[] testArrayNull = new Integer[0];
        Integer[] testArrayInt = new Integer[5];
        for (int i = 0; i < testArrayInt.length; i++) {
            testArrayInt[i] = i;
        }
        Assert.assertEquals(true, array.addAll(testArrayInt));
        Assert.assertEquals(false, array.addAll(testArrayNull));
    }

    @Test
    public void testSize() {
        array.add(1);
        Assert.assertEquals(1, array.size());

    }

    @Test
    public void testGet() {
        Integer[] testArrayInt = new Integer[5];
        for (int i = 0; i < testArrayInt.length; i++) {
            testArrayInt[i] = i;
        }
        array.addAll(testArrayInt);
        int index = 1;
        Assert.assertEquals(Optional.of(index), Optional.of(array.get(1)));
        try {
            array.get(-1);
            Assert.fail();
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println(aioobe);
        }
    }

    @Test
    public void testSet() {
        Integer[] testArrayInt = new Integer[5];
        for (int i = 0; i < testArrayInt.length; i++) {
            testArrayInt[i] = i;
        }
        array.addAll(testArrayInt);
        int oldValue = 1;
        Assert.assertEquals(Optional.of(oldValue), Optional.of(array.set(1, 99)));
        try {
            array.set(-1, 99);
            Assert.fail();
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println(aioobe);
        }
    }

    @Test
    public void testRemove() {
        Integer[] testArrayInt = new Integer[5];
        for (int i = 0; i < testArrayInt.length; i++) {
            testArrayInt[i] = i;
        }
        array.addAll(testArrayInt);
        Integer value = 1;
        Integer valueNone = 99;
        Assert.assertEquals(true, array.remove(value));
        Assert.assertEquals(false, array.remove(valueNone));
        try {
            array.remove(-1);
            Assert.fail();
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println(aioobe);
        }
    }

    @Test
    public void testIndexOf() {
        Integer[] testArrayInt = new Integer[5];
        for (int i = 0; i < testArrayInt.length; i++) {
            testArrayInt[i] = i;
        }
        array.addAll(testArrayInt);
        Assert.assertEquals(1, array.indexOf(1));
        Assert.assertEquals(-1, array.indexOf(99));
    }

    @Test
    public void testContains() {
        Integer[] testArrayInt = new Integer[5];
        for (int i = 0; i < testArrayInt.length; i++) {
            testArrayInt[i] = i;
        }
        array.addAll(testArrayInt);
        Assert.assertEquals(true, array.contains(1));
        Assert.assertEquals(false, array.contains(99));
    }
}