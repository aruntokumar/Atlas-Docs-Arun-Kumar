package day2_java;

public class task013 {

    void add(char x, char y) {
        System.out.println("x: " + x + ", y: " + y);
    }

    void add(int x, int y) {
        System.out.println("x: " + x + ", y: " + y);
    }

    public static void main(String[] args) {
        task013 task = new task013();
        task.add('d', 'a');
        task.add(100, 100);
    }


}