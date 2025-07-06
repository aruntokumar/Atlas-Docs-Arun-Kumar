//swap 2 numbers

package day1_java;

public class task004 {
    public static void main(String[] args){
        int a = 2;
        int b = 5;
        System.out.println("before swap :a :" + a + " b : " + b);
        int temp = a;
        a=b;
        b=temp;
        System.out.println("after swap : a :" + a + " b :" + b);
    }
}