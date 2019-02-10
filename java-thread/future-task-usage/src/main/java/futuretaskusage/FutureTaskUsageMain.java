package futuretaskusage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskUsageMain {
    public static void main(String[] args){
        test01();
    }

    public static void test01(){
        MultiplyingTask task1 = new MultiplyingTask(10,20,20000);
        MultiplyingTask task2 = new MultiplyingTask(20,30,40000);

        FutureTask<Integer> futureTask1 = new FutureTask<>(task1);
        FutureTask<Integer> futureTask2 = new FutureTask<>(task2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while(true){
            try{
                if(!futureTask1.isDone())
                {
                    System.out.println("Waitng for futureTask1 for completion");
                    System.out.println("FutureTask1 output="+futureTask1.get());
                }
                if(!futureTask2.isDone())
                {
                    System.out.println("Waitng for futureTask2 for completion");
                    System.out.println("FutureTask2 output="+futureTask2.get());
                }
                if(futureTask1.isDone() && futureTask2.isDone())
                {
                    System.out.println("Completed both the FutureTasks, shutting down the executors");
                    executor.shutdown();
                    return;
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            catch (ExecutionException ee){
                ee.printStackTrace();
            }
        }


    }
}
