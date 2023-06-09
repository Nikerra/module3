package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        /**
         * Добавление в наш массив самописного обьекта
         */

        TestClassforCollection tcc = new TestClassforCollection(1);
        CustomArrayImpl<TestClassforCollection> arrayTCC = new CustomArrayImpl<>();
        arrayTCC.add(tcc);
        System.out.println(arrayTCC.toString());
        arrayTCC.trim();
        System.out.println(arrayTCC.toString());


        /**
         * Ручная прогонка методов нашего Динамического массива
         */

        Integer[] testArray = new Integer[5];
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = i;
        }
//        System.out.println(Arrays.toString(testArray));
//        testArray[3] = null;
//        System.out.println(Arrays.toString(testArray));
        CustomArrayImpl<Integer> array = new CustomArrayImpl<>();
        array.addAll(testArray);
        System.out.println("array is empty=" + array.isEmpty());
        System.out.println(array.toString());
        System.out.println("add elem=" + array.add(1));
        System.out.println("size array=" + array.size());
        System.out.println(array.toString());
        System.out.println("add elem=" + array.add(2));
        System.out.println("size array=" + array.size());
        System.out.println(array.toString());
        System.out.println("add elem=" + array.add(3));
        System.out.println("size array=" + array.size());
        System.out.println(array.toString());
        System.out.println("add elem=" + array.add(4));
        System.out.println("size array=" + array.size());
        System.out.println(array.toString());
        System.out.println("add elem=" + array.add(5));
        System.out.println("size array=" + array.size());
        System.out.println(array.toString());
        System.out.println("add elem=" + array.add(6));
        System.out.println("size array=" + array.size());
        System.out.println(array.toString());
        System.out.println("array capacity=" + array.getCapacity());
        System.out.println(array.toString());
        System.out.println("array is empty=" + array.isEmpty());
        System.out.println(array.toString());
        System.out.println("size array before delete=" + array.size());
        System.out.println("delete elem=" + array.remove(new Integer(2)));
        System.out.println(array.toString());
        System.out.println("size array after delete=" + array.size());
        System.out.println(array.toString());
        System.out.println("repeat delete elem=" + array.remove(new Integer(2)));
        System.out.println("size array after delete=" + array.size());;
        array.remove(1);
        System.out.println("size array after delete=" + array.size());
        System.out.println(array.toString());
        System.out.println("get elem for index=" + array.get(1));
        System.out.println(array.toString());
        System.out.println("set elem for index old value=" + array.set(1,99));
        System.out.println("size array=" + array.size());
        System.out.println(array.toString());
        System.out.println("indexOf=" + array.indexOf(99));
        System.out.println("contains=" + array.contains(1));
        array.reverse();
        System.out.println("array reverse=" + array.toString());
        System.out.println("indexOf=" + array.indexOf(98));
        System.out.println("contains=" + array.contains(1));
        System.out.println("add elem=" + array.add(7));
        System.out.println("size array=" + array.size());
        System.out.println(array.toString());
    }
}