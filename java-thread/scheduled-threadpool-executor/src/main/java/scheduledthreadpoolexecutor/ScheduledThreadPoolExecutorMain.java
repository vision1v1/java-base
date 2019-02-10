package scheduledthreadpoolexecutor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorMain {
    public static void main(String[] args){
        //test01();
        test02();
    }

    //计划延迟执行任务
    public static void test01(){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        Task task1 = new Task("Demo Task 1");
        Task task2 = new Task("Demo Task 2");

        System.out.println("The time is : " + new Date());

        executor.schedule(task1,5, TimeUnit.SECONDS);
        executor.schedule(task2,10,TimeUnit.SECONDS);

        try{
            //阻塞直到所有计划任务完成。
            //或者超时。
            //或者线程中断。
            executor.awaitTermination(1,TimeUnit.DAYS);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

        executor.shutdown();

        System.out.println("End");
    }

    //计划周期执行任务
    public static void test02(){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Task task1 = new Task("Demo Task 1");

        System.out.println("The time is : " + new Date());

        ScheduledFuture<?> result = executor.scheduleAtFixedRate(task1,2,5,TimeUnit.SECONDS);

        try{
            TimeUnit.MILLISECONDS.sleep(20000);
        }
        catch (InterruptedException ie){
            ie.printStackTrace();
        }

        executor.shutdown();

        System.out.println("End");
    }
}
