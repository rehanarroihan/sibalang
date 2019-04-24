package com.uyab.sibalang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.uyab.sibalang.Util.GlobalConfig;
import com.uyab.sibalang.Util.RetrofitErrorUtils;
import com.uyab.sibalang.api.ApiClient;
import com.uyab.sibalang.api.ApiInterface;
import com.uyab.sibalang.model.ErrorResponse;
import com.uyab.sibalang.model.Stuff;
import com.uyab.sibalang.model.StuffResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StuffDetailActivity extends AppCompatActivity {
    public static final String STUFF_DATA = "STUFF_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_detail);

        Intent intent = getIntent();
        Stuff stuff = intent.getParcelableExtra(STUFF_DATA);

        getStuffDetail(stuff.getId());
    }

    private void getStuffDetail(String stuffId) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<StuffResponse> stuffDetailCall = api.stuffDetail(stuffId);
        stuffDetailCall.enqueue(new Callback<StuffResponse>() {
            @Override
            public void onResponse(Call<StuffResponse> call, Response<StuffResponse> response) {
                if ((response.isSuccessful()) && (response.errorBody() == null)) {
                    String errorCode = response.body().getErrCode();
                    if (errorCode.equals("00")) {

                    }
                    Toast.makeText(StuffDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    ErrorResponse error = RetrofitErrorUtils.parseError(response);
                    String errorMessage = error.message();
                    if(response.code() == 400) {
                        errorMessage = getResources().getString(R.string.bad_request);
                    }
                    Toast.makeText(StuffDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StuffResponse> call, Throwable t) {
                Toast.makeText(StuffDetailActivity.this, getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
