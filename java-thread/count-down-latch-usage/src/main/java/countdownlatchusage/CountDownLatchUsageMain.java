package countdownlatchusage;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchUsageMain {

    static class Worker extends Thread {

        private CountDownLatch countDownLatch;
        private int delay;

        Worker(int delay, CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
            this.delay = delay;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delay);
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + " Done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try{
            CountDownLatch countDownLatch = new CountDownLatch(4);

            Worker w1 = new Worker(1000, countDownLatch);
            w1.setName("w1");
            Worker w2 = new Worker(2000, countDownLatch);
            w2.setName("w2");
            Worker w3 = new Worker(3000, countDownLatch);
            w3.setName("w3");
            Worker w4 = new Worker(4000, countDownLatch);
            w4.setName("w4");

            w1.start();
            w2.start();
            w3.start();
            w4.start();

            countDownLatch.await();

            System.out.println(Thread.currentThread().getName() + " finished");
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
