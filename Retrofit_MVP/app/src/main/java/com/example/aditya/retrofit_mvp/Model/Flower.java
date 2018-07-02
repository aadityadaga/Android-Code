package com.example.aditya.retrofit_mvp.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Flower implements Serializable{
    private int productId;
    private String name;
    private String category;
    private String instructions;
    private double price;
    private String photo;
    private Bitmap bitmap;

    public Flower(int productId, String name, String category, String instructions, double price, String photo, Bitmap bitmap) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.price = price;
        this.photo = photo;
        this.bitmap = bitmap;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
