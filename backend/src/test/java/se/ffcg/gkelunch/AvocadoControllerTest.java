package se.ffcg.gkelunch;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AvocadoControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/avocados")
          .then()
             .statusCode(200)
             .body(containsString("Hass"));
    }

}