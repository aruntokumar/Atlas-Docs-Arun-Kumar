package day2_java;

class Superclass{
    int var;
    Superclass(int var){
        this.var = var;
    }
    public void getVar(){
        System.out.println("var value in super class is "+ var);
    }
}

public class task012_1 extends Superclass{
    task012_1(int var) {
        super(var);
    }
    public static void main(String[] args){
        Superclass sobj = new Superclass(100);
        sobj.getVar();

        task012_1 tobj = new task012_1(200);
        tobj.getVar();
    }
}