package producerconsumerproblem;

public class FixedQueue<T> {

    public static final int DEFAULT_SIZE = 10;

    private int size = 0;

    private int elementCount = 0;

    private int insert = 0;

    private int remove = 0;

    private Object[] elements;

    FixedQueue(int initSize){
        elements = new Object[initSize];
        size = initSize;
        elementCount = 0;
        insert = 0;
        remove = 0;
    }

    FixedQueue(){
        this(DEFAULT_SIZE);
    }

    public T poll(){
        if(elementCount == 0) return null;
        T element = (T) elements[remove];
        remove ++;
        elementCount--;
        if(remove == size) remove = 0;
        return element;
    }

    public boolean offer(T element){
        if(elementCount == size) return false;
        elements[insert++] = element;
        elementCount++;
        if(insert == size) insert = 0;
        return true;
    }

    public boolean isFull(){
        return elementCount == size;
    }

    public boolean isEmpty(){
        return elementCount == 0;
    }

    public int getElementCount(){
        return elementCount;
    }
}
