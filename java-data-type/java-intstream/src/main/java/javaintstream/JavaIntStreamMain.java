package javaintstream;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.*;

public class JavaIntStreamMain {
    public static void main(String[] args) {
        test01();
        test02();
        test03();
        test04();
        test05();
        test06();
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
            return e > 2;
        }).filter(e -> e < 4);
        //intStream.forEach(JavaIntStreamMain::print);
        int[] result = intStream.toArray();
        printIntArray(result);
        System.out.println();
    }


    private static void print(int i) {
        System.out.print(i);
    }

    private static void printIntArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
    }
}
