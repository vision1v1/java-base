package hello;

import java.util.Properties;

public class TestRead {
    public static void main(String[] args) {
        test01();
//        test02();
//        test03();
    }

    public static void test01() {
        Properties properties = System.getProperties();
        properties.list(System.out);
    }

    public static void test02() {
        String class_path = System.getProperty("java.class.path");
        System.out.println(class_path);
    }

    public static void test03() {
        System.out.println(System.getenv());
    }
}
