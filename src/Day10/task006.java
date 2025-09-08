class Counter1 {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}


class ThreadDemo3 extends Thread {
    Counter1 counter;

    ThreadDemo3(Counter1 counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i <20; i++) {
            counter.increment();
        }
    }
}

public class task006 {
    public static void main(String[] args) {
        Counter1 counter = new Counter1();
        ThreadDemo3 t1 = new ThreadDemo3(counter);
        ThreadDemo3 t2 = new ThreadDemo3(counter);

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

