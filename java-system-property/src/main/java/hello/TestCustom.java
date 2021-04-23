package hello;

public class TestCustom {
    public static void main(String[] args) {
        testReadPropertyFromCmd();
        testReadEnvFromCmd();
    }

    public static void testReadPropertyFromCmd() {
        System.out.println(System.getProperty("custom_key"));
    }

    public static void testReadEnvFromCmd() {
        System.out.println(System.getenv("my_env"));
    }


}
