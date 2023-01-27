package pl.javastart.restassured.main.test.data.pet;

public enum PetsTags {

    YOUNG_PET(1, "young-pet"), ADULT_PET(2, "adult-pet");

    private final int value;
    private final String description;

    PetsTags(int value, String description) {
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
