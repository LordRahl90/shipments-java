package com.lordrahl.shipments.requests;

public class Enquiry {
    private double size;
    private String origin;
    private String destination;

    public Enquiry(double size, String origin, String destination) {
        this.size = size;
        this.origin = origin;
        this.destination = destination;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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
}


