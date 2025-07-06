package day1_java;

public class task018{
    public static void main(String[] args) {
        task017 myObj = new task017();    // Instantiate a new Person object
        myObj.setName("John");          // Use the setter to assign a name
        System.out.println(myObj.getName());  // Use the getter to retrieve and print the name
    }
}