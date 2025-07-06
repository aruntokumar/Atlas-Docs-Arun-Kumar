package day4_java;

class RunnableDemo implements Runnable{
    private Thread t;
    private String threadName;
    RunnableDemo ( String name){
        threadName = name;
        System.out.println("Creating " + threadName );
    }
    public void run() {
        System.out.println("Running " + threadName );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        }
        System.out.println("Thread " + threadName + " exiting.");
    }
    public void Thstart () {
        System.out.println("Starting " + threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
public class task001 {
    public static void main(String args[]) {
        RunnableDemo T1 = new RunnableDemo ( "Thread-1");
        T1.Thstart();
        RunnableDemo T2 = new RunnableDemo ( "Thread-2");
        T2.Thstart();
    }
}