// Node class
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// LinkedList class
public class LinkedList {
    Node head;

    // Method to add a node at the end
    public void add(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Method to traverse and display the list
    public void display() {
        Node current = head;

        if (current == null) {
            System.out.println("The list is empty.");
            return;
        }

        System.out.print("Linked List Elements: ");
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Main method
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        // Adding 4 elements
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);

        // Displaying elements
        list.display();
    }
}