package day3_java;

// A Class that represents user-defined exception
class MyException extends Exception { // exception is a predefined class – parent class for MyException
    public MyException(String m) {
//inr age ====> scan in put ===> if clock if age <0 ==? Throw an exception
        super(m); // calling the parent class constructor with parameters
    }
}

// A Class that uses the above MyException
public class task008{
    public static void main(String args[]) {
        try {

            // Throw an object of user-defined exception
            throw new MyException("This is a custom exception");
        }
        catch (MyException ex) {
            System.out.println("Caught");
            System.out.println(ex.getMessage());
        }
    }
}