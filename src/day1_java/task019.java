package day1_java;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Element;

public class task019 {
    public static void main(String[] args) {
        System.out.println("Find by label 'Helium': " + Element.LOAD(Integer.parseInt("Helium")));
        System.out.println("Find by atomic number 10: " + Element.isNumber());
        System.out.println("Find by atomic weight 1.008f: " + Element.toString());
    }
}
