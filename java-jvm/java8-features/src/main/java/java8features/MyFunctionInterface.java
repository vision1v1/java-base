package java8features;

@FunctionalInterface
public interface MyFunctionInterface<F,T> {
    //函数式接口，只能有唯一抽象函数，对应Lambda
    T convert(F from);

    default String connect(F from,T to){
        return from + " : " + to;
    }
}
