package day4_java;

class Test extends Thread{
    public void run(){
        System.out.println("thread started.");
    }
}

public class task012 {
    public static void main(String args[]){

        Test t1 = new Test();
        t1.run();
    }
}