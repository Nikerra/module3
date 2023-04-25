package org.example;

import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        CustomArrayImpl<Integer> array = new CustomArrayImpl<>();
        System.out.println("array is empty=" + array.isEmpty());
        System.out.println("add elem=" + array.add(1));
        System.out.println("size array=" + array.size());
        System.out.println("add elem=" + array.add(2));
        System.out.println("size array=" + array.size());
        System.out.println("add elem=" + array.add(3));
        System.out.println("size array=" + array.size());
        System.out.println("add elem=" + array.add(4));
        System.out.println("size array=" + array.size());
        System.out.println("add elem=" + array.add(5));
        System.out.println("size array=" + array.size());
        System.out.println("add elem=" + array.add(6));
        System.out.println("size array=" + array.size());
        System.out.println("array capacity=" + array.getCapacity());
        System.out.println("array is empty=" + array.isEmpty());
        System.out.println(array.toString());
        System.out.println("size array before delete=" + array.size());
        System.out.println("delete elem=" + array.remove(new Integer(2)));
        System.out.println("size array after delete=" + array.size());
        System.out.println("size array before delete=" + array.size());
        System.out.println("delete elem=" + array.remove(new Integer(2)));
        System.out.println("size array after delete=" + array.size());
        System.out.println("size array before delete=" + array.size());
        array.remove(1);
        System.out.println("size array after delete=" + array.size());
        System.out.println("get elem for index=" + array.get(0));
        System.out.println("set elem for index old value=" + array.set(1,99));
        System.out.println(array.toString());

    }
}