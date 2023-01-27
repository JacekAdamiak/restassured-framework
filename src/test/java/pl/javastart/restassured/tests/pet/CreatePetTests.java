package pl.javastart.restassured.tests.pet;

import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.rop.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.main.test.data.PetTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreatePetTests extends SuiteTestBase {

    private Pet actualPet;

    @Issue("DEFECT-1")
    @TmsLink("ID-1")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to create pet and check if returned Pet object is the same")
    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {

        PetTestDataGenerator petTestDataGenerator = new PetTestDataGenerator();
        Pet pet = petTestDataGenerator.generatePet();

        actualPet = new CreatePetEndpoint()
                .setPet(pet)
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();


//        actualPet = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
//                .body(pet)
//                .when().post("pet")
//                .then().statusCode(HttpStatus.SC_OK).extract().as(Pet.class);

//        assertEquals(actualPet.getId(), pet.getId(), "Pet id");
//        assertEquals(actualPet.getName(), pet.getName(), "Pet name");

//        pet.setName("Diego");
        Assertions.assertThat(actualPet).describedAs("Send Pet was different than received by API")
                .usingRecursiveComparison().isEqualTo(pet);
    }

    @AfterMethod
    public void cleanUpAfterTest() {

        ApiResponse apiResponse = new DeletePetEndpoint()
                .setPetId(actualPet.getId())
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

//        ApiResponse apiResponse = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
//                .when().delete("pet/{petId}", actualPet.getId())
//                .then().statusCode(HttpStatus.SC_OK).extract().as(ApiResponse.class);

//        assertEquals(apiResponse.getCode(), Integer.valueOf(200), "Code");
//        assertEquals(apiResponse.getType(), "unknown", "Type");
//        assertEquals(apiResponse.getMessage(), actualPet.getId().toString(), "Message");

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(actualPet.getId().toString());

        Assertions.assertThat(apiResponse).describedAs("API Response from system was not as expected")
                .usingRecursiveComparison().isEqualTo(expectedApiResponse);


    }

}
