package day07_java;

public class DoublyLinkedList {
    // Node structure
    private static class Node {
        int data;
        Node prev;
        Node next;

        Node(int d) {
            data = d;
        }
    }

    private Node head;
    private Node tail;

    // Add at beginning
    public void insertAtBeginning(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    // Add at end
    public void insertAtEnd(int data) {
        Node node = new Node(data);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    // Insert at specific position (1-based index)
    public void insertAtPosition(int data, int position) {
        if (position <= 1 || head == null) {
            insertAtBeginning(data);
            return;
        }
        Node current = head;
        int idx = 1;
        while (current.next != null && idx < position - 1) {
            current = current.next;
            idx++;
        }
        if (current.next == null) {
            insertAtEnd(data);
        } else {
            Node node = new Node(data);
            node.next = current.next;
            node.prev = current;
            current.next.prev = node;
            current.next = node;
        }
    }

    // Delete node at specific position (1-based index)
    public void deleteAtPosition(int position) {
        if (head == null) return;
        if (position == 1) {
            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            return;
        }
        Node current = head;
        int idx = 1;
        while (current != null && idx < position) {
            current = current.next;
            idx++;
        }
        if (current == null) return;
        if (current == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
    }

    // Traverse forward
    public void traverseForward() {
        Node n = head;
        while (n != null) {
            System.out.print(n.data + " ");
            n = n.next;
        }
        System.out.println();
    }

    // Traverse backward
    public void traverseBackward() {
        Node n = tail;
        while (n != null) {
            System.out.print(n.data + " ");
            n = n.prev;
        }
        System.out.println();
    }

    // Sample usage
    public static void main(String[] args) {
        DoublyLinkedList dll = new DoublyLinkedList();
        dll.insertAtEnd(10);
        dll.insertAtEnd(20);
        dll.insertAtBeginning(5);
        dll.insertAtPosition(15, 3);
        dll.traverseForward();    // 5 10 15 20
        dll.traverseBackward();   // 20 15 10 5
        dll.deleteAtPosition(2);
        dll.traverseForward();    // 5 15 20
    }
}