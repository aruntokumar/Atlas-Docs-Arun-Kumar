package PracticeSet.atlaslearnings.day24.Task01;

public interface LaptopBuilder {
    LaptopBuilder buildMemory(int memory);
    LaptopBuilder buildStorage(int storage);
    Laptop build();
}