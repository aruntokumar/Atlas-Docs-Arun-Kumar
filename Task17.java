public class person {

    private String name;

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;

    }
    public static void main(String[] args) {
        person s = new person();

        s.setName("Arun");

        System.out.println("Name: " + s.getName());
    }
}
