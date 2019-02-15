package classloader;

import sun.misc.Launcher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassLoaderMain {
    public static void main(String[] args) {
        //test01();
        //test02();
        test03();
        end();
    }

    //测试几个关键的环境属性
    public static void test01() {
        //Launcher

        //BootstrapClassLoader
        System.out.println("BootstrapClassLoader 加载环境属性 sun.boot.class.path");
        Arrays.asList(System.getProperty("sun.boot.class.path").split(";")).forEach(item -> System.out.println(item));

        //ExtClassLoader
        System.out.println("ExtClassLoader 加载环境属性 java.ext.dirs");
        Arrays.asList(System.getProperty("java.ext.dirs").split(";")).forEach(item -> System.out.println(item));

        //AppClassLoader
        System.out.println("AppClassLoader 加载环境属性 java.class.path");
        Arrays.asList(System.getProperty("java.class.path").split(";")).forEach(item -> System.out.println(item));

    }

    //获取类加载器父加载器，为后续分析双亲委托做准备。
    public static void test02() {

        ClassLoader cl = ClassLoaderMain.class.getClassLoader();

        //sun.misc.Launcher.AppClassLoader
        System.out.println(cl);

        //sun.misc.Launcher.ExtClassLoader
        System.out.println(cl.getParent());

        //null 如果为null 会使用jvm内置的BootstrapClassLoader
        System.out.println(cl.getParent().getParent());


        ClassLoader cl1 = Thread.currentThread().getContextClassLoader();
        System.out.println(cl1);
        System.out.println(cl.equals(cl1));
    }

    //调试loadClass源代码
    public static void test03() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        System.out.println(cl);
        Launcher launcher = Launcher.getLauncher();

        try {
            //这里调试源代码的入口
            Class c = cl.loadClass("classloader.Student");
            if (c != null) {
                try{
                    Object obj = c.newInstance();
                    Method method = c.getDeclaredMethod("say",null);
                    //相当调用 obj.say()
                    method.invoke(obj,null);
                }
                catch (IllegalAccessException
                        | InstantiationException
                        | NoSuchMethodException
                        | InvocationTargetException e){
                    e.printStackTrace();
                }


            }

        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
    }

    public static void end() {
        try {
            int key = System.in.read();
            System.out.println("End " + key);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
