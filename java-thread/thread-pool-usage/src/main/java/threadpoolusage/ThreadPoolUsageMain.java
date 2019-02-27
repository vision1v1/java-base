package threadpoolusage;

import java.util.concurrent.*;

public class ThreadPoolUsageMain {
    public static void main(String[] args) {
        test01();
    }


    //参数含义
    public static void test01() {

        //空闲线程数量，如果设置allowCoreThreadTimeOut。可以回收线程到0.
        int corePoolSize = 2;

        //线程池中的最大线程数量。
        int maximumPoolSize = 4;

        //线程结束后，可以存活一段时间（keepAliveTime），时间单位为（unit），超时后会彻底结束掉这个线程。
        //如果设置allowCoreThreadTimeOut，闲置的线程同上。
        long keepAliveTime = 2;
        TimeUnit unit = TimeUnit.SECONDS;

        //用来存储将要执行的线程任务。
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();

        //线程工厂，这里使用默认的。
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        //拒绝执行策略。这里也是默认的。
        RejectedExecutionHandler defaultHandler = new ThreadPoolExecutor.AbortPolicy();

        //线程池执行器。
        ThreadPoolExecutor threadPoolExecutor
                = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, defaultHandler);

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new MyWork("my-work-" + i));
        }

        Thread t = new Thread(new Monitor(workQueue));
        t.start();
    }

    public static class Monitor implements Runnable{

        private BlockingQueue<Runnable> workQueue;

        public Monitor(BlockingQueue<Runnable> workQueue){
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            while (true){
                try{
                    Thread.sleep(1000);
                    System.out.println("BlockingQueue's size is " + this.workQueue.size());
                }
                catch (Exception e){

                }
            }
        }
    }



    public static class MyWork implements Runnable {

        private String name;

        public MyWork(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try{
                Thread.sleep(1000 * 60 * 60);//1 hour for debugging
                System.out.println(name + " has done.");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
