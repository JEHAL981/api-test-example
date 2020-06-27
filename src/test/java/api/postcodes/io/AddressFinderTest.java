package api.postcodes.io;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AddressFinderTest {

    public AddressFinderTest() {
        RestAssured.baseURI = URLConfiguration.OPEN_URL_POSTCODE;
    }

    @Test
    public void ShouldRetrievePostcode() {
        ValidatableResponse response =
                given().
                        contentType("application/json").
                        when().
                        get("postcodes/SW1A 1AA").
                        then().
                        statusCode(200).
                        assertThat().
                        body("result.postcode", equalTo("SW1A 1AA"));


    }

    @Test
    public void ShouldReturnInvalidMessageWhenNoPostCodeIsProvided() {
        ValidatableResponse response =
                given().
                        contentType("application/json").
                        when().
                        get("http://api.postcodes.io/postcodes/").
                        then().
                        statusCode(400).
                        assertThat().
                        body("error", equalTo("No postcode query submitted." +
                                " Remember to include query parameter"));
    }

    @Test
    public void ShouldReturnInvalidPostcodeErrorMessage() {
        ValidatableResponse response =
                given().
                        contentType("application/json").
                        when().
                        get("http://api.postcodes.io/postcodes/123AB").
                        then().
                        statusCode(404).
                        assertThat().
                        body("error", equalTo("Invalid postcode"));


    }
}
