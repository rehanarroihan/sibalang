package com.uyab.sibalang.api;

import com.uyab.sibalang.model.GeneralResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("auth/register")
    Call<GeneralResponse> register(
            @Field("nim") String nim,
            @Field("password") String password,
            @Field("fullname") String fullname,
            @Field("departmen") String departmen,
            @Field("program") String program,
            @Field("phone") String phone,
            @Field("position") String position
    );

    @FormUrlEncoded
    @POST("auth/login")
    Call<GeneralResponse> login(
            @Field("nim") String nim,
            @Field("password") String password
    );

    @Multipart
    @POST("stuff")
    Call<GeneralResponse> addStuff(
            @Field("nim") String nim,
            @Field("name") String name,
            @Field("description") String description,
            @Part MultipartBody.Part photo
    );

    @FormUrlEncoded
    @POST("stuff/turn")
    Call<GeneralResponse> turnStuff(
            @Field("stuff_id") String stuffId
    );
}
