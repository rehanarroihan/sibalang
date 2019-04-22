package com.uyab.sibalang.Util;

import com.uyab.sibalang.api.ApiClient;
import com.uyab.sibalang.api.ApiInterface;
import com.uyab.sibalang.model.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class RetrofitErrorUtils {
    private ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
    public static ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                ApiClient.getClient().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        ErrorResponse error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorResponse();
        }
        return error;
    }
}
