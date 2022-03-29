package com.epam.cdp.bdd.wstesting.stepdef;

import com.epam.cdp.bdd.wstesting.core.store.RxStore;
import com.epam.cdp.bdd.wstesting.model.heirs.requests.RqObject;
import com.epam.cdp.bdd.wstesting.model.heirs.requests.heirs.DeleteRequestObject;
import com.epam.cdp.bdd.wstesting.model.heirs.requests.heirs.GetRequestObject;
import com.epam.cdp.bdd.wstesting.model.heirs.requests.heirs.PostRequestObject;
import com.epam.cdp.bdd.wstesting.model.heirs.responses.RsObject;
import com.epam.cdp.bdd.wstesting.model.heirs.responses.heirs.DeleteResponseObject;
import com.epam.cdp.bdd.wstesting.model.heirs.responses.heirs.GetResponseObject;
import com.epam.cdp.bdd.wstesting.model.heirs.responses.heirs.PostResponseObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class StepDefinitions {
    private RxStore rxStore = RxStore.getInstance();

    @Given("^user has \"([^\"]*)\" request$")
    public void userHasRequestForGettingAllUsers(String rqName) {
        GetRequestObject getRequestObject = new GetRequestObject(rqName);
        getRequestObject.createRequestForGettingUsers();

        rxStore.putDataIntoStore(getRequestObject.getRxName(), getRequestObject);
    }

    @When("^user sends \"([^\"]*)\" \"([^\"]*)\" request$")
    public void userSendsRequest(String requestMethod, String rqName) {
        RqObject requestObject = (RqObject) rxStore.getDataFromStore(rqName);
        String rsName = rqName.replace("RQ", "RS");
        RsObject receivedResponse = switch (requestMethod) {
            case "GET" -> new GetResponseObject(rsName, requestObject.sendGetRequest());
            case "POST" -> new PostResponseObject(rsName, requestObject.sendPostRequest());
            case "DELETE" -> new DeleteResponseObject(rsName, requestObject.sendDeleteRequest());
            default -> throw new IllegalStateException("Unexpected value: " + requestMethod);
        };

        rxStore.putDataIntoStore(receivedResponse.getRxName(), receivedResponse);
    }

    @Then("^\"([^\"]*)\" code is \"([^\"]*)\"$")
    public void responseStatusCodeEqualsExpected(String rsName, String statusCode) {
        RsObject actualResponse = (RsObject) rxStore.getDataFromStore(rsName);
        Assert.assertEquals(statusCode, String.valueOf(actualResponse.getStatusCode()),
                "Status code doesn't match expected");
    }

    @And("^number of all users equal \"([^\"]*)\" in \"([^\"]*)\"$")
    public void numberOfUsersEquals(String expectedNumberOfUsers, String rsName) {
        GetResponseObject actualResponse = (GetResponseObject) rxStore.getDataFromStore(rsName);
        Assert.assertEquals(expectedNumberOfUsers, String.valueOf(actualResponse.getAllUsers().size()),
                "Actual number of users don't match expected");
    }

    @Given("user has {string} request with id {string}")
    public void userHasRequestForGettingUserById(String rqName, String userId) {
        GetRequestObject getRequestObject = new GetRequestObject(rqName);
        getRequestObject.createRequestForGettingUserById(userId);

        rxStore.putDataIntoStore(getRequestObject.getRxName(), getRequestObject);
    }

    @And("user name contains expected {string}")
    public void usersNameEquals(String expectedUserName) {
        GetResponseObject actualResponse = (GetResponseObject) rxStore.getDataFromStore("getUserByIdRS");
        Assert.assertEquals(actualResponse.getUserName(), expectedUserName, "Found result doesn't contains username");
    }

    @Given("user has {string} request with following parameters")
    public void userHasRequestForCreatingUserWithFollowingParameters(String rqName, DataTable usersInfo) {
        PostRequestObject postRequestObject = new PostRequestObject(rqName);
        String email = usersInfo.cell(1, 0);
        String name = usersInfo.cell(1, 1);
        String status = usersInfo.cell(1, 2);
        String gender = usersInfo.cell(1, 3);
        postRequestObject.createRequestForCreatingUser(email, name, status, gender);

        rxStore.putDataIntoStore(postRequestObject.getRxName(), postRequestObject);
    }

    @And("user has {string} request with id from {string} response")
    public void userHasRequestWithIdFromResponse(String rqName, String rsName) {
        PostResponseObject createUserResponse = (PostResponseObject) rxStore.getDataFromStore(rsName);

        GetRequestObject getRequestObject = new GetRequestObject(rqName);
        getRequestObject.createRequestForGettingUserById(String.valueOf(createUserResponse.getUserId()));

        rxStore.putDataIntoStore(getRequestObject.getRxName(), getRequestObject);
    }

    @Given("user has {string} request by id {string}")
    public void userHasRequestById(String rqName, String userId) {
        DeleteRequestObject deleteRequestObject = new DeleteRequestObject(rqName);
        deleteRequestObject.createRequestForDeletingUser(userId);

        rxStore.putDataIntoStore(deleteRequestObject.getRxName(), deleteRequestObject);
    }
}
