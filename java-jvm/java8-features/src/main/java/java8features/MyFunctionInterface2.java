package java8features;

//不写这个标记，也支持函数引用
//@FunctionalInterface
public interface MyFunctionInterface2<P,OT> {
    OT newObj(P p);
}
