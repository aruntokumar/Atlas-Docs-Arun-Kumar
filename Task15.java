package StringHandling;

public class Demo01 {
    public static void main(String[] args) {
        String str1 = "Java Strings ";             	// String literal
        String str2 = new String(str1);           	// Using new keyword (creates a new object)
        String str3 = new String("are easy to learn ");
        char ch[] = {'S', 't', 'r' ,'i', 'n', 'g'};	// Character array
        String str4 = new String(ch);             	// Convert char array to string

        System.out.println(str1 + "\n" + str2 + "\n" + str3 + "\n" + str4);
    }
}