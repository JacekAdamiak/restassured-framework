package pl.javastart.restassured.main.rop;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.test.data.pet.PetStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class UpdatePetNameStatusEndpoint extends BaseEndpoint<UpdatePetNameStatusEndpoint, ApiResponse> {

    private Integer petId;
    private String petName;
    private String petStatus;

    @Override
    protected Type getModelType() {
        return ApiResponse.class;
    }

    @Step("Update Pet name and status")
    @Override
    public UpdatePetNameStatusEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification().contentType(ContentType.URLENC))
                .when()
                .formParam("name", petName)
                .formParam("status", petStatus)
                .post("pet/{petId}", petId);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public UpdatePetNameStatusEndpoint setPetId(Integer petId) {
        this.petId = petId;
        return this;
    }

    public UpdatePetNameStatusEndpoint setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public UpdatePetNameStatusEndpoint setPetStatus(String petStatus) {
        this.petStatus = petStatus;
        return this;
    }

}
