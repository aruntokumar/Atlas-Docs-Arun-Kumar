/*Wap to display numbers from 10 to 1 .. skip 7 and 5.*/
package day1_java;

public class task013 {
    public static void main(String[] args){
        for(int i=10;i>0;i--){
            if(i==7||i==5){
                continue;
            }
            System.out.println(i);
        }
    }
}