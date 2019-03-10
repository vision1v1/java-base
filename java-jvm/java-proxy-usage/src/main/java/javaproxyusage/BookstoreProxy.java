package javaproxyusage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BookstoreProxy implements InvocationHandler {

    private Object target;

    public Object bind(Object target){
        this.target = target;
        //这里是核心
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
        return proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("pre invoke");
        result = method.invoke(target,args);
        System.out.println("post invoke");
        return result;
    }
}
