package com.company;

public class TabletInfo {

    private final String name;
    private final int memorySize;
    private final int rating;
    private final float price;

    public TabletInfo(String name, int memorySize, int rating, float price) {
        this.name = name;
        this.memorySize = memorySize;
        this.rating = rating;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public int getRating() {
        return rating;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Модель планшета: %s\nКоличество памяти: %s\nРейтинг: %s\nЦена: %s\n", name, memorySize, rating, price);
    }
}
