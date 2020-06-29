package api.postcodes.io;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AddressFinderTest {

    public AddressFinderTest() {
        RestAssured.baseURI = URLConfiguration.OPEN_URL_POSTCODE;
    }

    @Test
    public void ShouldRetrievePostcode() {
        Response response =
                given().
                        contentType("application/json").
                        get("postcodes/SW1A 1AA");
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().print();
        assertThat(statusCode, is(200));
        assertThat(responseBody, containsString("SW1A 1AA"));


    }

    @Test
    public void ShouldReturnInvalidMessageWhenNoPostCodeIsProvided() {
        Response response =
                given().
                        contentType("application/json").
                        get("http://api.postcodes.io/postcodes/");
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().print();
        assertThat(statusCode, is(400));
        assertThat(responseBody, containsString("No postcode query submitted." +
                " Remember to include query parameter"));
    }

    @Test
    public void ShouldReturnInvalidPostcodeErrorMessage() {
        Response response =
                given().
                        contentType("application/json").
                        get("http://api.postcodes.io/postcodes/123AB");
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().print();
        assertThat(statusCode, is(404));
        assertThat(responseBody, containsString("Invalid postcode"));

    }
}
