package threadsimpleusage;

public class ThreadSimpleUsageMain {
    public static void main(String[] args) {
        //test01();
        //test02();
        test03();
        end();
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

    //测试线程 interrupt
    public static void test03(){

        Thread sleep = new Thread(()->{
            try{
                System.out.println(Thread.currentThread().getName() + " " +Thread.currentThread().getState() + " " + Thread.currentThread().isInterrupted());
                Thread.sleep(5000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        sleep.setName("sleep");
        sleep.start();

        Thread t = new Thread(new Runnable() {
            public void run() {
                try{
                    System.out.println(Thread.currentThread().getName() + " " +Thread.currentThread().getState() + " " + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();
                    //当前线程标记中断后，当前线程在sleep，就会出现 InterruptedException。
                    //Thread.sleep(5000);

                    //当前线程标记中断后，当前线程在join，就会出现 InterruptedException。
                    sleep.join();
                    System.out.println(Thread.currentThread().getName() + " " +Thread.currentThread().getState() + " " + Thread.currentThread().isInterrupted());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(false);
        t.setName("test03-thread");
        t.start();
    }

    public static void end(){
        try{
            int input = System.in.read();
            System.out.println("End" + input);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
