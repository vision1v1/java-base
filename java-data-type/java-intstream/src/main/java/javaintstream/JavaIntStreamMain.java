package javaintstream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.*;

import static java.util.stream.IntStream.builder;

public class JavaIntStreamMain {
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
    }

    public static void test01() {
        IntStream intStream = IntStream.of(1, 2, 3, 4, 5);
        intStream.forEach(System.out::print);
        System.out.println();
    }

    public static void test02() {
        IntStream intStream = Arrays.stream(new int[]{1, 2, 3, 4, 5});
        intStream.forEach(JavaIntStreamMain::print);
        System.out.println();
    }

    public static void test03() {
        int[] ints = new int[]{1, 2, 3, 4, 5};
        IntStream intStream = Arrays.stream(ints, 0, ints.length);
        intStream.forEach(JavaIntStreamMain::print);
        System.out.println();
    }

    @SuppressWarnings("Duplicates")
    public static void test04() {
        int[] ints = new int[]{1, 2, 3, 4, 5};
        Spliterator.OfInt spliterator = Arrays.spliterator(ints, 0, ints.length);
        IntStream intStream = StreamSupport.intStream(spliterator, false);
        intStream.forEach(JavaIntStreamMain::print);
        System.out.println();
    }

    @SuppressWarnings("Duplicates")
    public static void test05() {
        class IntArraySpliterator implements Spliterator.OfInt {

            private final int[] array;
            private int index;        // current index, modified on advance/split
            private final int fence;  // one past last index
            private final int characteristics;

            public IntArraySpliterator(int[] array, int additionalCharacteristics) {
                this(array, 0, array.length, additionalCharacteristics);
            }

            public IntArraySpliterator(int[] array, int origin, int fence, int additionalCharacteristics) {
                this.array = array;
                this.index = origin;
                this.fence = fence;
                this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
            }

            @Override
            public OfInt trySplit() {
                int lo = index, mid = (lo + fence) >>> 1;
                return (lo >= mid)
                        ? null
                        : new IntArraySpliterator(array, lo, index = mid, characteristics);
            }

            @Override
            public long estimateSize() {
                return (long) (fence - index);
            }

            @Override
            public int characteristics() {
                return characteristics;
            }

            @Override
            public boolean tryAdvance(IntConsumer action) {
                if (action == null)
                    throw new NullPointerException();
                if (index >= 0 && index < fence) {
                    action.accept(array[index++]);
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(IntConsumer action) {
                int[] a;
                int i, hi; // hoist accesses and checks from loop
                if (action == null)
                    throw new NullPointerException();
                if ((a = array).length >= (hi = fence) &&
                        (i = index) >= 0 && i < (index = hi)) {
                    do {
                        action.accept(a[i]);
                    } while (++i < hi);
                }
            }
        }


        int[] ints = new int[]{1, 2, 3, 4, 5};
        Spliterator.OfInt spliterator = new IntArraySpliterator(ints, 0, ints.length, Spliterator.ORDERED | Spliterator.IMMUTABLE);
        IntStream intStream = StreamSupport.intStream(spliterator, false);
        intStream.forEach(JavaIntStreamMain::print);
        System.out.println();
    }


    @SuppressWarnings("Duplicates")
    public static void test06() {
        int[] ints = new int[]{1, 2, 3, 4, 5};
        Spliterator.OfInt spliterator = Arrays.spliterator(ints, 0, ints.length);
        IntStream intStream = StreamSupport.intStream(spliterator, false);
        intStream = intStream.filter(e -> {
            return e >= 2;
        }).filter(e -> e < 4);
        //intStream.forEach(JavaIntStreamMain::print);

        int[] result = intStream.toArray();
        //int[] result2 = intStream.toArray();//stream has already been operated upon or closed
        printIntArray(result);
        System.out.println();
    }

    @SuppressWarnings("Duplicates")
    public static void test07() {
        int[] ints = new int[]{1, 2, 3, 4, 5};
        Spliterator.OfInt spliterator = Arrays.spliterator(ints, 0, ints.length);
        IntStream intStream = StreamSupport.intStream(spliterator, false);
        intStream = intStream.map(a -> {
            return a + 1;
        });
        int[] result = intStream.toArray();
        //int[] result2 = intStream.toArray();//stream has already been operated upon or closed
        printIntArray(result);
        System.out.println();
    }

    @SuppressWarnings("Duplicates")
    public static void test08() {

        class Student {

            Student(int id) {
                this.Id = id;
            }

            public int getId() {
                return Id;
            }

            public void setId(int id) {
                Id = id;
            }

            @Override
            public String toString() {
                return "StudentId : " + Id;
            }

            private int Id;
        }

        int[] ints = new int[]{1, 2, 3, 4, 5};
        Spliterator.OfInt spliterator = Arrays.spliterator(ints, 0, ints.length);
        IntStream intStream = StreamSupport.intStream(spliterator, false);
        Stream<Student> stream = intStream.mapToObj(i -> {
            return new Student(i);
        });
        List<Student> studentList = stream.collect(Collectors.toList());
        //List<Student> studentList2 = stream.collect(Collectors.toList());//stream has already been operated upon or closed
        studentList.forEach(s -> {
            System.out.println(s);
        });
        System.out.println();
    }

    public static void test09() {

        class Student {

            Student(int id, String name, boolean sex) {
                this.Id = id;
                this.sex = sex;
                this.name = name;
            }

            public int getId() {
                return Id;
            }

            public void setId(int id) {
                Id = id;
            }

            @Override
            public String toString() {
                return "[StudentId : " + Id + " Name : " + name + " Sex : " + sex + "]";
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isSex() {
                return sex;

            }

            public void setSex(boolean sex) {
                this.sex = sex;
            }



            private int Id;
            private boolean sex;
            private String name;
        }

        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "infi", true));
        students.add(new Student(2, "th000", false));
        students.add(new Student(3, "fly", true));
        students.add(new Student(4, "lyn", true));
        students.add(new Student(5, "moon", false));

        List<Student> studentList = students.stream().filter(student -> student.sex == true).filter(student -> student.Id > 2).collect(Collectors.toList());

        studentList.forEach(student -> System.out.println(student));
    }

    public static void test10(){
        int[] ints = new int[]{1, 2, 3, 4, 5};
        IntStream stream = Arrays.stream(ints);
        int[] result = stream.flatMap(e->IntStream.of(e+e)).toArray();
        printIntArray(result);
    }

//    public static void test11(){
//        int[] ints1 = new int[]{1, 2, 3, 4, 5};
//        IntStream stream1 = Arrays.stream(ints1);
//        int[] ints2 = new int[]{-1, -2, -3, -4, -5};
//        IntStream stream2 = Arrays.stream(ints2);
//
//        int[] result = stream1.flatMap(e-> stream2.map(s->s+e)).toArray();
//        printIntArray(result);
//    }


    private static void print(int i) {
        System.out.print(i);
    }

    private static void printIntArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
    }
}
