package day4_java;

// MyRunnable.java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Code executed in a new thread via Runnable.");
    }
}

// MyThread.java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Code executed in a new thread via Thread extension.");
    }
}

// Main.java
public class task013{
    public static void main(String[] args) {
        // Using Runnable interface
        Thread thread1 = new Thread(new MyRunnable());
        thread1.start();

        // Using Thread class extension
        MyThread thread2 = new MyThread();
        thread2.start();
    }
}