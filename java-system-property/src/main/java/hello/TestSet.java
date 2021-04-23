package hello;

public class TestSet {
    public static void main(String[] args) {
        System.setProperty("diy_key_01","diy_value_01");
        System.out.println(System.getProperty("diy_key_01"));

        // 程序是无法设置环境的
        System.getenv().put("my_env_01","my_env_value_01");
        System.out.println(System.getenv("my_env_01"));
    }
}
