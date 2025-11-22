import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserLoginDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utils.Constants.BASE_API_URL;

@Tag("API")
public class HomoDevelopusTests {

    @BeforeAll
    static void setUpClass() {
        authentication = RestAssured.preemptive().basic("test91", "oiwSMUmQo9uuuyc9KITwH");
    }

    @Test
    void getUserLoginTest() throws JsonProcessingException {
        var userLoginDto = new UserLoginDto("nikit1534@gmail.com", "Wq5MPEmi83A5");
        var userLoginRequestBody = new ObjectMapper().writeValueAsString(userLoginDto);

        given()
                .contentType(ContentType.JSON)
                .body(userLoginRequestBody)
        .when()
                .post(BASE_API_URL + "/tester/login")
        .then()
                .statusCode(201)
                .body("accessToken",
                        allOf(notNullValue(),
                        instanceOf(String.class),
                        not(emptyString())));
    }
}
