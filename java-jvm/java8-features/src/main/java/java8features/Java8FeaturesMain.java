package java8features;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

public class Java8FeaturesMain {


    public static void main(String[] args) {
        test01();

        test02();

        test03();

        test04();

        test05();

        test06();

        test07();

        test08();

        test09();

        test10();

        test11();

        test12();

        test13();

        test14();

        test15();
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

    static class TestForFunc {
        private String name;

        public TestForFunc() {
            this("haha");
        }

        public TestForFunc(String name) {
            this.name = name;
        }

        public Integer valueOf(String str) {
            System.out.println("Hi " + str);
            return Integer.valueOf(str);
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return name + " ";
        }
    }

    //方法引用
    //构造函数引用
    public static void test04() {
        //静态方法引用
        MyFunctionInterface<String, Integer> fromTo = Integer::valueOf;

        System.out.println(fromTo.convert("456"));

        TestForFunc func = new TestForFunc();

        //实例方法引用
        fromTo = func::valueOf;

        System.out.println(fromTo.convert("666"));

        //构造函数引用，注意返回类型。
        //根据fun2中函数签名，选择合适的构造函数。
        MyFunctionInterface2<String, TestForFunc> fun2 = TestForFunc::new;

        TestForFunc obj = fun2.newObj("obj");

        System.out.println(obj.getName());

    }

    //Lambda 作用域 访问局部变量
    public static void test05() {

        //访问 这里面隐性的 final，如果在后面对a进行修改赋值，就会报错。
        //所以在lambda里面修改a,也会报错。
        final String a = "123";
        //Lambda 方式
        MyFunctionInterface<String, String> fun = (from) -> {
            //可以访问外层的变量。
            return from + a;
        };
        //a="321";
        System.out.println(fun.convert("haha"));

        //这里面隐性的 final，如果在后面对b进行修改赋值。
        //所以最好显示的标记上 final。
        String b = "456";
        //匿名对象方式
        MyFunctionInterface<String, String> fun2 = new MyFunctionInterface<String, String>() {
            @Override
            public String convert(String from) {
                return from + b;
            }
        };
        System.out.println(fun2.convert("hehe"));
        //b = "100";

    }

    private String name = "000";
    private static int age = 20;

    //Lambda 作用域 访问对象字段与类变量。
    public void testLambda() {
        //Lambda 函数可以访问对象字段。也可以修改。类变量同理
        MyFunctionInterface<String, String> fun = (from) -> {
            name = "456";
            age = 10;
            return from + name + age;
        };
        age = 0;
        name = "123";
        System.out.println(fun.convert("999"));

        //匿名对象 可以访问对象字段，也可修改。类变量同理
        MyFunctionInterface<String, String> fun2 = new MyFunctionInterface<String, String>() {
            @Override
            public String convert(String from) {
                name = "456";
                age = 30;
                return from + name + age;
            }
        };
        name = "123";
        age = 99;
        System.out.println(fun2.convert("888"));
    }

    //测试对象字段与类变量的访问与修改。
    public static void test06() {
        Java8FeaturesMain main = new Java8FeaturesMain();
        main.testLambda();
    }

    //测试在Lambda中 使用接口的扩展方法
    public static void test07() {

        //测试在Lambda中不能访问，函数式接口的默认扩展方法。
        MyFunctionInterface<String, String> fun = (from) -> {
            return from;
        };

        System.out.println(fun.convert("123"));

        //在匿名对象中，是可以使用的扩展方法的。
        MyFunctionInterface<String, String> fun2 = new MyFunctionInterface<String, String>() {
            @Override
            public String convert(String from) {
                return this.connect(from, "666");
            }
        };

        System.out.println(fun2.convert("999"));

    }

    //测试使用java自身的一些常用的函数式接口
    public static void test08() {

        //谓词工具 Predicate<T>
        //字符串不为空，并且不以"a"开头，或者以"d"结尾
        Predicate<String> notNull = Objects::nonNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> p = notNull.and(isEmpty.negate()).and((s) -> !s.startsWith("a")).or(s -> s.endsWith("d"));

        System.out.println(p.test("abc"));//false
        System.out.println(p.test(""));//false
        System.out.println(p.test("okd"));//true
        System.out.println(p.test("abcd"));//true

        //Function<T,R> 一般用于功能逻辑的组合
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<Integer, Double> add100 = i -> new Double(i + 100);
        Function<String, Double> result = toInteger.andThen(add100);

        System.out.println(result.apply("300"));

        //Supplier<T> 一般用于提供模式，创建模式。
        Supplier<TestForFunc> createTestForFunc = TestForFunc::new;
        TestForFunc tff = createTestForFunc.get();
        System.out.println(tff.getName());


        //Consumer<T> 一般用于操作消费模式，没有返回值的
        Consumer<String> print = s -> System.out.println(s);
        Consumer<String> doublePrint = print.andThen(System.out::println);
        doublePrint.accept("hi WCG2019");

        //Comparator<T> 比较器的使用
        Comparator<TestForFunc> comparator = (t1, t2) -> t1.getName().compareTo(t2.getName());
        TestForFunc t1 = new TestForFunc("infi");
        TestForFunc t2 = new TestForFunc("th000");

        System.out.println(comparator.compare(t1, t2));
        System.out.println(comparator.reversed().compare(t1, t2));//反转比较器


    }

    //测试 Optional
    public static void test09(){
        Optional<String> optional = Optional.of("abc");//创建一个"abc"
        System.out.println(optional.isPresent());//判断值是否存在。
        System.out.println(optional.get());//返回值。
        System.out.println(optional.orElse("default"));//如果不存在值，创建一个"default"值。
        optional.ifPresent(s->System.out.println(s.charAt(0)));//如果存在，输出首字母。
    }


    //测试 stream 操作
    public static void test10(){
        List<String> list = new ArrayList<>();
        list.add("ddd2");
        list.add("aaa2");
        list.add("bbb1");
        list.add("aaa1");
        list.add("bbb3");
        list.add("ccc");
        list.add("bbb2");
        list.add("ddd1");
        list.add("eee1");


        //Filter 过滤
        StreamSupport.stream(list.spliterator(),false).filter(s->s.startsWith("b")).forEach(System.out::println);
        list.stream().filter(s->s.startsWith("a")).forEach(System.out::println);
        System.out.println(list);

        //sorted 排序器 需要注意的是，排序只创建了一个排列好后的Stream，而不会影响原有的数据源，排序之后原数据 是不会被修改的
        list.stream().sorted((a,b)->b.compareTo(a)).filter(s->s.startsWith("a")).forEach(System.out::print);
        System.out.println(list);


        //map 映射，一般需要将stream中元素映射成另外一个元素。
        list.stream().map(s-> new TestForFunc(s)).sorted(Comparator.comparing(TestForFunc::getName)).forEach(System.out::print);
        System.out.println();

        //match 最终操作
        //有匹配的就行
        boolean anyMatch = list.stream().anyMatch(s->s.startsWith("a"));
        System.out.println(anyMatch);
        //都匹配
        boolean allMatch = list.stream().allMatch(s->s.startsWith("a"));
        System.out.println(allMatch);
        //都不匹配
        boolean noneStartWith = list.stream().noneMatch(s->s.startsWith("k"));
        System.out.println(noneStartWith);

        //count 最终操作
        long count = list.stream().filter(s->s.startsWith("a")).count();
        System.out.println(count);

        //Reduce 规约 最终操作
        Optional<String> reduced = list.stream().sorted().reduce((s1,s2)-> s1 + "," + s2);
        reduced.ifPresent(System.out::println);


    }

    //测试 并行 stream
    public static void test11(){
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));


