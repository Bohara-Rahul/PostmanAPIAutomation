package com.rest.tests;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Workspace;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WorkspaceTests {

    @Test
    public void get_all_workspaces() {
        given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-6311ffcf495391516b00c07e-945f0f3fd997cc483b1cfea2e2a7d3f2ee").
        when().
            get("/workspaces").
                then().
                    log().all().
                    assertThat().
                    statusCode(200)
                    .body("workspaces.size()", greaterThan(0),
                            "workspaces.id", hasItems(
                                    "e5a9e81c-f241-4fff-b33c-1278d6e41a07",
                                    "ecfd867b-7914-4708-b77c-42a6cc1ed03a",
                                    "3bd024f3-6943-4e90-a60b-a4b639453fc9",
                                    "7e31dec0-24ce-4d49-85f4-ee7ae78113be",
                                    "0260361f-a6ea-45b1-9ab9-786b0d21f967"),
                            "workspaces.name", hasItems("Microservices", "Automated Testing", "My Workspace 1", "Workspace 3", "Workspace 2"),
                            "workspaces.type", hasItems("personal", "personal", "personal", "personal"),
                            "workspaces.visibility", hasItems("personal", "personal", "personal", "personal"),
                            "workspaces[0].id", equalTo("e5a9e81c-f241-4fff-b33c-1278d6e41a07"),
                            "workspaces[0].name", equalTo("Microservices"),
                            "workspaces[0].type", equalTo("personal"),
                            "workspaces[0].visibility", equalTo("personal")
                    );

    }

    @Test
    public void get_single_workspace() {
        Workspace expectedWorkspace = new Workspace(
                "3bd024f3-6943-4e90-a60b-a4b639453fc9",
                "My Workspace 1",
                "personal",
                "personal"
        );
        String res = given()
                .header("x-api-key", "PMAK-6311ffcf495391516b00c07e-945f0f3fd997cc483b1cfea2e2a7d3f2ee")
                .when()
                    .get("https://api.postman.com/workspaces/3bd024f3-6943-4e90-a60b-a4b639453fc9")
                    .then()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .response()
                        .asString();

        assertThat(JsonPath.from(res).getString("workspace.id"), equalTo(expectedWorkspace.getId()));
        assertThat(JsonPath.from(res).getString("workspace.name"), equalTo(expectedWorkspace.getName()));
        assertThat(JsonPath.from(res).getString("workspace.type"), equalTo(expectedWorkspace.getType()));
        assertThat(JsonPath.from(res).getString("workspace.visibility"), equalTo(expectedWorkspace.getVisibility()));
    }

    @Test
    public void create_workspace() {
        String jsonBody = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"3bd024f3-6943-4e90-a60b-a4b639453fc9\",\n" +
                "        \"name\": \"Workspace 3\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is your personal, private workspace to play around in. Only you can see the collections and APIs you create here - unless you share them with your team.\",\n" +
                "        \"visibility\": \"personal\"\n" +
                "    }\n" +
                "}";

        String res = given()
                .header("x-api-key", "PMAK-6311ffcf495391516b00c07e-945f0f3fd997cc483b1cfea2e2a7d3f2ee")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                    .post("https://api.postman.com/workspaces")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .response()
                    .asString();

        assertThat(JsonPath.from(res).getString("workspace.name"), equalTo("Workspace 3"));

    }

    @Test
    public void update_workspace() {
        String jsonBody = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"Workspace 2\"\n" +
                "    }\n" +
                "}";

        String res = given()
                .header("x-api-key", "PMAK-6311ffcf495391516b00c07e-945f0f3fd997cc483b1cfea2e2a7d3f2ee")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                    .when()
                        .put("https://api.postman.com/workspaces/0260361f-a6ea-45b1-9ab9-786b0d21f967")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .response()
                        .asString();

        assertThat(JsonPath.from(res).getString("workspace.id"), equalTo("0260361f-a6ea-45b1-9ab9-786b0d21f967"));
        assertThat(JsonPath.from(res).getString("workspace.name"), equalTo("Workspace 2"));
    }

    @Test
    public void delete_workspace() {
        Workspace workspace = new Workspace("7f48eeb7-a901-4bee-88fd-0761e91369b3");

        String res = given()
                .header("x-api-key", "PMAK-6311ffcf495391516b00c07e-945f0f3fd997cc483b1cfea2e2a7d3f2ee")
                .when()
                    .delete("https://api.postman.com/workspaces/"+workspace.getId())
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .response()
                    .asString();

        assertThat(JsonPath.from(res).getString("workspace.id"), equalTo(workspace.getId()));
    }
}
