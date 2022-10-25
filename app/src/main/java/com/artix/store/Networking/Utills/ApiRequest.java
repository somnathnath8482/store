package com.artix.store.Networking.Utills;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.artix.store.BuildConfig;
import com.artix.store.Helper.MethodClass;
import com.artix.store.Networking.interfaces.ApiInterface;
import com.artix.store.Networking.interfaces.OnCallBackListner;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiRequest {
    private Context context;
    private OnCallBackListner listner;
  private Dialog progressDialog;


    public ApiRequest(Context context, OnCallBackListner listner) {
        this.context = context;
        this.listner = listner;

    }

    private void loader() {
      /*  if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }


        progressDialog = new Dialog(context);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.customprogress);*/


    }


    /*---GET REQUEST---*/
    public void callGetRequest(String url, String authHeader, String tag) {
        if (NetWorkChecker.check(context)) {


            loader();
            if (1==1) {
                if (progressDialog != null) {
                    progressDialog.show();
                }
            }

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<String> callMethod = apiInterface.getRequest(url, authHeader, tag);
            callMethod.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    onCallBackSuccess(call, response);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    onCallBackFailed(call, t);
                }
            });
        }

    }


    /*---POST REQUEST WITH JSON BODY (AUTH & NO AUTH)---*/
    public void postRequestJson(String url, HashMap<String, Object> params, String authHeader, String tag) {
        if (NetWorkChecker.check(context)) {

            if (1==1) {
                loader();
                if (progressDialog != null) {
                    progressDialog.show();
                }

            }

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<String> callMethod = apiInterface.postRequestJson(url, params, authHeader, tag);
            callMethod.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    onCallBackSuccess(call, response);
                    if (BuildConfig.DEBUG) {
                        try {
                            Log.i("response_body" + call.request().header("tag"), response.body());
                            Log.i("response_body" + call.request().header("tag"), url);
                            Log.i("response_body" + call.request().header("tag") + "_params", "" + new GsonBuilder().create().toJson(params));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    onCallBackFailed(call, t);
                }
            });
        }

    }

    public void postRequestJson(String url, JsonObject params, String authHeader, String tag) {
        if (NetWorkChecker.check(context)) {

            loader();
            if (progressDialog != null) {
                progressDialog.show();
            }


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<String> callMethod = apiInterface.postRequestJson(url, params, authHeader, tag);
            callMethod.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    onCallBackSuccess(call, response);
                    if (BuildConfig.DEBUG) {
                        try {
                            Log.i("response_body" + call.request().header("tag"), response.body());
                            Log.i("response_body" + call.request().header("tag"), url);
                            Log.i("response_body" + call.request().header("tag") + "_params", "" + new GsonBuilder().create().toJson(params));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    onCallBackFailed(call, t);
                }
            });
        }

    }

    public void postRequestJson(String url, HashMap<String, Object> params, String tag) {
        if (NetWorkChecker.check(context)) {
            loader();
            if (progressDialog != null) {
                progressDialog.show();
            }

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<String> callMethod = apiInterface.postRequestJson(url, params, tag);
            callMethod.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    onCallBackSuccess(call, response);
                    if (BuildConfig.DEBUG) {
                        try {
                            Log.i("response_body" + call.request().header("tag"), response.body());
                            Log.i("response_body" + call.request().header("tag"), url);
                            // Log.i("response_body" + call.request().header("tag") + "_params", "" + new GsonBuilder().create().toJson(params));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    onCallBackFailed(call, t);
                }
            });
        }

    }


    /*---POST REQUEST WITH FORM DATA  (AUTH & NO AUTH)---*/
    public void postRequestFormData(String url, HashMap<String, String> params, String authHeader, String tag) {
        if (NetWorkChecker.check(context)) {
            loader();
            if (1==1) {
                progressDialog.show();
            }

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<String> callMethod = apiInterface.postRequestFormData(url, params, authHeader, tag);
            callMethod.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    onCallBackSuccess(call, response);
                    if (BuildConfig.DEBUG) {
                        try {
                            Log.i("response_body" + call.request().header("tag"), response.body());
                            Log.i("response_body" + call.request().header("tag"), url);
                            Log.i("response_body" + call.request().header("tag") + "_params", "" + new GsonBuilder().create().toJson(params));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    onCallBackFailed(call, t);
                }
            });
        }

    }

    public void postRequestFormData(String url, HashMap<String, String> params, String tag) {
        if (NetWorkChecker.check(context)) {
            loader();
            if (progressDialog != null) {
                progressDialog.show();
            }

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<String> callMethod = apiInterface.postRequestFormData(url, params, tag);
            callMethod.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    onCallBackSuccess(call, response);
                    if (BuildConfig.DEBUG) {
                        try {
                            Log.i("response_body" + call.request().header("tag"), response.body());
                            Log.i("response_body" + call.request().header("tag"), url);
                            Log.i("response_body" + call.request().header("tag") + "_params", "" + new GsonBuilder().create().toJson(params));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    onCallBackFailed(call, t);
                }
            });
        }

    }


     /*----MULTIPART REQUEST----*/
    public void multipartRequest(String url, @NonNull HashMap<String, String> param, @NonNull PART part, String authHeader, String tag) {

        if (NetWorkChecker.check(context)) {
            loader();
            if (1==1) {
                progressDialog.show();
            }

            ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

            Call<String> stringCall = service.multipartFormData(url, getParam(param), Params.createMultiPart(part), authHeader, tag);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                    onCallBackSuccess(call, response);
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                    onCallBackFailed(call, t);

                }
            });
        }

    }

    public void callManyFilesUploadwithJsonBody(String url, @NonNull HashMap<String, String> param, List<PART> part_list, String authHeader, final String tag) {

        if (NetWorkChecker.check(context)) {
            loader();
            if (progressDialog != null) {
                progressDialog.show();
            }

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<String> stringCall = apiInterface.multipartPostMethod(url, getParam(param), Params.createPartList(part_list), authHeader, tag);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                    onCallBackSuccess(call, response);
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                    onCallBackFailed(call, t);

                }
            });
        }

    }

    public void multipartRequest(String url, @NonNull HashMap<String, String> param, @NonNull PART part, String tag) {

        if (NetWorkChecker.check(context)) {
            loader();
            if (progressDialog != null) {
                progressDialog.show();
            }

            ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

            Call<String> stringCall = service.multipartFormData(url, getParam(param), Params.createMultiPart(part), tag);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                    onCallBackSuccess(call, response);
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                    onCallBackFailed(call, t);

                }
            });
        }

    }

    public void callManyFilesUploadWithJsonStep_1(String url, @NonNull HashMap<String, Object> param, List<PART> part_list, String authHeader, final String tag) {

        if (NetWorkChecker.check(context)) {
            loader();
            if (progressDialog != null) {
                progressDialog.show();
            }

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<String> stringCall = apiInterface.multipartPostMethod(url, getParamForStep_1(param), Params.createPartList(part_list), authHeader, tag);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                    onCallBackSuccess(call, response);
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                    onCallBackFailed(call, t);

                }
            });
        }

    }

    /*--RESPONSE HANDLER---*/
    public void onCallBackSuccess(Call<String> call, Response<String> response) {


        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (response.isSuccessful()) {

            try {
                assert response.body() != null;
                JSONObject jsonObject = MethodClass.get_result_from_webservice((Activity) context, new JSONObject(response.body()));
                if (jsonObject != null) {
                    listner.OnCallBackSuccess(call.request().header("tag"), new JSONObject(response.body()));
                }
            } catch (Exception e) {
                listner.OnCallBackError(call.request().header("tag"), e.getMessage(), -1);
                e.printStackTrace();
            }
        } else {

            try {
                JSONObject object = new JSONObject(response.errorBody().toString());

                if (object.has("error"))
                    ToastUtils.showLong((Activity) context, object.getString("error"), true);


            } catch (JSONException e) {
                e.printStackTrace();
                ToastUtils.showLong((Activity) context, "Something Went Wrong", true);

            }


        }


    }

    public void onCallBackFailed(Call<String> call, Throwable t) {


        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (listner != null) {
                listner.OnCallBackError(call.request().header("tag"), t.getMessage(), -1);
            }

            Log.e("response_body" + call.request().header("tag"), call.toString());

        } catch (Exception e) {

            listner.OnCallBackError(call.request().header("tag"), e.getMessage(), -1);


        }


    }


    private HashMap<String, RequestBody> getParam(HashMap<String, String> param) {
        HashMap<String, RequestBody> tempParam = new HashMap<>();
        for (String key : param.keySet()) {
            tempParam.put(key, toRequestBody(param.get(key)));
        }

        return tempParam;
    }




    private static RequestBody toRequestBody(String value) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, value);
    }

    private HashMap<String, RequestBody> getParamForStep_1(HashMap<String, Object> param) {
        HashMap<String, RequestBody> tempParam = new HashMap<>();
        for (String key : param.keySet()) {
            tempParam.put(key, toRequestBodyForStep_1(param.get(key)));
        }
        return tempParam;
    }

    private static RequestBody toRequestBodyForStep_1(Object value) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, value.toString());
    }

}

