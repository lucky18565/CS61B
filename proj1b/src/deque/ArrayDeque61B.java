package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private int size;
    private int front;
    private int back;
    private T[] array;
    private static final int THRESHOLD = 16;


    public ArrayDeque61B() {
        this.size = 0;
        this.front = 0;
        this.back = 1;
        this.array = (T[]) new Object[8];
    }



    public Iterator<T> iterator() {
        return new Foor();
    }
    private class Foor implements Iterator<T> {
        int current = 0;
        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T item = get(current);
            current += 1;
            return item;
        }
    }

    @Override
    public boolean equals(Object s) {
        if (this == s) {
            return true;
        }
        if (s instanceof Deque61B<?> newx) {
            if (size != newx.size()) {
                return false;
            }
            java.util.Iterator<?> it = newx.iterator();
            for (T x : this) {
                Object y = it.next(); // 对应位置元素
                if (!java.util.Objects.equals(x, y)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String list = "[";
        for (int i = 0; i < size; i++) {
            list += get(i);
            if (i != size - 1) {
                list += ", ";
            }
        }
        list += "]";
        return list;
    }

    public int getFrontIndex() {
        return front;
    }
    public int getBackIndex() {
        return back;
    }
    public int getArrayLength() {
        return array.length;
    }
    public void resize(int newLength) {
        T[] newArray = (T[]) new Object[newLength];
        for (int i = 0; i < size; i++) {
            int id = Math.floorMod(front + 1 + i, array.length);
            newArray[i] = array[id];
        }
        array = newArray;
        front = newLength - 1;
        back = size;
    }
    @Override
    public void addFirst(T x) {
        if (size == array.length) {
            resize(getArrayLength() * 2);
        }
        size++;
        array[front] = x;
        front = Math.floorMod(front - 1, array.length);
    }

    @Override
    public void addLast(T x) {
        if (size == array.length) {
            resize(getArrayLength() * 2);
        }
        size++;
        array[back] = x;
        back = Math.floorMod(back + 1, array.length);

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int id = Math.floorMod(front + 1 + i, array.length);
            returnList.add(array[id]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        front = Math.floorMod(front + 1, array.length);
        T first = array[front];
        array[front] = null;
        size--;
        if (array.length >= THRESHOLD && size < array.length / 4) {
            resize(array.length / 2);
        }
        return first;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        back = Math.floorMod(back - 1, array.length);
        T finlly = array[back];
        array[back] = null;
        size--;
        if (array.length >= THRESHOLD && size < array.length / 4) {
            resize(array.length / 2);
        }
        return finlly;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int id = Math.floorMod(front + 1 + index, array.length);
        return  array[id];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
