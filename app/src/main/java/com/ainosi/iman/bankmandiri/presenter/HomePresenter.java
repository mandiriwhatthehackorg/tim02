package com.ainosi.iman.bankmandiri.presenter;

import android.content.Context;
import android.util.Log;

import com.ainosi.iman.bankmandiri.util.Api;
import com.ainosi.iman.bankmandiri.util.Config;
import com.ainosi.iman.bankmandiri.util.JSONHandler;
import com.ainosi.iman.bankmandiri.util.Key;
import com.ainosi.iman.bankmandiri.util.SharedPref;
import com.ainosi.iman.bankmandiri.util.Util;
import com.ainosi.iman.bankmandiri.util.Value;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class HomePresenter {
    private Context context;
    private HomeInterface homeInterface;
    private static final String TAG = "HomePresenter";

    public HomePresenter(Context context, HomeInterface homeInterface){
        this.context = context;
        this.homeInterface = homeInterface;
    }

    private void returnBalance(String balance){
        String accountnumber = SharedPref.get(context, Config.ACCOUNTNUMBER, "-");
        String bearer_token = SharedPref.get(context, Config.BEARER_TOKEN, "-");
        JSONObject request = new JSONObject();
        try {
            request.put("transactionID", "00000011");
            request.put("transactionDate", Util.getDateNow("yyyy-MM-dd"));
            request.put("referenceID", "Order/2019/001");
            request.put("sourceAccountNumber", accountnumber);
            request.put("beneficiaryAccountNumber", "1111006407851");
            request.put("amount", balance);
            request.put("currency", "IDR");
            request.put("sourceAccountCustType", "1");
            request.put("beneficiaryCustType", "1");
            request.put("remark1", "Transfer");
            request.put("remark2", "Reverse Money");
        } catch (JSONException e) {
            Log.e(TAG, "returnBalance: " + e);
            e.printStackTrace();
        }

        JSONObject body = new JSONObject();
        try {
            body.put("Request", request);
        } catch (JSONException e) {
            Log.e(TAG, "returnBalance: " + e);
            e.printStackTrace();
        }

        Log.e(TAG, "returnBalance: req$ " +body.toString());

        AndroidNetworking.post(Config.SERVER + Api.TRANSFER)
                .addHeaders(Config.AUTHORIZATION, "Bearer " + bearer_token.trim())
                .addHeaders(Config.CONTENT_TYPE, Value.JSON_APPLICATION)
                .addHeaders(Config.ACCEPT, Value.JSON_APPLICATION)
                .addJSONObjectBody(body)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: ===============================================");
                        Log.e(TAG, "onResponse: response$ " + response.toString());
                        JSONObject Response = JSONHandler.getJSONObjectFromJSONObject(response, Key.RESPONSE);
                        Log.e(TAG, "onResponse: Response " + Response.toString());
                        JSONObject balance = JSONHandler.getJSONObjectFromJSONObject(Response, Key.BALANCE);
                        Log.e(TAG, "onResponse: balance$ " + balance.toString());
                        JSONObject balanceInfo = JSONHandler.getJSONObjectFromJSONObject(balance, Key.BALANCEINFO);
                        Log.e(TAG, "onResponse: balanceInfo$ " + balanceInfo.toString());
                        String balance_value = JSONHandler.getStringFromJSONObject(balanceInfo, Key.LEDGERBALANCE);
                        Log.e(TAG, "onResponse: balance$ Rp " + balance_value);
                        Log.e(TAG, "onResponse: ===============================================");

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: ===============================================");
                        Log.e(TAG, "onError: balance# " + anError.getErrorDetail());
                        Log.e(TAG, "onError: balance# " + anError.getErrorBody());
                        Log.e(TAG, "onError: balance# " + anError.getMessage());
                        Log.e(TAG, "onError: ===============================================");
                    }
                });
    }

    public void splitBalance(String string_balance, int set_a, int set_b, int set_c){
        long balance = Util.extractAmount(string_balance);
        Log.e(TAG, "splitBalance: "+balance);
        long[] saving = new long[4];
        saving[0] = (balance * set_a) / 100;
        saving[1] = (balance * set_b) / 100;
        saving[2] = (balance * set_c) / 100;
        saving[3] = balance;
        homeInterface.onSplitBalanceSuccess(saving);
        returnBalance(String.valueOf(balance));
    }

    public void getBalanceInfo(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                return response.request().newBuilder()
                        .header("Authorization", Credentials.basic(Value.CLIENT_ID, Value.CLIENT_SECRET)).build();
            }
        }).build();

        AndroidNetworking.get(Config.SERVER + Api.BEARER_TOKEN)
                .setOkHttpClient(okHttpClient)
                .addHeaders(Config.CONTENT_TYPE, Value.JSON_APPLICATION)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String bearer_token = JSONHandler.getStringFromJSONObject(response, Key.JWT);
                        SharedPref.set(context, Config.BEARER_TOKEN, bearer_token);
                        Log.e(TAG, "onResponse: token$ " + bearer_token);
                        getData();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: token# " + anError.getErrorDetail());
                        Log.e(TAG, "onError: token# " + anError.getErrorBody());
                        Log.e(TAG, "onError: token# " + anError.getMessage());
                    }
                });
    }

    private void getData() {
        String bearer_token = SharedPref.get(context, Config.BEARER_TOKEN, "-");
        Log.e(TAG, "onCreate  : " + bearer_token);

        AndroidNetworking.get(Config.SERVER + Api.SERVICING_CASA + "1111006397571" + Api.PARAM_BALANCE)
                .addHeaders(Config.AUTHORIZATION, "Bearer " + bearer_token.trim())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, "onResponse: response$ " + response.toString());
                        JSONObject Response = JSONHandler.getJSONObjectFromJSONObject(response, Key.RESPONSE);
                        Log.e(TAG, "onResponse: Response " + Response.toString());
                        JSONObject balance = JSONHandler.getJSONObjectFromJSONObject(Response, Key.BALANCE);
                        Log.e(TAG, "onResponse: balance$ " + balance.toString());
                        JSONObject balanceInfo = JSONHandler.getJSONObjectFromJSONObject(balance, Key.BALANCEINFO);
                        Log.e(TAG, "onResponse: balanceInfo$ " + balanceInfo.toString());
                        String balance_value = JSONHandler.getStringFromJSONObject(balanceInfo, Key.LEDGERBALANCE);
                        Log.e(TAG, "onResponse: balance$ Rp " + balance_value);

                        homeInterface.onGetBalanceInfoSuccess(balanceInfo);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: balance# " + anError.getErrorDetail());
                        Log.e(TAG, "onError: balance# " + anError.getErrorBody());
                        Log.e(TAG, "onError: balance# " + anError.getMessage());

                        homeInterface.onGetBalanceInfoFailed(anError.getMessage());
                    }
                });
    }
}
