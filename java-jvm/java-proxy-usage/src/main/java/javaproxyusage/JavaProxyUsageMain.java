package javaproxyusage;

import net.sf.cglib.proxy.Enhancer;
import sun.misc.ProxyGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;

public class JavaProxyUsageMain {
    public static void main(String[] args) {
        //test01();

        //test01Debug();

        //test02();

        test03();
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

    public static void test03(){

        BookstoreNotImplement target = new BookstoreNotImplement();
        BookstoreCGLibProxy proxy = new BookstoreCGLibProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(proxy);

        BookstoreNotImplement obj = (BookstoreNotImplement)enhancer.create();

        obj.addBook();
        obj.updateBook();
    }
}
