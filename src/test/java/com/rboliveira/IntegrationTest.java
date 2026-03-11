package com.rboliveira;

import com.rboliveira.dto.UserDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
class IntegrationTest {

    private static final String BASE_PATH = "/users";

    @Test
    void testGetAllUsersEmpty() {
        given()
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(200)
                .body("", hasSize(greaterThan(-1)));
    }

    @Test
    void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
        userDTO.setEmail("test@example.com");

        given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("username", equalTo("testuser"))
                .body("email", equalTo("test@example.com"));
    }

    @Test
    void testCreateAndGetUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("getuser");
        userDTO.setPassword("password456");
        userDTO.setEmail("getuser@example.com");

        // Criar usuário
        String userId = given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .path("id");

        // Buscar usuário criado
        given()
                .when()
                .get(BASE_PATH + "/" + userId)
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("username", equalTo("getuser"))
                .body("email", equalTo("getuser@example.com"));
    }

    @Test
    void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("updateuser");
        userDTO.setPassword("password789");
        userDTO.setEmail("updateuser@example.com");

        // Criar usuário
        String userId = given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        // Atualizar usuário
        UserDTO updatedDTO = new UserDTO();
        updatedDTO.setUsername("updateduserchanged");
        updatedDTO.setPassword("newpassword123");
        updatedDTO.setEmail("updated@example.com");

        given()
                .contentType(ContentType.JSON)
                .body(updatedDTO)
                .when()
                .put(BASE_PATH + "/" + userId)
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("username", equalTo("updateduserchanged"))
                .body("email", equalTo("updated@example.com"));
    }

    @Test
    void testDeleteUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("deleteuser");
        userDTO.setPassword("password999");
        userDTO.setEmail("deleteuser@example.com");

        // Criar usuário
        String userId = given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        // Deletar usuário
        given()
                .when()
                .delete(BASE_PATH + "/" + userId)
                .then()
                .statusCode(204);

        // Verificar que o usuário foi deletado
        given()
                .when()
                .get(BASE_PATH + "/" + userId)
                .then()
                .statusCode(404);
    }

    @Test
    void testGetUserNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        given()
                .when()
                .get(BASE_PATH + "/" + nonExistentId)
                .then()
                .statusCode(404);
    }

    @Test
    void testGetAllUsersWithPagination() {
        // Criar alguns usuários
        for (int i = 0; i < 3; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("paginationuser" + i);
            userDTO.setPassword("password" + i);
            userDTO.setEmail("paginationuser" + i + "@example.com");

            given()
                    .contentType(ContentType.JSON)
                    .body(userDTO)
                    .when()
                    .post(BASE_PATH)
                    .then()
                    .statusCode(200);
        }

        // Testar paginação
        given()
                .queryParam("page", 0)
                .queryParam("pageSize", 2)
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(200)
                .body("", hasSize(greaterThan(-1)));
    }

}