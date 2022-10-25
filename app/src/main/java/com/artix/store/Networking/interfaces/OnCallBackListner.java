package com.artix.store.Networking.interfaces;

import org.json.JSONObject;

public interface OnCallBackListner {

    void OnCallBackSuccess(String tag, JSONObject response);
    void OnCallBackError(String tag, String error, int i);


}

