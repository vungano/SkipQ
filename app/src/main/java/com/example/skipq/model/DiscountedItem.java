package com.example.skipq.model;

public class DiscountedItem {
    private String imageUrl;    //URL of the product image to be displayed in the frontend
    private String productName; //Name of the product
    private Double oldPrice;    //The old price before the discount
    private Double newPrice;    //The new price after the discount

    public DiscountedItem() {
    }

    public DiscountedItem(String imageUrl, String productName, Double oldPrice, Double newPrice) {
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }
}
