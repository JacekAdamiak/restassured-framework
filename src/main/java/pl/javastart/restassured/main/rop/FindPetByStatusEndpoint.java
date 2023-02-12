package pl.javastart.restassured.main.rop;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.test.data.pet.PetStatus;

import java.lang.reflect.Type;
import java.math.BigInteger;

import static io.restassured.RestAssured.given;

public class FindPetByStatusEndpoint extends BaseEndpoint<FindPetByStatusEndpoint, Pet[]>{

    private PetStatus petStatus;

    @Override
    protected Type getModelType() {
        return Pet[].class;
    }

    @Step("Get Pets by status")
    @Override
    public FindPetByStatusEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .queryParam("status", petStatus.getDescription())
                .when().get("pet/findByStatus");
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public FindPetByStatusEndpoint setPetStatus(PetStatus petStatus) {
        this.petStatus = petStatus;
        return this;
    }
}
