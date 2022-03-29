package com.epam.cdp.bdd.wstesting.model.heirs.requests.heirs;

import com.epam.cdp.bdd.wstesting.model.heirs.requests.RqObject;

public class DeleteRequestObject extends RqObject {
    public DeleteRequestObject(String rqName) {
        super(rqName);
    }

    public void createRequestForDeletingUser(String userId) {
        setBaseUri("https://gorest.co.in/public/v2/users/" + userId);
        setCommonParams();
    }
}
