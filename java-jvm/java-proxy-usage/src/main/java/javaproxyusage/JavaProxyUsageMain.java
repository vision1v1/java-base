package javaproxyusage;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sun.misc.ProxyGenerator;

import java.lang.reflect.*;
import java.util.Arrays;

public class JavaProxyUsageMain {
    public static void main(String[] args) {

        //testJDKDynamicSimpleUsage();

        testCGLibDynamicProxySimpleUsage();

        //test01();

        //test01Debug();

        //test02();

        //test03();
    }

    interface ComputerInterface {
        int add(int a, int b);

        int sub(int a, int b);
    }

    static class Target implements ComputerInterface {

        @Override
        public int add(int a, int b) {
            return a + b;
        }

        @Override
        public int sub(int a, int b) {
            return a - b;
        }
    }

    static class TargetProxyCallback implements InvocationHandler {

        private Target target;

        public TargetProxyCallback(Target target){
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //Arrays.stream(args).forEach(System.out::println);

            //proxy 就是代理对象，可以通过这个对象调用代理的其它方法，然后其它方法也会调用这个回调。
            //这样目标类的所有方法都可以被代理了。

            System.out.println(proxy.getClass().getName());

            //jdk 动态代理类 发生StackOverflowError 原因就是
            //proxy调用方法-> 调到invoke 方法，然后在invoke中->调用proxy中的方法 -> 然后在invoke中 -> ....
            proxy.hashCode();//这句会产生调用栈溢出错误。

            Object result = method.invoke(target,args);

            return result;
        }
    }

    //测试jdk动态的代理的简单使用
    public static void testJDKDynamicSimpleUsage() {

        Target target = new Target();
        TargetProxyCallback callback = new TargetProxyCallback(target);
        ComputerInterface computerInterface =
                (ComputerInterface) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), callback);
        System.out.println(computerInterface.add(1, 2));
        System.out.println(computerInterface.sub(1, 2));


    }



    public static class TargetForCGLib {


        public int add(int a, int b) {
            return a + b;
        }

        public int sub(int a, int b) {
            return a - b;
        }
    }

    static class TargetForCGLibCallback implements MethodInterceptor{

        private TargetForCGLib target;

        public TargetForCGLibCallback(TargetForCGLib target){
            this.target = target;
        }

        public TargetForCGLibCallback(){
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println(obj.getClass().getName());
            System.out.println(method.getName());
            System.out.println(proxy.getSignature());
            Arrays.stream(args).forEach(arg-> System.out.print(arg + " "));



            //Object result = method.invoke(target,args);

            //CGLib是通过集成目标类完成代理的，对目标类的方法进行重写增强。
            Object result =  proxy.invokeSuper(obj,args);

            return result;
        }
    }



    public static void testCGLibDynamicProxySimpleUsage(){

        TargetForCGLib target = new TargetForCGLib();

        TargetForCGLibCallback callback = new TargetForCGLibCallback(target);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(callback);
        TargetForCGLib proxy =  (TargetForCGLib) enhancer.create();
        System.out.println("---------------"+proxy.getClass().getName());
        System.out.println("---------------"+proxy.getClass().getSuperclass().getName());

        System.out.println(proxy.add(1, 2));
        System.out.println(proxy.sub(1, 2));

    }

    //
    public static void test01() {
        BookstoreProxy proxy = new BookstoreProxy();
        Bookstore bookstore = (Bookstore) proxy.bind(new BookstoreImpl());
        bookstore.addBook();
        bookstore.updateBook();
    }

    static class ForCallDefineClassLoader extends ClassLoader {

        public Class myDefineClass(String name, byte[] b, int off, int len) {
            Class clazz = defineClass(name, b, off, len);
            return clazz;
        }
    }

    //实现
    public static void test01Debug() {

        BookstoreImpl target = new BookstoreImpl();

        BookstoreProxy2 proxy = new BookstoreProxy2(target);

        String proxyName = "com.sun.proxy.$Proxy0";

        Class[] interfaces = target.getClass().getInterfaces();

        int accessFlags = Modifier.PUBLIC | Modifier.FINAL;

        //保存生成的 .class 文件
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces, accessFlags);

        ForCallDefineClassLoader loader = new ForCallDefineClassLoader();

        Class clazz = loader.myDefineClass(null, proxyClassFile, 0, proxyClassFile.length);

        try {
            Constructor constructor = clazz.getConstructor(InvocationHandler.class);

            Bookstore bookstore = (Bookstore) constructor.newInstance(proxy);

            bookstore.addBook();

            bookstore.updateBook();

            System.out.println(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void test02() {
        //cglib
        BookstoreCGLibProxy proxy = new BookstoreCGLibProxy();
        BookstoreNotImplement bookstore = (BookstoreNotImplement) proxy.getInstance(new BookstoreNotImplement());
        bookstore.addBook();
        bookstore.updateBook();

    }

    public static void test03() {

        BookstoreNotImplement target = new BookstoreNotImplement();
        BookstoreCGLibProxy proxy = new BookstoreCGLibProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(proxy);

        BookstoreNotImplement obj = (BookstoreNotImplement) enhancer.create();

        obj.addBook();
        obj.updateBook();
    }
}
