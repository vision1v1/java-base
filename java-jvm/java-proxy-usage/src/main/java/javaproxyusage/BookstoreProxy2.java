package javaproxyusage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BookstoreProxy2 implements InvocationHandler {

    private Object target;

    public BookstoreProxy2(Object target){
        this.target = target;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("pre invoke");
        result = method.invoke(target,args);
        System.out.println("post invoke");
        return result;
    }
}
