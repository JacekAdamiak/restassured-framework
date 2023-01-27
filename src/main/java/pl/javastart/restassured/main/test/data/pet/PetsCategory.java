package pl.javastart.restassured.main.test.data.pet;

import java.util.Random;

public enum PetsCategory {

    DOGS(1, "dogs"), CATS(2, "cats"), OTHER(3, "other");

    private final int value;
    private final String description;

    PetsCategory(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }
    public String getDescription() {
        return description;
    }

}
