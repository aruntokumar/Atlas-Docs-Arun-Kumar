package day08_java;

class Node1 {
    int data;
    Node1 next;

    Node1(int data) {
        this.data = data;
        this.next = null;
    }
}

// Circular linked list class renamed to DeleteNode
public class DeleteNode {
    Node1 head;

    // Add node at the end
    public void add(int data) {
        Node1 newNode = new Node1(data);
        if (head == null) {
            head = newNode;
            head.next = head;
        } else {
            Node1 current = head;
            while (current.next != head) {
                current = current.next;
            }
            current.next = newNode;
            newNode.next = head;
        }
    }

    // Display list elements
    public void display() {
        if (head == null) {
            System.out.println("The list is empty.");
            return;
        }
        Node1 current = head;
        System.out.print("Circular Linked List Elements: ");
        do {
            System.out.print(current.data + " ");
            current = current.next;
        } while (current != head);
        System.out.println();
    }

    // Delete first occurrence of key
    public void delete(int key) {
        if (head == null) return;

        // Only one node
        if (head.next == head && head.data == key) {
            head = null;
            return;
        }

        // If head node needs deletion
        if (head.data == key) {
            Node1 last = head;
            while (last.next != head) {
                last = last.next;
            }
            head = head.next;
            last.next = head;
            return;
        }

        // Deleting other nodes
        Node1 prev = head;
        Node1 curr = head.next;
        while (curr != head) {
            if (curr.data == key) {
                prev.next = curr.next;
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }

    // Test the implementation
    public static void main(String[] args) {
        DeleteNode list = new DeleteNode();

        list.add(20);
        list.add(25);
        list.add(30);
        list.add(40);

        list.display();            // Output: 20 25 30 40

        list.delete(25);
        list.display();            // Output: 20 30 40

        list.delete(20);           // Deleting head
        list.display();            // Output: 30 40

        list.delete(40);           // Deleting last
        list.display();            // Output: 30

        list.delete(30);           // Deleting sole node
        list.display();            // Output: The list is empty.
    }
}