package pl.javastart.restassured.main.rop;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class GetPetEndpoint extends BaseEndpoint<GetPetEndpoint, Pet>{

    private Integer petId;

    @Override
    protected Type getModelType() {
        return Pet.class;
    }

    @Step("Get pet")
    @Override
    public GetPetEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .when().get("pet/{petId}", petId);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public GetPetEndpoint setPetId(int petId) {
        this.petId = petId;
        return this;
    }

}
