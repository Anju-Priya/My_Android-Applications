package com.gtechyderabad.gtec;

public class Client {
    private String name;
    private String quote;
    private int imageResourceId;

    public Client(String name, String quote, int imageResourceId) {
        this.name = name;
        this.quote = quote;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getQuote() {
        return quote;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
