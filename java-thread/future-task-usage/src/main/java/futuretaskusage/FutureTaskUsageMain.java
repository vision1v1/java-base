package futuretaskusage;

import java.util.concurrent.*;

public class FutureTaskUsageMain {
    public static void main(String[] args) {
        //test01();
        test02();
    }

    static class Computer implements Callable<Integer> {

        private int a;
        private int b;

        public Computer(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Integer call() throws Exception {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + " " + currentThread.getState());
            // 模拟计算慢
            //Thread.sleep(60*60*1000);// 计算一小时方便调试源码
            Thread.sleep(20*1000);
            return a + b;
        }
    }


    public static void test01() {
        MultiplyingTask task1 = new MultiplyingTask(10, 20, 20000);
        MultiplyingTask task2 = new MultiplyingTask(20, 30, 40000);

        FutureTask<Integer> futureTask1 = new FutureTask<>(task1);
        FutureTask<Integer> futureTask2 = new FutureTask<>(task2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                if (!futureTask1.isDone()) {
                    System.out.println("Waitng for futureTask1 for completion");
                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }
                if (!futureTask2.isDone()) {
                    System.out.println("Waitng for futureTask2 for completion");
                    System.out.println("FutureTask2 output=" + futureTask2.get());
                }
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Completed both the FutureTasks, shutting down the executors");
                    executor.shutdown();
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException ee) {
                ee.printStackTrace();
            }
        }


    }


    public static void test02() {
        System.out.println("main : " + Thread.currentThread().getName() + " " + Thread.currentThread().getState());
        Computer c1 = new Computer(1, 2 );

        FutureTask<Integer> task1 = new FutureTask<>(c1);

        Thread t = new Thread(task1);

        t.start();

        //task1.run();

        try{
            //调试跟踪get实现
            Integer result1 = task1.get();
            //2秒后还没有的到计算结果，就会抛出TimeoutException
            Integer result2 = task1.get(2,TimeUnit.SECONDS);

            System.out.println(result1);
            System.out.println(result2);
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
