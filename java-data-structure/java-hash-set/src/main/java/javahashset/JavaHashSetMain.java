package javahashset;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class JavaHashSetMain {

    public static void main(String[] args){
        test01();
        System.out.println("-----------------");
        test02();
    }

    static class Element{
        private int e =0;
        Element(int data){
            this.e = data;
        }

        @Override
        public String toString() {
            return Integer.toString(e);
        }
    }

    //注意HashSet插入顺序与输出顺序不一定一致，这是与LinkedHashSet的主要区别。
    //HashSet的插入比LinkedHashSet快
    public static void test01(){
        HashSet<Element> hashSet = new HashSet<>();

        for(int i=0;i<100;i++){
            hashSet.add(new Element(i));
        }

        hashSet.stream().forEach(item->{
            System.out.println(item);
        });
    }

    public static void test02(){

        LinkedHashSet<Element> linkedHashSet = new LinkedHashSet<>();

        for(int i=0;i<100;i++){
            linkedHashSet.add(new Element(i));
        }

        linkedHashSet.stream().forEach(s->{
            System.out.println(s);
        });

    }
}
