class ThreadDemo1 extends Thread {
    public ThreadDemo1(String name) {
        super(name);
        System.out.println("Creating " + getName());
    }

    @Override
    public void run() {
        System.out.println("Running " + getName());
        for (int i = 4; i > 0; i--) {
            System.out.println("Thread: " + getName() + ", " + i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Thread " + getName() + " interrupted.");
            }
        }
        System.out.println("Thread " + getName() + " exiting.");
    }
}

public class task004_1 {
    public static void main(String[] args) {
        ThreadDemo t1 = new ThreadDemo("Thread-1");
        t1.start();

        ThreadDemo t2 = new ThreadDemo("Thread-2");
        t2.start();
    }
}