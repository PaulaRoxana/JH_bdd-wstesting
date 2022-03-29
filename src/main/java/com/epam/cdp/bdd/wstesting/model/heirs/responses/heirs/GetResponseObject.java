package com.epam.cdp.bdd.wstesting.model.heirs.responses.heirs;

import com.epam.cdp.bdd.wstesting.model.heirs.responses.RsObject;
import io.restassured.response.Response;

import java.util.List;

public class GetResponseObject extends RsObject {
    private static final String RESULT_LOCATOR = "data";
    private static final String RESULT_NAME_LOCATOR = "name";

    public GetResponseObject(String rsName, Response response) {
        super(rsName, response);
    }

    public List<String> getAllUsers() {
        return response.jsonPath().getList(RESULT_LOCATOR);
    }

    public String getUserName() {
        return response.jsonPath().get(RESULT_NAME_LOCATOR);
    }
}
