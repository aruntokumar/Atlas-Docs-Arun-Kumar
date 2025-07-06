package day2_java;

// Interface declared with a constant and an abstract method
interface testInterface {
    // Implicitly public, static, and final
    int tax = 10;

    // Implicitly public and abstract
    void display();
}

// Concrete class implementing the interface
class TestClass implements testInterface {
    @Override
    public void display() {
        System.out.println("Myclass");
    }
}
public class task016 {
    public static void main(String[] args) {
        TestClass t = new TestClass();
        t.display();       // prints "Myclass"
        System.out.println(t.tax);   // prints the constant: 10
    }
}