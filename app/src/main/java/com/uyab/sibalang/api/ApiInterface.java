package com.uyab.sibalang.api;

import com.uyab.sibalang.model.GeneralResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

    @FormUrlEncoded
    @POST("stuff")
    Call<GeneralResponse> addStuff(
            @Field("nim") String nim,
            @Field("name") String name,
            @Field("description") String description,
            @Field("photo") String photo,
            @Field("turned") String turned
    );

    @FormUrlEncoded
    @POST("stuff/turn")
    Call<GeneralResponse> turnStuff(
            @Field("stuff_id") String stuffId
    );
}
