import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Shared Counter class
class Counter4 {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // Acquire lock
        try {
            count++;
        } finally {
            lock.unlock(); // Always release lock
        }
    }

    public int getCount() {
        return count;
    }
}

// Thread class that increments the counter
class ThreadDemo6 extends Thread {
    private final Counter4 counter;

    ThreadDemo6(Counter4 counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            counter.increment();
        }
    }
}

// Main class
public class task009 {
    public static void main(String[] args) {
        Counter4 counter = new Counter4();

        ThreadDemo6 t1 = new ThreadDemo6(counter);
        ThreadDemo6 t2 = new ThreadDemo6(counter);

        t1.start();
        t2.start();

        try {
            t1.join(); // Wait for t1 to finish
            t2.join(); // Wait for t2 to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Expected output: 40
        System.out.println("Final count: " + counter.getCount());
    }
}