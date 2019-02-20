package reentrantlockcondition;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.io.BufferedReader;
import java.util.concurrent.ThreadPoolExecutor;

public class ReentrantLockConditionMain {
    public static void main(String[] args) {
        test01();
        end();
    }

    public static void test01() {
        BoundedBuffer buffer = new BoundedBuffer(10);

        Thread monitor = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println(buffer.toString());
                } catch (Exception e) {

                }
            }
        });
        monitor.start();

        for (int i = 0; i < 1; i++) {
            WorkTake take = new WorkTake(buffer, "take " + i);
            take.start();
        }

        for (int i = 0; i < 1; i++) {
            WorkWrite write = new WorkWrite(buffer, "write " + i);
            write.start();
        }

    }


    static class WorkTake extends Thread {

        private BoundedBuffer buffer;

        public WorkTake(BoundedBuffer buffer, String threadName) {
            this.buffer = buffer;
            setName(threadName);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object obj = buffer.get();
                    Thread.sleep(2000);
                    System.out.println(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class WorkWrite extends Thread {

        private BoundedBuffer buffer;

        public WorkWrite(BoundedBuffer buffer, String threadName) {
            this.buffer = buffer;
            setName(threadName);
        }

        @Override
        public void run() {
            int i = 0;
            while (true) {
                try {
                    String content = getName() + " " + i;
                    this.buffer.put(content);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public static void end() {
        try {
            int key = System.in.read();
            System.out.println("End " + key);
        } catch (Exception e) {

        }
    }
}
