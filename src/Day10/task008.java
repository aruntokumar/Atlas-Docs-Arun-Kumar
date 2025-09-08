class Counter3 {
    private static int count = 0;

    public static synchronized void increment() {
        count++;
    }

    public static int getCount() {
        return count;
    }
}

class ThreadDemo5 extends Thread {
    Counter3 counter;

    ThreadDemo5(Counter3 counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i <60; i++) {
            counter.increment();
        }
    }
}

public class task008 {
    public static void main(String[] args) {
        Counter3 counter = new Counter3();
        ThreadDemo5 t1 = new ThreadDemo5(counter);
        ThreadDemo5 t2 = new ThreadDemo5(counter);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + counter.getCount());
    }
}

