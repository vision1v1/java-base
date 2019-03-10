package javaproxyusage;

public class BookstoreImpl implements Bookstore {
    @Override
    public void addBook() {
        System.out.println(" BookstoreImpl + addBook ");
    }

    @Override
    public void updateBook() {
        System.out.println(" BookstoreImpl + updateBook ");
    }


}
