package java8features;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Java8FeaturesMain {

    public static void main(String[] args) {
        test01();

        test02();

        test03();

        test04();
    }

    //接口默认方法，扩展方法概念。
    //扩展方法目的可能就是在接口定义不准确时，给出一种全局弥补定义的机会吧。
    public static void test01() {
        MyInterface add = new MyInterface() {
            @Override
            public double compute(int a, int b) {
                return add(a, b);
            }

            //可以覆盖掉扩展的方法
            @Override
            public double add(int a, int b) {
                return a * b;
            }
        };

        MyInterface sub = new MyInterface() {
            @Override
            public double compute(int a, int b) {
                return sub(a, b);
            }
        };

        System.out.println(add.compute(1, 2));
        System.out.println(sub.compute(1, 2));
    }

    //Lambda 表达式
    public static void test02() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

        //老做法
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        //改进
        Collections.sort(names, (String s1, String s2) -> {
            return s1.compareTo(s2);
        });

        //在改进
        Collections.sort(names, (String s1, String s2) -> s1.compareTo(s2));

        //在改进
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
    }

    //函数式接口
    public static void test03() {

        MyFunctionInterface<String, Integer> fromTo = (f) -> Integer.valueOf(f);

        Integer result = fromTo.convert("123");

        System.out.println(result);

    }

    //方法引用
    //构造函数引用
    public static void test04(){
        //可以通过静态方法引用来表示
        MyFunctionInterface<String, Integer> fromTo = Integer::valueOf;

        System.out.println(fromTo.convert("456"));

        Integer o = new Integer("100");

        
    }
}
