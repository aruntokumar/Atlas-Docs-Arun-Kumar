import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.print("Id: ");
        String id = input.nextLine();

        System.out.print("Pwd: ");
        String pwd = input.nextLine();


        System.out.println("You entered:");
        System.out.println("Id: " + id);
        System.out.println("Pwd: " + pwd);

        input.close();
    }
}
