package day4_java;

// Counter.java
class Counter1 {
    private int count = 0;

    // Thread-safe increment method
    public synchronized void increment() {
        count++;
    }

    // Getter method
    public int getCount() {
        return count;
    }
}

// Main.java
public class task004 {
    public static void main(String[] args) throws InterruptedException {
        Counter1 counter = new Counter1();

        // Thread 1: increments counter 1000 times
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        // Thread 2: increments counter 1000 times
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        // Start both threads
        t1.start();
        t2.start();

        // Wait for both threads to finish
        t1.join();
        t2.join();

        // Output the final count (should be 2000)
        System.out.println("Final Count: " + counter.getCount());
    }
}