package reentrantreadwritelockusage;

import java.io.IOException;
import java.util.Random;

public class ReentrantReadWriteLockUsageMain {

    static final int READER_SIZE = 10;
    static final int WRITER_SIZE = 2;


    public static void main(String[] args) {

        Integer[] initNumbers = {33, 28, 86, 99};

        ReadWriteList<Integer> list = new ReadWriteList<>(initNumbers);
        for (int i = 0; i < READER_SIZE; i++) {
            Thread thread = new Reader(list);
            thread.setName("r"+i);
            thread.start();
        }

        for (int i = 0; i < WRITER_SIZE; i++) {
            Thread thread = new Writer(list);
            thread.setName("w"+i);
            thread.start();
        }

        end();
    }

    static class Writer extends Thread {
        private ReadWriteList<Integer> list;

        public Writer(ReadWriteList<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            Random random = new Random();
            int number = random.nextInt(100);
            list.add(number);
            try {
                Thread.sleep(100);
                System.out.println(getName() + " -> put: " + number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Reader extends Thread {
        private ReadWriteList<Integer> list;

        Reader(ReadWriteList<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            Random random = new Random();
            int index = random.nextInt(list.size());
            Integer number = list.get(index);
            System.out.println(getName() + "-> get: " + number);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static void end() {
        try {
            int data = System.in.read();
            System.out.println("End " + data);
        } catch (IOException e) {

        }


    }
}
