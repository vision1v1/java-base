package java8features;

//1.8 接口可以提供非抽象的方法
public interface MyInterface {

    double compute(int a,int b);


    default double add(int a,int b){
        return a + b;
    }

    default double sub(int a,int b){
        return a - b;
    }
}
