package javavector;

import java.util.Vector;

public class JavaVectorMain {
    public static void main(String[] args){
        test01();
    }

    static class Element {
        private int e = 0;

        Element(int data) {
            this.e = data;
        }

        @Override
        public String toString() {
            return Integer.toString(e);
        }
    }

    public static void test01(){
        Vector<Element> vector = new Vector<>();
        for (int i=0;i<100;i++){
            vector.add(new Element(i));
        }

        vector.stream().forEach(item->{
            System.out.println(item);
        });
    }
}
