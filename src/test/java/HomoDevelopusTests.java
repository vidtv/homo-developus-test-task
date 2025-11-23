import dto.PlayerResponseDTO;
import dto.UserLoginDTO;
import factory.PlayerRequestFactory;
import io.qameta.allure.Description;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Constants.BASE_API_URL;

@Tag("API")
@Tag("HomoDevelopus")
public class HomoDevelopusTests {

    @BeforeAll
    static void setUpClass() {
        baseURI = BASE_API_URL;
        var userLoginDto = new UserLoginDTO("nikit1534@gmail.com", "Wq5MPEmi83A5");

        var userBearerToken =
                given()
                    .contentType(ContentType.JSON)
                    .body(userLoginDto)
                .when()
                    .post("/tester/login")
                .then()
                    .statusCode(201)
                    .body("accessToken",
                        allOf(notNullValue(),
                                instanceOf(String.class),
                                not(emptyString())))
                    .extract()
                    .path("accessToken")
                    .toString();

        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_API_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + userBearerToken)
                .build();
    }

    @Test
    @DisplayName("Registration of new users, sorting users list and deleting created users")
    @Description("Verify that 12 users are created, each user matches specification " +
            "and then all previously created users are deleted correctly")
    void usersRegistrationAndDeletionTest() {
        var newPlayersQuantity = 12;
        var playersList = PlayerRequestFactory.createPlayersList(newPlayersQuantity);
        var playersIdList = new ArrayList<String>();

        step("1. Create " + newPlayersQuantity + " new users and verify that response matches specification", () -> {
            playersList.forEach(player -> {
                var playerId =
                        given()
                            .body(player)
                            .when()
                            .post("/automationTask/create")
                            .then()
                            .statusCode(201)
                            .body(matchesJsonSchemaInClasspath("schemas/created_user.json"))
                            .extract()
                            .jsonPath()
                            .getString("_id");

                playersIdList.add(playerId);
            });
        });

        step("2. Retrieve one of users by its email and verify that response matches specification", () -> {
            var userEmailToRequest = playersList.get(0).email;

            given()
                    .body("{\"email\": \"" + userEmailToRequest + "\"}")
                    .when()
                    .post("/automationTask/getOne")
                    .then()
                    .statusCode(201)
                    .body(matchesJsonSchemaInClasspath("schemas/existing_user.json"));
        });

        step("3. Retrieve list of all created users and sort them by their 'name' field values", () -> {
            var allUsersList =
                    given()
                            .when()
                            .get("/automationTask/getAll")
                            .then()
                            .statusCode(200)
                            .extract()
                            .as(new TypeRef<List<PlayerResponseDTO>>() {});

            var allUsersSortedList = allUsersList
                    .stream()
                    .sorted(Comparator.comparing(PlayerResponseDTO::getName))
                    .toList();
        });

        step("4. Delete each created user and verify that the list of players created by the current user is empty", () -> {
            playersIdList.forEach(id ->
                    given()
                        .when()
                        .delete("/automationTask/deleteOne/" + id)
                        .then()
                        .statusCode(200)
            );

            var getAllUsersList =
                    given()
                        .when()
                        .get("/automationTask/getAll")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(new TypeRef<List<PlayerResponseDTO>>() {});

            assertTrue(getAllUsersList.isEmpty(), "There are no users created by a current user");
        });
    }
}
