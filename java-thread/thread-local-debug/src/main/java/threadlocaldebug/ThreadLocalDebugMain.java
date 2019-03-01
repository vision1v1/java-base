package threadlocaldebug;

public class ThreadLocalDebugMain {

    private static ThreadLocal<String> local2 = new ThreadLocal<>();

    public static void main(String[] args){
        test01();
    }

    // 每个Thread对象包含了一个ThreadLocalMap类型的对象。是一个key,value存储格式
    // 用于存储ThreadLocal对象，这里是local1。local1的hashcode作为key。这里的value就是456
    @SuppressWarnings("Duplicates")
    public static void test01(){
        ThreadLocal<String> local1 = new ThreadLocal<>();

        Thread t1 = new Thread(()->{
            local1.set("123");
            local1.set("456");
            work(local1);
        });

        t1.start();

        Thread t2 = new Thread(()->{
            work(local1);
        });

        t2.start();
    }

    @SuppressWarnings("Duplicates")
    public static void test02(){

        Thread t1 = new Thread(()->{
            local2.set("123");
            local2.set("456");
            work(local2);
        });

        t1.start();

        Thread t2 = new Thread(()->{
            work(local2);
        });

        t2.start();
    }

    public static void work(ThreadLocal<String> local){
        String result = local.get();
        System.out.println("---------" + result);
    }
}
