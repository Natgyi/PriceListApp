package com.example.pricelistapp;

public class PriceItem {
    private int id;
    private String itemName;
    private double price;
    private String category;

    public PriceItem(int id, String itemName, double price, String category) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.category = category;
    }

    public int getId() { return id; }
    public String getItemName() { return itemName; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}
