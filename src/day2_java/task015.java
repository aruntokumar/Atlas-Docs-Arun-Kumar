package day2_java;

// Abstract class defining the blueprint for all gadgets
abstract class Gadgets {
    abstract void turnOn();
    abstract void turnOff();
}

// Concrete class: TVRemote
class TVRemote extends Gadgets {
    @Override
    void turnOn() {
        System.out.println("TV is turned ON.");
    }

    @Override
    void turnOff() {
        System.out.println("TV is turned OFF.");
    }
}

// Concrete class: ACRemote
class ACRemote extends Gadgets {
    @Override
    void turnOn() {
        System.out.println("AC is turned ON.");
    }

    @Override
    void turnOff() {
        System.out.println("AC is turned OFF.");
    }
}

// New Concrete class: FanRemote
class FanRemote extends Gadgets {
    @Override
    void turnOn() {
        System.out.println("Fan is turned ON.");
    }

    @Override
    void turnOff() {
        System.out.println("Fan is turned OFF.");
    }
}

// New Concrete class: CoolerRemote
class CoolerRemote extends Gadgets {
    @Override
    void turnOn() {
        System.out.println("Cooler is turned ON.");
    }

    @Override
    void turnOff() {
        System.out.println("Cooler is turned OFF.");
    }
}

// Main class to demonstrate abstraction
public class task015 {
    public static void main(String[] args) {
        Gadgets tv = new TVRemote();
        Gadgets ac = new ACRemote();
        Gadgets fan = new FanRemote();
        Gadgets cooler = new CoolerRemote();

        // Operate each gadget
        tv.turnOn();
        tv.turnOff();

        ac.turnOn();
        ac.turnOff();

        fan.turnOn();
        fan.turnOff();

        cooler.turnOn();
        cooler.turnOff();
    }
}