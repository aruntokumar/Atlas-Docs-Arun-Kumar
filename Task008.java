class customer {
    void accept() {
        System.out.println("accept customer");
    }
}
public class New {
    public static void main(String[] args) {
        customer custome = new customer();
        custome.accept();
    }
}
