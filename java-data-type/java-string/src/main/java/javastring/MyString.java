package javastring;

public class MyString implements java.io.Serializable, Comparable<String>, CharSequence {
    @Override
    public int compareTo(String o) {
        return 0;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }
}
