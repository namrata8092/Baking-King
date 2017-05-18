package com.nds.baking.king.net.requests;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public interface NetworkRequester {
    void onFailure(Throwable error);

    void onSuccess(Object respObj);

}
