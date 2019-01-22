package javastring;

import java.util.Arrays;

public class JavaStringMain {

    public static void main(String[] args){
        //test01();

        //test02();

        test03();


    }

    //测试字符串常量池与堆上创建
    private static void test01(){
        String s1 = "ab";
        String s2 = "a" + "b";
        String s3 = "a";
        String s4 = "b";
        String s5 = s3 + s4;

        //s1 与 s2 指向常量池一个空间，所以 s1与s2 引用一样
        System.out.println(s1==s2);

        //s5 指向堆上 相当于 s5 = new String("ab")
        System.out.println(s5==s2);

        //值比较当然是相等
        System.out.println("s1.equals(s2) : " + s1.equals(s2));
        System.out.println("s5.equals(s2) : " + s5.equals(s2));
    }

    //
    private static void test02(){
        //'a' 97
        //'b' 98
        String s = "66abcaaddefa";

        s.chars().filter(e->e==97).forEach(System.out::println);

        System.out.println(s.chars().min().getAsInt());

        System.out.println(s.chars().filter(e->e==97).summaryStatistics().getCount());

        System.out.println(Arrays.toString(s.chars().map(e->e+1).toArray()));

    }

    //StringBuffer 是线程安全的，StringBuilder 非线程安全的。其它功能一样
    private static void test03(){
        //StringBuffer 是线程安全的
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("abc");
        stringBuffer.append("def");
        System.out.println(stringBuffer.toString());

        //StringBuilder 非线程安全的
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("abc");
        stringBuilder.append("def");
        System.out.println(stringBuilder.toString());
    }

    //to do
    private static void test0X(){
//        java.lang.StringBuffer
//
//        java.lang.StringBuilder
//
//        java.nio.charset.Charset
//
//                StringCoding
//
//        StreamSupport.intStream
//
//                Character
//
//        Unicode code units
//
//        Class String is special cased within the Serialization Stream Protocol
    }
}

