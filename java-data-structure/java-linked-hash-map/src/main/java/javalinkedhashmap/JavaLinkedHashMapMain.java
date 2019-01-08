package javalinkedhashmap;

import java.util.LinkedHashMap;

public class JavaLinkedHashMapMain {
    public static void main(String[] args){

        testInsertionOrderedLinkedHashMap();

        testAccessOrderedLinkedHashMap();
    }

    public static void testInsertionOrderedLinkedHashMap(){
        LinkedHashMap<Integer, String> pairs = new LinkedHashMap<>();

        pairs.put(1,  "A");
        pairs.put(2,  "B");
        pairs.put(3,  "C");
        pairs.put(4,  "D");

        pairs.forEach((key, value) -> {
            System.out.println("Key:"+ key + ", Value:" + value);
        });
    }

    private static void testAccessOrderedLinkedHashMap(){

        System.out.println("-----testAccessOrderedLinkedHashMap-----");
        //3rd parameter set access order
        //LRU最近最少使用的放在前面，频繁使用的后移。MRU最近最多使用后移
        //Invoking the put, putIfAbsent, get, getOrDefault, compute, computeIfAbsent, computeIfPresent, or merge methods results in an access to the corresponding entry
        LinkedHashMap<Integer, String> pairs = new LinkedHashMap<>(2, .75f, true);

        pairs.put(1,  "A");
        pairs.put(2,  "B");
        pairs.put(3,  "C");
        pairs.put(4,  "D");

//Access 3rd pair
        pairs.get(3);

//Access 1st pair
        pairs.getOrDefault(2, "oops");

        System.out.println(pairs.getOrDefault(5,"hello"));

        pairs.forEach((key, value) -> {
            System.out.println("Key:"+ key + ", Value:" + value);
        });
    }
}
