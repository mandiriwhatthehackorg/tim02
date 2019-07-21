package com.ainosi.iman.bankmandiri.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONHandler {

    private static final String TAG = "JSONHandler";

    public static String getStringFromJSONObject(JSONObject object, String key){
        String value = "-";

        try {
            value = object.has(key)?object.getString(key):value;
        } catch (JSONException e) {
            Log.e(TAG, "getStringFromJSONOBject: JSONException$ " + e);
            e.printStackTrace();
        } catch (NullPointerException e){
            Log.e(TAG, "getStringFromJSONOBject: NullPointerException$ " + e);
        }

        return value;
    }

    public static JSONObject getJSONObjectFromJSONObject(JSONObject object, String key){
        JSONObject result = new JSONObject();
        try {
            result  = object.has(key)?object.getJSONObject(key):result;
        } catch (JSONException e) {
            Log.e(TAG, "getStringFromJSONOBject: JSONException$ " + e);
            e.printStackTrace();
        } catch (NullPointerException e){
            Log.e(TAG, "getStringFromJSONOBject: NullPointerException$ " + e);
        }
        return result;
    }
}
