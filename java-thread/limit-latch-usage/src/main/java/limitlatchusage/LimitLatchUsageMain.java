package limitlatchusage;

import java.io.IOException;
import java.util.Collection;

public class LimitLatchUsageMain {

    static LimitLatch latch = new LimitLatch(0);

    public static void main(String[] args) throws IOException {

        Thread monitor = new Thread(new Monitor());
        monitor.setName("monitor");
        monitor.setDaemon(true);
        monitor.start();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Worker());
            t.setDaemon(true);
            t.setName("t" + i);
            t.start();
        }

        int a = System.in.read();
        System.out.println(a + " End");
    }

    static class Worker implements Runnable {

        @Override
        public void run() {
            try {
                latch.countUpOrAwait();
                System.out.println(Thread.currentThread().getName() + " is working");
                Thread.sleep(2000);
                latch.countDown();

            } catch (InterruptedException e) {
                latch.countDown();
            }

        }
    }

    static class Monitor implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    //if(latch.hasQueuedThreads()){
                    StringBuilder info = new StringBuilder();
                    Collection<Thread> threads = latch.getQueuedThreads();
                    info.append("等待线程数量 " + threads.size());
                    info.append("[ ");
                    threads.forEach(t -> {
                        info.append(t.getName() + ":" + t.getState() + " ");
                    });
                    info.append("]");
                    Thread.sleep(1000);
                    System.out.println(info);
                    //}
                }
                catch (Exception e){

                }
            }
        }
    }
}
