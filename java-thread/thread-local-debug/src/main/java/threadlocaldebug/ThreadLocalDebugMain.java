package threadlocaldebug;

public class ThreadLocalDebugMain {

    private static ThreadLocal<String> local1 = new ThreadLocal<>();

    public static void main(String[] args){
        test01();
    }

    // 每个Thread对象包含了一个ThreadLocalMap类型的对象。是一个key,value存储格式
    // 用于存储ThreadLocal对象，这里是local1。local1的hashcode作为key。这里的value就是456
    public static void test01(){
        Thread t1 = new Thread(()->{
            local1.set("123");
            local1.set("456");
            work();
        });

        t1.start();

        Thread t2 = new Thread(()->{
            work();
        });

        t2.start();
    }

    public static void work(){
        String result = local1.get();
        System.out.println("---------" + result);
    }
}
