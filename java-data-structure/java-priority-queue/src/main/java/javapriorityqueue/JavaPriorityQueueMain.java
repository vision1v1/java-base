package javapriorityqueue;

import java.util.PriorityQueue;

public class JavaPriorityQueueMain {

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
        long begin = System.nanoTime();
        PriorityQueue<Element> priorityQueue = new PriorityQueue<>();

        for (int i = 0; i < 100; i++) {
            priorityQueue.add(new Element(i));
        }
        long end = System.nanoTime();
        System.out.println(end-begin);


        priorityQueue.forEach(item->{
            System.out.print(item + " ");
        });
        System.out.println();

        //查看队首元素，该元素不出队
        Element e1 = priorityQueue.peek();
        System.out.println(e1);

        //获取队首元素，该元素出队
        Element e2 = priorityQueue.poll();
        System.out.println(e2);

        //元素入队
        priorityQueue.offer(new Element(9999));

    }
}
