import java.util.Scanner;

public class LoginValidation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String correctId = "Arun";
        String correctPwd = "123456";

        String loginId = "";
        String password = "";

        while (true) {
            System.out.print("Arun: ");
            loginId = sc.nextLine();

            System.out.print("123456: ");
            password = sc.nextLine();

            if (loginId.equals(correctId) && password.equals(correctPwd)) {
                System.out.println("Login successful!");
                break;
            } else {
                System.out.println("Invalid login ID or password. Please try again.\n");
            }
        }
        sc.close();
    }
}
