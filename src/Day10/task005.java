class Counter {
    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class ThreadDemo2 extends Thread {
    Counter counter;

    ThreadDemo2(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            counter.increment();
//            System.out.println(i);
        }
    }
}

public class task005 {
    public static void main(String[] args) {
        Counter counter = new Counter();
        ThreadDemo2 t1 = new ThreadDemo2(counter);
        ThreadDemo2 t2 = new ThreadDemo2(counter);

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

