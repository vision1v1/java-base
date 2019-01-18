package volatileusage;

public class VolatileUsageMain {

    private static volatile int race = 0;

    private static void increase(){
        //synchronized (VolatileUsageMain.class){
            //race++ 不是原子的
            //race 保证变量的可见性，线程之间的访问不存在同步问题，但是race++不是原子的。
            race++;
        //}

    }

    private static int THREAD_COUNT = 20;

    public static void main(String[] args){
        Thread[] threads = new Thread[THREAD_COUNT];

        for(int i=0;i<THREAD_COUNT;i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10000;i++){
                        increase();
                    }
                }
            });
            threads[i].start();
        }



        while(Thread.activeCount() > 2) {
            Thread.yield();

        }

        System.out.println(race);

    }
}
