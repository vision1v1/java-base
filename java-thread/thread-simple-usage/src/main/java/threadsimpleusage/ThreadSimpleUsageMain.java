package threadsimpleusage;

public class ThreadSimpleUsageMain {
    public static void main(String[] args) throws Exception{
        test01();
        test02();
        int input = System.in.read();
        System.out.println("End" + input);
    }

    public static void test01(){
        Thread t = new MyThread();
        t.setName("hello-thread");
        t.setDaemon(true);
        t.start();
    }

    public static void test02(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i=0;i<10;i++){
                    System.out.println(Thread.currentThread().getName()+i);
                }
            }
        });
        t.setDaemon(false);
        t.setName("hello-runnable");
        t.start();
    }
}
