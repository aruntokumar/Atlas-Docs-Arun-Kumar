/*Write a program to create a class named Customer
        Call the customer class in Task008 class using an object.
 */
package day1_java;

public class task008 {
    public static void main(String[] args) {
        Customer cobj = new Customer();

        cobj.accept();

        cobj.display();
    }
}
class Customer {

    void accept() {
        System.out.println("accept customer called");
    }

    void display() {
        System.out.println("display customer called");
    }
}