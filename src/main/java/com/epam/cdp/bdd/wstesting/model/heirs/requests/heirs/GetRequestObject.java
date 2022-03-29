package com.epam.cdp.bdd.wstesting.model.heirs.requests.heirs;

import com.epam.cdp.bdd.wstesting.model.heirs.requests.RqObject;

public class GetRequestObject extends RqObject {
    public GetRequestObject(String rqName) {
        super(rqName);
    }

    public void createRequestForGettingUsers() {
        setBaseUri();
        setCommonParams();
    }

    public void createRequestForGettingUserById(String userId) {
        setBaseUri("https://gorest.co.in/public/v2/users/" + userId);

        setCommonParams();
    }
}
