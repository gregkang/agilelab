package com.ebaby.application;

public enum Category {
    CAR("Car"),
    DOWNLOADABLE_SOFTWARE("Downloadable Software"),
    TOY("Toy");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
