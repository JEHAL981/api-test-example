package api.postcodes.io;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AddressFinderTest {

    @Test
    public void ShouldRetrievePostcode() {
        given().
                when().
                get("http://api.postcodes.io/postcodes/SW1A 1AA").
                then().
                statusCode(200).
                assertThat().
                body("result.postcode", equalTo("SW1A 1AA"));


    }

    @Test
    public void ShouldReturnInvalidMessageWhenNoPostCodeIsProvided() {
        given().
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
        given().
                when().
                get("http://api.postcodes.io/postcodes/123AB").
                then().
                statusCode(404).
                assertThat().
                body("error", equalTo("Invalid postcode"));
    }
}
