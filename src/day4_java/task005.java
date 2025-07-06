package day4_java;

// Counter.java
class Counter2 {
    private int count = 0;

    // Only the critical section is synchronized
    public void increment() {
        synchronized (this) {
            count++;
        }
    }

    // No synchronization here; safe in this simple case
    public int getCount() {
        return count;
    }
}

// Main.java
public class task005 {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        // Thread 1
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        // Thread 2
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        // Start the threads
        t1.start();
        t2.start();

        // Wait for both threads to finish
        t1.join();
        t2.join();

        // Print the final count
        System.out.println("Final Count: " + counter.getCount());
    }
}