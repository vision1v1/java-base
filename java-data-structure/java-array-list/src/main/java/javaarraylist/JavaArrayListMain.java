package javaarraylist;

import java.util.ArrayList;
import java.util.List;

public class JavaArrayListMain {
    public static void main(String[] args){
        //testArrayListSubListIssue();

        testFixArrayListSubListIssue();
    }


    //问题
    //会出现 java.util.ConcurrentModificationException
    public static void testArrayListSubListIssue(){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("1");
        arrayList.add("2");

        //这里subList与arrayList共用了相同的存储空间。
        //ArrayList 的 modCount = 2 这是 求出来的 subList的 modCount = 2
        List<String> subList = arrayList.subList(1,2);

        //subList 的 modeCount = 2 而 ArrayList的 modCount = 3
        arrayList.add("3");


        //在迭代过程中源码判断了，ArrayList的modCount 与 subList 的 modCount不相等。
        //所以抛出了 java.util.ConcurrentModificationException
        for(String item:subList){
            System.out.println(item);
        }
    }

    //subList的作者，在注释中也明确的说出，subList的base-list就是ArrayList，如果ArrayList结构修改了，在遍历subList时就会导致不可预期的错误
    //所以源码中添加了modCount的判断，并抛出了java.util.ConcurrentModificationException异常
    //解决办法1 直接复制一份出来
    //解决办法2 不是使用这个微妙的代码。.net的 就是直接浅表复制一份出来。
    public static void testFixArrayListSubListIssue(){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("1");
        arrayList.add("2");

        //根据subList的迭代器复制一份出来
        List<String> subList = new ArrayList<String>(arrayList.subList(1,2));

        arrayList.add("3");

        for(String item:subList){
            System.out.println(item);
        }
    }
}
