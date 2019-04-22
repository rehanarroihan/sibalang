package com.uyab.sibalang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.uyab.sibalang.Util.GlobalConfig;
import com.uyab.sibalang.Util.RetrofitErrorUtils;
import com.uyab.sibalang.api.ApiClient;
import com.uyab.sibalang.api.ApiInterface;
import com.uyab.sibalang.model.ErrorResponse;
import com.uyab.sibalang.model.GeneralResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etNIM, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNIM = findViewById(R.id.editTextNIM);
        etPassword = findViewById(R.id.editTextPassword);

        Button btnLogin = findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse> loginCall = api.login(etNIM.getText().toString(), etPassword.getText().toString());
        loginCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if ((response.isSuccessful()) && (response.errorBody() == null)) {
                    String errorCode = response.body().getErrCode();
                    if (errorCode.equals("00")) {
                        Prefs.putBoolean(GlobalConfig.IS_LOGGED_IN, true);
                        Prefs.putString(GlobalConfig.USER_NIM, etNIM.getText().toString());

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(i);
                        finish();
                    }
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    ErrorResponse error = RetrofitErrorUtils.parseError(response);
                    String errorMessage = error.message();
                    if(response.code() == 400) {
                        errorMessage = getResources().getString(R.string.bad_request);
                    }
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
