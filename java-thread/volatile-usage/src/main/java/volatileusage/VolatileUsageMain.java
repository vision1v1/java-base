package volatileusage;

import java.util.HashMap;

public class VolatileUsageMain {

    private static volatile int race = 0;

    private static void increase() {
        //synchronized (VolatileUsageMain.class){
        //race++ 不是原子的
        //race 保证变量的可见性，线程之间的访问不存在同步问题，但是race++不是原子的。
        race++;
        //}

    }

    private static int THREAD_COUNT = 20;

    public static void main(String[] args) {
        //test01();

        //test02();

        test03();
        System.out.println("Ended");
    }

    //volatile 可见性
    public static void test01() {
        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }


        while (Thread.activeCount() > 2) {
            Thread.yield();

        }

        System.out.println(race);
    }

    //private static volatile boolean configFinish = false;

    private static boolean configFinish = false;

    private static HashMap<String, String> configs = new HashMap<>();

    //volatile 防止指令重排，导致变量的位置变化。
    public static void test02() {
        Thread config = new Thread(new Runnable() {
            @Override
            public void run() {
                ConfigThread();
            }
        });

        Thread work = new Thread(new Runnable() {
            @Override
            public void run() {
                OtherThread();
            }
        });

        work.start();
        config.start();

        while (Thread.activeCount() > 2) {
            Thread.yield();

        }
    }

    public static void ConfigThread() {
        try {
            configs.put("path", "c://config");
            Thread.sleep(5000);
            configs.put("name", "ini.txt");
            configFinish = true;//这里 configFinish 如果不是volatile,有可能会发生指令重排在上面执行，导致configs中可能没有值
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void OtherThread() {
        while (!configFinish) {
            Thread.yield();
        }
        System.out.println("config " + configs);
    }


    private static volatile SetAndGet setAndGet = new SetAndGet();

    //volatile 写操作先行与读操作的理解
    public static void test03() {

        Thread readThread = new Thread(() -> {
            readThread();
        });

        Thread writeThread = new Thread(() -> {
            writeThread();
        });

        readThread.start();
        writeThread.start();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }


    }

    private static void readThread() {
        //for (int i = 0; i < 1000; i++) {
            setAndGet.getA();
        //}
    }


    private static void writeThread() {
        for (int i = 0; i < 1000; i++) {
            setAndGet.setA(i);
        }
    }


}
