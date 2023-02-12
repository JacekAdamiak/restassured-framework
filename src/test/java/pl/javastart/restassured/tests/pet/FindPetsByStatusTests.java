package pl.javastart.restassured.tests.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.rop.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.main.rop.FindPetByStatusEndpoint;
import pl.javastart.restassured.main.test.data.PetTestDataGenerator;
import pl.javastart.restassured.main.test.data.pet.PetStatus;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import java.util.ArrayList;
import java.util.List;

public class FindPetsByStatusTests extends SuiteTestBase {

    Pet[] actualListOfPets;
    List<Pet> listOfPets = new ArrayList<>();


    @BeforeMethod
    public void createPets() {
        int numberOfPets = 3;

        for (int i = 0; i < numberOfPets; i++) {
            Pet pet = new PetTestDataGenerator().generatePet();
            pet.setStatus(PetStatus.SOLD.getDescription());
            new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess();
            listOfPets.add(pet);
        }
    }


    @TmsLink("ID-7")
    @Severity(SeverityLevel.NORMAL)
    @Description("The goal of this test is to find pets by status sold")
    @Test
    public void givenPetsWhenPetsPostThenPetsAreCreatedTest() {

        actualListOfPets = new FindPetByStatusEndpoint()
                .setPetStatus(PetStatus.SOLD)
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

        Assertions.assertThat(actualListOfPets).usingRecursiveFieldByFieldElementComparator().containsAll(listOfPets);
    }

    @AfterMethod
    public void cleanUpAfterTest() {
        listOfPets.forEach(pet -> new DeletePetEndpoint()
                .setPetId(pet.getId())
                .sendRequest()
                .assertRequestSuccess());
    }
}
