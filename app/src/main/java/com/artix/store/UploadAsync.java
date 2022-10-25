package com.artix.store;

import static com.artix.store.Networking.Utills.Utils.getParam;
import static com.artix.store.Networking.Utills.Utils.getParams;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.artix.store.Helper.MethodClass;
import com.artix.store.Networking.Utills.ApiClient;
import com.artix.store.Networking.Utills.PART;
import com.artix.store.Networking.Utills.Params;
import com.artix.store.Networking.interfaces.ApiInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadAsync extends AsyncTask<Void,Void,String> {
    Context context ;
    private Collection<PART> part_list = new ArrayList<>();
    List<String >Paths = new ArrayList<>();

    public UploadAsync(Context context, List<String> paths) {
        this.context = context;
        Paths = paths;
    }

    @Override
    protected String doInBackground(Void... voids) {

        for (int i = 0; i<Paths.size(); i++){
            String path = Paths.get(i);
            part_list.add(new PART("files[]",new File(path)));
        }

        HashMap<String, String> param = new HashMap<>();
        param.put("abc","abcd");


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<String> stringCall = apiInterface.multipartPostMethod("https://artixdevl.000webhostapp.com/Track/index.php",
                getParams(param),
                Params.createPartList(part_list),
                "",
                "Create");

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("UPLOAD", "onResponse: "+response.isSuccessful());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("UPLOAD", "onResponse: "+t.getMessage());

            }
        });

        return null;
    }
}
