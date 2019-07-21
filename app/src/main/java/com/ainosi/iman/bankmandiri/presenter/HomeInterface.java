package com.ainosi.iman.bankmandiri.presenter;

import org.json.JSONObject;

public interface HomeInterface {
    void onGetBalanceInfoSuccess(JSONObject response);
    void onGetBalanceInfoFailed(String message);
    void onSplitBalanceSuccess(long... saving);
}
