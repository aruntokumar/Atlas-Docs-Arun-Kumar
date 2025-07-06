package day4_java;

// MyRunnable.java
class MyRunnable1 implements Runnable {
    @Override
    public void run() {
        System.out.println("Code executed in a new thread via Runnable.");
    }
}

// MyThread.java
class MyThread1 extends Thread {
    @Override
    public void run() {
        System.out.println("Code executed in a new thread via Thread extension.");
    }
}

// task19.java
public class task014 {
    public static void main(String[] args) {
        MyRunnable runnableInstance = new MyRunnable();     // Runnable instance
        MyThread threadInstance = new MyThread();           // Thread subclass instance

        Thread t1 = new Thread(runnableInstance);           // Wrap Runnable in a Thread

        t1.start();             // Starts new thread from Runnable
        threadInstance.start(); // Starts new thread from Thread subclass
    }
}