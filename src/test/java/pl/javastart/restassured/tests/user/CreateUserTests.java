package pl.javastart.restassured.tests.user;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.user.User;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.rop.CreateUserEndpoint;
import pl.javastart.restassured.main.rop.DeleteUserEndpoint;
import pl.javastart.restassured.main.test.data.UserTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreateUserTests extends SuiteTestBase {

    private User user;

    @TmsLink("ID-3")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to create new user")
    @Test
    public void givenUserWhenPostUserThenUserIsCreatedTest() {

        UserTestDataGenerator userTestDataGenerator = new UserTestDataGenerator();
        user = userTestDataGenerator.generateUser();

        ApiResponse userResponse = new CreateUserEndpoint()
                .setUser(user)
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();


//        userResponse = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
//                .body(user)
//                .when().post("user")
//                .then().statusCode(HttpStatus.SC_OK).extract().as(ApiResponse.class);

//        assertEquals(userResponse.getCode(), Integer.valueOf(200), "Response code");
//        assertEquals(userResponse.getType(), "unknown", "Response type");
//        assertEquals(userResponse.getMessage(), user.getId().toString(), "Response message with ID");

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(user.getId().toString());


        Assertions.assertThat(userResponse).describedAs("API Response from system was not as expected")
                .usingRecursiveComparison().isEqualTo(expectedApiResponse);

    }

    @AfterMethod
    public void cleanUpAfterTest() {

        ApiResponse apiResponse = new DeleteUserEndpoint()
                .setUsername(user.getUsername())
                .sendRequest()
                .assertRequestSuccess()
                .getResponseModel();

//        ApiResponse apiResponse = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
//                .when().delete("user/{username}", user.getUsername())
//                .then().statusCode(HttpStatus.SC_OK).extract().as(ApiResponse.class);

//        assertEquals(apiResponse.getCode(), Integer.valueOf(200), "Code");
//        assertEquals(apiResponse.getType(), "unknown", "Type");
//        assertEquals(apiResponse.getMessage(), user.getUsername(), "Message");

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(user.getUsername());


        Assertions.assertThat(apiResponse).describedAs("API Response from system was not as expected")
                .usingRecursiveComparison().isEqualTo(expectedApiResponse);


    }


}
