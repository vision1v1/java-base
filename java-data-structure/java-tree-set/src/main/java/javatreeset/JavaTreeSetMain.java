package javatreeset;

import java.util.TreeSet;

public class JavaTreeSetMain {
    public static void main(String[] args) {
        test01();
    }

    static class Element implements Comparable {
        private int e = 0;

        Element(int data) {
            this.e = data;
        }

        @Override
        public String toString() {
            return Integer.toString(e);
        }

        @Override
        public int compareTo(Object o) {
            return this.e - ((Element)o).e;
        }
    }

    public static void test01() {
        long start = System.nanoTime();

        TreeSet<Element> treeSet = new TreeSet<>();
        for (int i = 0; i < 1000; i++) {
            treeSet.add(new Element(i));
        }

        long end = System.nanoTime();
        System.out.println("插入耗时 " + (end - start) + " 纳秒");

        treeSet.forEach(item->{
            System.out.println(item);
        });
    }
}
