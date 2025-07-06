package day07_java;


import java.util.NoSuchElementException;
import java.util.StringJoiner;

class Node<T> {
    T data;
    Node<T> next;

    Node(T data) {
        this.data = data;
        this.next = null;
    }
}

public class CustomLinkedList<T> {
    private Node<T> head;
    private int size = 0;

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
        size++;
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        T removed = head.data;
        head = head.next;
        size--;
        return removed;
    }

    public T get(int index) {
        checkBounds(index);
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" -> ", "[", "]");
        Node<T> curr = head;
        while (curr != null) {
            sj.add(curr.data.toString());
            curr = curr.next;
        }
        return sj.toString();
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " out of bounds for size " + size);
        }
    }

    // Demo
    public static void main(String[] args) {
        CustomLinkedList<String> liobj = new CustomLinkedList<>();
        liobj.add("Anitha");
        liobj.add("Verma");
        liobj.addFirst("Jack");

        System.out.println("List: " + liobj);
        System.out.println("First Element: " + liobj.get(0));
        System.out.println("Size: " + liobj.size());

        liobj.removeFirst();

        System.out.println("After removal at front...");
        System.out.println("List: " + liobj);
        System.out.println("First Element: " + liobj.get(0));
        System.out.println("Size: " + liobj.size());

        // Additional demonstration
        liobj.clear();
        System.out.println("After clear(), isEmpty? " + liobj.isEmpty());
    }
}