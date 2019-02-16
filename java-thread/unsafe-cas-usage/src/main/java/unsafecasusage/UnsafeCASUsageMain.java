package unsafecasusage;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeCASUsageMain {
    public static void main(String[] args){
        test01();

        end();
    }

    static class Student{
        public int Id = 10;
    }

    public static void test01(){

        try{
            //这样不能获取unsafe实例，会报SecurityException
            //（$JAVA_HOME/lib目录下jar包包含的类，如java.util.concurrent.atomic.AtomicInteger）
            //才能通过Unsafe.getUnsafe静态方法获取
            //Unsafe unsafe = Unsafe.getUnsafe();

            //我们只能通过反射获取unsafe实例
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);


            Field idFeild = Student.class.getDeclaredField("Id");
            Student s = new Student();
            System.out.println("原始Id值 : " + idFeild.get(s));


            //获取id字段在class文件里的偏移量
            final long idOffset = unsafe.objectFieldOffset(idFeild);


            System.out.println("第一次swap(10,20)函数返回值:" + unsafe.compareAndSwapInt(s,idOffset,10,20));
            System.out.println("第一次swap(10,20)后value值:" + idFeild.get(s));


            System.out.println("第二次swap(10,20)函数返回值:" + unsafe.compareAndSwapInt(s,idOffset,10,20));
            System.out.println("第二次swap(10,20)后value值:" + idFeild.get(s));







        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void end(){
        try{
            int key = System.in.read();
            System.out.println("end " + key);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
