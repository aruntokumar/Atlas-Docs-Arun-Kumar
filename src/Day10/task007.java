class Counter2 {
    private int count = 0;

    public void increment() {
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}



class ThreadDemo4 extends Thread {
    Counter2 counter;

    ThreadDemo4(Counter2 counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i <10; i++) {
            counter.increment();
        }
    }
}

public class task007 {
    public static void main(String[] args) {
        Counter2 counter = new Counter2();
        ThreadDemo4 t1 = new ThreadDemo4(counter);
        ThreadDemo4 t2 = new ThreadDemo4(counter);

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

