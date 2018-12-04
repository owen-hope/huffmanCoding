//OWEN HOPE cs610 5191 prp
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class minHeap5191<T> implements Iterable<T> {
    //Array to store the items for indecie 1 to n
    private T[] min;
    //number of items in min heap
    private int size;

    private Comparator<T> comparator;
    
    @SuppressWarnings("unchecked")
    public minHeap5191(int initSize) {
        min = (T[])new Object[initSize +1];
        size = 0;
    }

    public minHeap5191(){
        this(1);
    }
    
    @SuppressWarnings("unchecked")
    public minHeap5191(int initSize, Comparator<T> comparator) {
        this.comparator = comparator;
        min = (T[]) new Object[initSize + 1];
        size = 0;
    }

    public minHeap5191(Comparator<T> comparator) {
        this(1, comparator);
    }
    
    @SuppressWarnings("unchecked")
    public minHeap5191(T[] val) {
        size = val.length;
        min = (T[]) new Object[val.length + 1];
        for (int i = 0; i < size; i++) {
            min[i + 1] = val[i];
        }
        for (int k = size/2; k >= 1; k--) {
            downHeap(k);
        }
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    //Return smallest key
    public T getMin() {
        if (isEmpty()){
            throw new NoSuchElementException("Empty");
        }
        return min[1];
    }
    
    @SuppressWarnings("unchecked")
    //To resize the heap This would not be needed in the PrP
    private void resize(int newSize) {
        assert newSize > size;
        T[] temp = (T[]) new Object[newSize];
        for (int i = 1; i <= size; i++) {
            temp[i] = min[i];
        }

        min = temp;
    }

    public void insert(T el) {
        //Check to see the element can fit in the array
        if (size == min.length - 1) {
            resize(2 * min.length);
        }

        min[++size] = el;
        upHeap(size);
        assert isminHeap();
    }
    //REMOVES and RETURNS smallest key
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty");
        }

        T minVal = min[1];
        swap(1, size--);
        downHeap(1);
        assert isminHeap();
        return minVal;
    }

    public void upHeap(int x) {
        while (x > 1 && Compare(x/2, x)) {
            swap(x, x/2);
            x = x/2;
        }
    }

    public void downHeap(int x) {
        while (2*x <= size) {
            int y = 2*x;
            if (y < size && Compare(y, y+1)) {
                y++;
            }
            if (!Compare(x, y)){
                break;
            }
            swap(x,y);
            x = y;
        }
    }
    
    @SuppressWarnings("unchecked")
    private boolean Compare(int i, int j) {
        if (comparator == null) {
            return ((Comparable<T>) min[i]).compareTo(min[j]) > 0;
        } else {
            return comparator.compare(min[i], min[j]) > 0;
        }
    }

    private void swap(int i, int j) {
        T temp = min [i];
        min[i] = min [j];
        min[j] = temp;
    }

    private boolean isminHeap() {
        return isminHeap(1);
    }

    private boolean isminHeap(int i) {
        if (i > size) {
            return true;
        }

        int left = 2*i;
        int right = 2*i + 1;

        if (left <= size && Compare(i, left)) {
            return false;
        }
        if (right <= size && Compare(i, right)) {
            return false;
        }

        return isminHeap(left) && isminHeap(right);
    }

    public Iterator<T> iterator() {
        return new heapIT();
    }

    private class heapIT implements Iterator<T> {
        private minHeap5191<T> minHeapTemp;
        public heapIT() {
            if (comparator == null) {
                minHeapTemp = new minHeap5191<>(size());
            }else {
                minHeapTemp = new minHeap5191<>(size(), comparator);
            }

            for (int i = 1; i <= size; i++) {
                minHeapTemp.insert(min[i]);
            }
        }

        public boolean hasNext() {
            return !minHeapTemp.isEmpty();
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return minHeapTemp.removeMin();
        }
    }

}
