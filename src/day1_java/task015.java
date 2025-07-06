package day1_java;

public class task015
{
    public static void main(String[] args) {
        String str1 = "Java Strings ";
        String str2 = str1;
        String str3 = "are easy to learn ";
        char[] ch = { 'S', 't', 'r', 'i', 'n', 'g' };
        String str4 = new String(ch);

        System.out.println(str1 + "\n" + str2 + "\n" + str3 + "\n" + str4);
    }
}