        t0 = System.nanoTime();
        count = values.parallelStream().sorted().count();
        System.out.println(count);

        t1 = System.nanoTime();

        millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }

    //测试 map
    public static void test12(){
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((id, val) -> System.out.println(id + " : " + val));


        map.computeIfPresent(3, (num, val) -> val + num);
        System.out.println(map.get(3));             // val33

        map.computeIfPresent(9, (num, val) -> null);
        System.out.println(map.containsKey(9));     // false

        map.computeIfAbsent(23, num -> "val" + num);
        System.out.println(map.containsKey(23));    // true

        map.computeIfAbsent(3, num -> "bam");
        System.out.println(map.get(3));             // val33


        map.remove(3, "val3");
        System.out.println(map.get(3));             // val33
        map.remove(3, "val33");
        System.out.println(map.get(3));             // null

        System.out.println(map.getOrDefault(42, "not found"));  // not found

        System.out.println(map.get(1));
        map.merge(1, "newValue", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(1));             // val9
        map.merge(1, "concat", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(1));             // val9concat
    }

    // 测试 Clock
    // Clock类提供了访问当前日期和时间的方法，Clock是时区敏感的，
    // 可以用来取代 System.currentTimeMillis() 来获取当前的微秒数。
    // 某一个特定的时间点也可以使用Instant类来表示，Instant类也可以用来创建老的java.util.Date对象。
    public static void test13(){
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);   // legacy java.util.Date
        System.out.println(legacyDate);
    }

    //Timezones 时区 LocalTime 本地时间
    public static void test14(){
        System.out.println(ZoneId.getAvailableZoneIds());
        // prints all available timezone ids
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());

        // ZoneRules[currentStandardOffset=+01:00]
        // ZoneRules[currentStandardOffset=-03:00]

        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);
        System.out.println(now1.isBefore(now2));  // false

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);       // -3
        System.out.println(minutesBetween);     // -239

        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late);       // 23:59:59
        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMAN);

        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println(leetTime);   // 13:37

    }

    //test LocalDate
    public static void test15(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
    }


}
