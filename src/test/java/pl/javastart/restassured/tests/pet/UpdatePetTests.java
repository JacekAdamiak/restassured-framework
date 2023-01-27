package pl.javastart.restassured.tests.pet;

import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.rop.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.main.rop.GetPetEndpoint;
import pl.javastart.restassured.main.rop.UpdatePetEndpoint;
import pl.javastart.restassured.main.test.data.PetTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class UpdatePetTests extends SuiteTestBase {


    private Pet petBeforeUpdate;

    @BeforeTest
    public void beforeTest() {

        Pet pet = new PetTestDataGenerator().generatePet();

        petBeforeUpdate = new CreatePetEndpoint()
                .setPet(pet)
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        Assertions.assertThat(petBeforeUpdate).describedAs("Send Pet was different than received by API")
                .usingRecursiveComparison().isEqualTo(pet);

    }

    @Issue("DEFECT-5")
    @TmsLink("ID-5")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to update pet and check if it was updated")
    @Test
    public void givenPetWhenPetGetsUpdatedThenPetIsUpdatedTest() {

        Pet newPet = new PetTestDataGenerator().generatePet();
        newPet.setId(petBeforeUpdate.getId());

        Pet updatedPet = new UpdatePetEndpoint()
                .setPet(newPet)
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        Assertions.assertThat(updatedPet).describedAs("Updated pet was not the as same as update")
                .usingRecursiveComparison().isEqualTo(newPet);
        Assertions.assertThat(updatedPet).describedAs("Updated pet was the same as create pet")
                .usingRecursiveComparison().isNotEqualTo(petBeforeUpdate);


        Pet petAfterUpdate = new GetPetEndpoint()
                .setPetId(petBeforeUpdate.getId())
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        Assertions.assertThat(petAfterUpdate).describedAs("Updated pet was not the as same as update")
                .usingRecursiveComparison().isEqualTo(newPet);
        Assertions.assertThat(petAfterUpdate).describedAs("Updated pet was the same as create pet")
                .usingRecursiveComparison().isNotEqualTo(petBeforeUpdate);
    }

    @AfterTest
    public void cleanUpAfterTest() {

        ApiResponse apiResponse = new DeletePetEndpoint()
                .setPetId(petBeforeUpdate.getId())
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(petBeforeUpdate.getId().toString());

        Assertions.assertThat(apiResponse).describedAs("API Response from system was not as expected")
                .usingRecursiveComparison().isEqualTo(expectedApiResponse);

    }


}
