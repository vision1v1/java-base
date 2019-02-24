package javalinkedhashset;

import java.util.LinkedHashSet;

public class JavaLinkedHashSetMain {
    public static void main(String[] args){
        test01();
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

    public static void test01(){

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();

        linkedHashSet.add("th000");
        linkedHashSet.add("infi");
        linkedHashSet.add("fly100");
        linkedHashSet.add("ted");

        linkedHashSet.stream().forEach(s->{
            System.out.println(s);
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
