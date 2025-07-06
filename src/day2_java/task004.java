package day2_java;
public class task004 {
    public static void main(String[] args) {
        // Declare and initialize the array of Student objects
        Student[] myStudents = new Student[] {
                new Student("Dharma"),
                new Student("Sanvi"),
                new Student("Rupa"),
                new Student("Ajay")
        };

        // Iterate through the array and print each student
        for (Student m : myStudents) {
            System.out.println(m);
        }
    }
}