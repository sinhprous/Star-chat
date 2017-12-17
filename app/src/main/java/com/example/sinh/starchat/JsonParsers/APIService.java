package com.example.sinh.starchat.JsonParsers;

import com.example.sinh.starchat.Model.User;
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
    Call<LoginResponse> login(@Body LoginRequest body);

    @POST("/api/register/")
    @Headers({"Content-type: application/json", "Cache-control: max-age=640000"})
    Call<RegisterResponse> register(@Body User body);

    // Tiny model for api call
    // Actual model could not be in here

    class LoginRequest {
        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("password")
        @Expose
        public String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    class LoginResponse {
        @SerializedName("result")
        @Expose
        public String result;
        // TODO: return user info
        @Override
        public String toString() {
            return result;
        }
    }

    class RegisterResponse {
        @SerializedName("result")
        @Expose
        public String result;

        @Override
        public String toString() {
            return result;
        }
    }
}