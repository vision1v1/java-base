package javalinkedlist;

import java.util.LinkedList;

public class JavaLinkedList {
    public static void main(String[] args){
        test01();
    }

    static class Element {
        private int e = 0;

        Element(int data) {
            this.e = data;
        }

        @Override
        public String toString() {
            return Integer.toString(e);
        }
    }

    public static void test01(){
        long begin = System.nanoTime();
        LinkedList<Element> linkedList = new LinkedList<>();
        for(int i=0;i<1000;i++){
            linkedList.add(new Element(i));
        }
        long end = System.nanoTime();
        System.out.println("插入用时 " + (end-begin) + " 纳秒");
        linkedList.forEach(item->{
            System.out.println(item);
        });
    }
}
