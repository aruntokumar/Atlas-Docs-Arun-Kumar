class task003 implements Runnable {
    private Thread t;
    private String threadName;
    task003( String name){
        threadName = name;
        System.out.println("Creating " + threadName );
    }
    public void run() {
        System.out.println("Running " + threadName );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
// Let the thread sleep for a while.
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        }
        System.out.println("Thread " + threadName + " exiting.");
    }
    public void start ()
    {
        System.out.println("Starting " + threadName );
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
public class TestThread {
    public static void main(String args[]) {
        task003 R1 = new task003( "Thread-1");
        R1.start();
        task003 R2 = new task003( "Thread-2");
        R2.start();
    }
}