package day4_java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

class Resource {
    private final Lock lock = new ReentrantLock();
    private final String name;

    public Resource(String name) {
        this.name = name;
    }

    public boolean tryLock() {
        try {
            return lock.tryLock(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void unlock() {
        lock.unlock();
    }

    public void use(Resource other) {
        if (this.tryLock()) {
            System.out.println(Thread.currentThread().getName() + " locked " + this.name);
            try {
                Thread.sleep(50); // simulate some work

                if (other.tryLock()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " locked " + other.name);
                        System.out.println(Thread.currentThread().getName() + " is using " + this.name + " and " + other.name);
                    } finally {
                        other.unlock();
                        System.out.println(Thread.currentThread().getName() + " released " + other.name);
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " could not lock " + other.name);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                this.unlock();
                System.out.println(Thread.currentThread().getName() + " released " + this.name);
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " could not lock " + this.name);
        }
    }
}

public class task008 {
    public static void main(String[] args) {
        Resource r1 = new Resource("Resource-1");
        Resource r2 = new Resource("Resource-2");

        Thread t1 = new Thread(() -> r1.use(r2), "Thread-1");
        Thread t2 = new Thread(() -> r2.use(r1), "Thread-2");

        t1.start();
        t2.start();
    }
}