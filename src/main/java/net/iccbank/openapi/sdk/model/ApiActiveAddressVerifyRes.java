package net.iccbank.openapi.sdk.model;

import java.io.Serializable;

public class ApiActiveAddressVerifyRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean verify;

    public ApiActiveAddressVerifyRes() {
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }
}
