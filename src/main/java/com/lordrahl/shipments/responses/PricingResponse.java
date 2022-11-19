package com.lordrahl.shipments.responses;

public class PricingResponse {
    private double weight;
    private double price;
    private String origin;
    private String destination;
    private String category;


    public PricingResponse(double weight, double price, String origin, String destination, String category) {
        this.weight = weight;
        this.price = price;
        this.origin = origin;
        this.destination = destination;
        this.category = category;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PricingResponse{" +
                "weight=" + weight +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
