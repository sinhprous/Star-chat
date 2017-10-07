package com.example.sinh.starchat.JsonParsers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 10/7/2017.
 */


// send email and password
public class LoginParser {
    String res;
    Context context;
    public LoginParser (Context context) {
        this.res = null;
        this.context = context;
    }
    public String post(String email, String password){
        APIService apiService = ApiUtils.getAPIService();
        apiService.savePost(email, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    res = response.body().toString();
                } else {
                    //Toast.makeText(context, "PASSWORD INCCORECT!", Toast.LENGTH_SHORT).show();
                    res = response.body().toString();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(context, "UNABLE TO CONNECT TO SERVER!", Toast.LENGTH_SHORT).show();
            }
        });
        return res;
    }
}

class Login {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

interface APIService {
    @POST("/post")
    @FormUrlEncoded
    Call<Login> savePost(@Field("email") String email, @Field("password") String password);
}

class ApiUtils {

    private ApiUtils() {};

    public static final String BASE_URL = "http://127.0.0.1/login/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}

