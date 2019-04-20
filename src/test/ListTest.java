package test;

import java.util.ArrayList;

public class ListTest {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("apple");
        arrayList.add("banana");
        arrayList.add("orange");
        arrayList.add("strawberry");
        arrayList.add("blueberry");
        arrayList.add("lemon");
        arrayList.subList(0, 3).clear();
        System.out.println(arrayList);
    }
}
