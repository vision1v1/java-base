package javaadvancedebug;

public class JavaAdvanceDebugMain {

    public static void main(String[] args){
        test01();
    }

    @SuppressWarnings("Duplicates")
    //创建两个线程，每个1秒输出一次计算信息，用工具分析内存。
    public static void test01(){
        Thread t1 = new Thread(()->{
            while(true){
                try{
                    Thread.sleep(1000);
                    int sum = 0;
                    for(int i=0;i<1000;i++){
                        sum += i;
                    }
                    System.out.println("计算结果 : " + sum);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t1.setName("t1-myThread");
        t1.start();

        Thread t2 = new Thread(()->{
            while(true){
                try{
                    Thread.sleep(1000);
                    int sum = 0;
                    for(int i=0;i<1000;i++){
                        sum += i;
                    }
                    System.out.println("计算结果 : " + sum);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t2.setName("t2-myThread");
        t2.start();
    }

}
