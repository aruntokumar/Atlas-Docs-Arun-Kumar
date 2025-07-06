package day4_java;

class Counter3 {
    private static int count = 0;  // Shared among all instances (or no instances)

    public static synchronized void increment() {
        count++;  // Thread-safe increment
    }

    public static int getCount() {
        return count;
    }
}
public class task006 {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Counter3.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Counter3.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Count: " + Counter3.getCount()); // Expected: 2000
    }
}