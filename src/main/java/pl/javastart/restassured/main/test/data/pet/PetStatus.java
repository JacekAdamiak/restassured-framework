package pl.javastart.restassured.main.test.data.pet;

public enum PetStatus {

    AVAILABLE("available"), PENDING("pending"), SOLD("sold");

    private final String description;

    PetStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
