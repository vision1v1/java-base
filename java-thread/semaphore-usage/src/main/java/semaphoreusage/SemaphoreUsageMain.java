package semaphoreusage;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class SemaphoreUsageMain {
    public static void main(String[] args) {
        test01();
        end();
    }

    public static void test01() {
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Work(semaphore),"t" + i);
            t.start();
        }

    }

    static class Work implements Runnable {

        private Semaphore semaphore;

        Work(Semaphore semaphore){
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                //获得一个信号量
                semaphore.acquire();
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " Done");
                //释放一个信号量
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
