public class Main {
    public static void main(String[] args) {
        int a = 10;
        int b = 5;

        String result = (a > b) ? "a is greater than b"
                : (a < b) ? "b is greater than a"
                : "a and b are equal";

        System.out.println(result);
    }
}
