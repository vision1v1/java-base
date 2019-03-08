package javareflectusage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaReflectUsageMain {

    public static void main(String[] args) {
//        test01();
//
//        test02();
//
//        test03();

        test04();
    }


    //获取类的三种方式
    public static void test01() {

        //1.直接通过对象获取对应的类对象
        Car car = new Car();
        Class carClass1 = car.getClass();
        System.out.println(carClass1);

        //2.通过类型获取类对象
        Class carClass2 = Car.class;
        System.out.println(carClass2);

        //3.通过Class静态方法，获取全限定名指定的Class对象。
        try {
            Class carClass3 = Class.forName("javareflectusage.Car");
            System.out.println(carClass3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取Class对象后，根据Class对象获取，类中的成员，Constructor,Field,Method
    @SuppressWarnings("Duplicates")
    public static void test02() {

        try {
            Class carClass3 = Class.forName("javareflectusage.Car");

            //默认构造
            Constructor<Car> constructor1 = carClass3.getConstructor();
            Car car1 = constructor1.newInstance(null);
            car1.printInfo();

            //有参数构造
            Constructor<Car> constructor2 = carClass3.getConstructor(String.class);
            Car car2 = constructor2.newInstance("德国");
            car2.printInfo();


        } catch (ClassNotFoundException |
                NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException
                e) {
            e.printStackTrace();
        }

    }

    //Field
    @SuppressWarnings("Duplicates")
    public static void test03() {
        try {
            Class carClass = Class.forName("javareflectusage.Car");

            Field field = carClass.getDeclaredField("name");

            field.setAccessible(true);

            Car car = (Car) carClass.getConstructor().newInstance(null);

            field.set(car, "美国");

            car.printInfo();
        } catch (ClassNotFoundException |
                NoSuchMethodException |
                NoSuchFieldException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException
                e) {
            e.printStackTrace();
        }
    }

    //Method
    @SuppressWarnings("Duplicates")
    public static void test04() {
        try {
            Class carClass = Class.forName("javareflectusage.Car");

            Constructor<Car> constructor = carClass.getConstructor(String.class);

            Car car = constructor.newInstance("中国");

            Method print = carClass.getMethod("printInfo", null);
            print.invoke(car, null);

            Method getCarInfo = carClass.getMethod("getCarInfo", String.class);
            String info = (String)getCarInfo.invoke(car, "仿照");
            System.out.println(info);

        } catch (ClassNotFoundException |
                NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException
                e) {
            e.printStackTrace();
        }
    }

}
