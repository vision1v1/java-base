package javahashtable;

import java.util.Hashtable;


//Hashtable is obsolete. Best is to use ConcurrentHashMap class which provide much higher degree of concurrency.
public class JavaHashTableMain {
    public static void main(String[] args) {


//        differences between a hashmap and hashtable in Java.
//
//        HashMap is non synchronized. Hashtable is synchronized.
//        HashMap allows one null key and multiple null values. Hashtable doesn’t allow any null key or value.
//        HashMap is fast. Hashtable is slow due to added synchronization.
//        HashMap is traversed by Iterator. Hashtable is traversed by Enumerator and Iterator.
//        Iterator in HashMap is fail-fast. Enumerator in Hashtable is not fail-fast.
//        HashMap inherits AbstractMap class. Hashtable inherits Dictionary class.


        testHashTable();
    }

    public static void testHashTable() {
        // 1. 存储数组
        // 2. hash 冲突，链表。
        Hashtable<String, Integer> hashTable = new Hashtable<>();
        hashTable.put("th000", 1);
        hashTable.put("infi", 2);
        hashTable.put("fly", 3);
        hashTable.put("fly", 4);

        System.out.println(hashTable);
    }
}
