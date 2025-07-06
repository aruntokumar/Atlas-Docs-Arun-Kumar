package day2_java;
// File: AbstractDemo.java

public class AbstractDemo {

    public static void main(String[] args) {
        // You cannot instantiate an abstract class directly
        Employee e = new SalaryEmployee("George W.", "Houston, TX", 43, 75000.0);
        System.out.println("\nCall mailCheck using Employee reference:");
        e.mailCheck();
    }
}

// Abstract base class
abstract class Employee {
    private String name;
    private String address;
    private int number;

    public Employee(String name, String address, int number) {
        System.out.println("Constructing an Employee");
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public abstract double computePay();  // Abstract method

    public void mailCheck() {
        System.out.println("Mailing a check to " + this.name + " at " + this.address);
    }

    public String toString() {
        return name + " " + address + " " + number;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String newAddress) {
        address = newAddress;
    }

    public int getNumber() {
        return number;
    }
}

// Concrete subclass
class SalaryEmployee extends Employee {
    private double salary;

    public SalaryEmployee(String name, String address, int number, double salary) {
        super(name, address, number);
        this.salary = salary;
    }

    @Override
    public double computePay() {
        System.out.println("Computing salary pay for " + getName());
        return salary / 12;  // Monthly pay
    }

    @Override
    public void mailCheck() {
        System.out.println("Within mailCheck of SalaryEmployee class.");
        System.out.println("Mailing check to " + getName() + " with salary: $" + salary);
    }
}