package day5_java;

@FunctionalInterface
interface MyInterface1 {
    String reverse(String n);
}

public class task011{
    public static void main(String[] args) {
        MyInterface1 ref = (str) -> {
            String result = "";
            for (int i = str.length() - 1; i >= 0; i--)
                result += str.charAt(i);
            return result;
        };

        System.out.println("Lambda reversed = " + ref.reverse("Lambda"));
    }
}