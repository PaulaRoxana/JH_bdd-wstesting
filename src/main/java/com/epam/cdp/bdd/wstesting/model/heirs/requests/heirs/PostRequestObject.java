package com.epam.cdp.bdd.wstesting.model.heirs.requests.heirs;

import com.epam.cdp.bdd.wstesting.model.heirs.requests.RqObject;
import org.json.JSONException;
import org.json.JSONObject;

public class PostRequestObject extends RqObject {
    public PostRequestObject(String rqName) {
        super(rqName);
    }

    public void createRequestForCreatingUser(String email, String name, String status, String gender) throws JSONException {
        setBaseUri();
        setCommonParams();
        requestSpecification.body(createUserAsJsonObject(email, name, status, gender).toString());
    }

    private JSONObject createUserAsJsonObject(String email, String name, String status, String gender) throws JSONException {
        JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);
            requestBody.put("name", name);
            requestBody.put("status", status);
            requestBody.put("gender", gender);
        return requestBody;
    }
}
