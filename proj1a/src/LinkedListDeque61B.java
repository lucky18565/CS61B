import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private Node sentinel;
    private int size;

    public class Node {
        private T item;
        private Node prev;
        private Node next;
        public Node(T x, Node n, Node g) {
            item = x;
            prev = n;
            next = g;
        }
    }

    public LinkedListDeque61B() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }


    @Override
    public void addFirst(T x) {
        size++;
        Node old = sentinel.next;
        Node newNode = new Node(x, sentinel, old);
        sentinel.next = newNode;
        old.prev = newNode;
    }

    @Override
    public void addLast(T x) {
        size++;
        Node old = sentinel.prev;
        Node newNode = new Node(x, old, sentinel);
        sentinel.prev = newNode;
        old.next = newNode;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            returnList.add(current.item);
            current = current.next;
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
        size--;
        Node removeNode = sentinel.next;
        sentinel.next = removeNode.next;
        removeNode.next.prev = sentinel;
        return removeNode.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        Node removeNode = sentinel.prev;
        sentinel.prev = removeNode.prev;
        removeNode.prev.next = sentinel;
        return removeNode.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public T getRecursive(int index) {
        return helpGetRecursive(index, 0, sentinel.next);
    }
    public T helpGetRecursive(int index, int k, Node current) {
        if (index < 0 || index >= size) {
            return null;
        }
        if (k < index) {
            return helpGetRecursive(index, k + 1, current.next);
        }
        return current.item;
    }
}
