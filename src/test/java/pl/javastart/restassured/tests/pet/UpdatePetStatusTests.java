package pl.javastart.restassured.tests.pet;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.*;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.rop.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.main.rop.GetPetEndpoint;
import pl.javastart.restassured.main.rop.UpdatePetNameStatusEndpoint;
import pl.javastart.restassured.main.test.data.PetTestDataGenerator;
import pl.javastart.restassured.main.test.data.pet.PetStatus;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdatePetStatusTests extends SuiteTestBase {

    private Pet petBeforeUpdate;

    @BeforeMethod
    public void beforeMethod() {
        Pet pet = new PetTestDataGenerator().generatePet();

        petBeforeUpdate = new CreatePetEndpoint()
                .setPet(pet)
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        Assertions.assertThat(petBeforeUpdate).describedAs("Send Pet was different than received by API")
                .usingRecursiveComparison().isEqualTo(pet);
    }

    @Issue("DEFECT-6")
    @TmsLink("ID-6")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to update pet status and check if pet status was updated")
    @Test(dataProvider = "petStatusAndName")
    public void givenPetWhenPetGetsUpdatedNameOrStatusThenPetIsUpdatedTest(PetStatus petStatus) {

        ApiResponse apiResponse = new UpdatePetNameStatusEndpoint()
                .setPetId(petBeforeUpdate.getId())
                .setPetStatus(petStatus.getDescription())
                .setPetName(petBeforeUpdate.getName())
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        Pet petAfterUpdate = new GetPetEndpoint()
                .setPetId(petBeforeUpdate.getId())
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        petBeforeUpdate.setStatus(petStatus.getDescription());

        Assertions.assertThat(petAfterUpdate.getStatus()).describedAs("Pet status")
                .isEqualTo(petStatus.getDescription());
    }

    @AfterMethod
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


    @DataProvider
    public Object[][] petStatusAndName() {
        return  new Object[][] {
                {PetStatus.AVAILABLE},
                {PetStatus.PENDING},
                {PetStatus.SOLD}
        };
    }


}
