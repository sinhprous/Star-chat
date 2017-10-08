package com.example.sinh.starchat.JsonParsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 10/7/2017.
 */

public interface APIService {
    @POST("/api/login/")
    @Headers({"Content-type: application/json", "Cache-control: max-age=640000"})
    Call<Result> login(@Body Login body);

    class Login {
        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("password")
        @Expose
        public String password;

        public Login(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    class Result {
        @SerializedName("result")
        @Expose
        public String result;

        @Override
        public String toString() {
            return result;
        }
    }
}