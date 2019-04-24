package com.uyab.sibalang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.uyab.sibalang.Util.GlobalConfig;
import com.uyab.sibalang.Util.RetrofitErrorUtils;
import com.uyab.sibalang.api.ApiClient;
import com.uyab.sibalang.api.ApiInterface;
import com.uyab.sibalang.model.ErrorResponse;
import com.uyab.sibalang.model.Stuff;
import com.uyab.sibalang.model.StuffDetail;
import com.uyab.sibalang.model.StuffDetailResponse;
import com.uyab.sibalang.model.StuffResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StuffDetailActivity extends AppCompatActivity {
    public static final String STUFF_DATA = "STUFF_DATA";
    private ImageView imageViewPict;
    private TextView textViewName, textViewDate, textViewStatus, textViewDesc;
    private TextView tvNama, tvProdi, tvWA;
    private Button btnClaim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_detail);
        setTitle("Detail Barang Hilang");

        Intent intent = getIntent();
        Stuff stuff = intent.getParcelableExtra(STUFF_DATA);

        imageViewPict = findViewById(R.id.imageViewPict);
        textViewName = findViewById(R.id.textViewName);
        textViewDate = findViewById(R.id.textViewDate);
        textViewDesc = findViewById(R.id.textViewDesc);
        textViewStatus = findViewById(R.id.textViewStatus);
        btnClaim = findViewById(R.id.buttonClaim);

        tvNama = findViewById(R.id.textViewUser);
        tvProdi = findViewById(R.id.textViewFakultas);
        tvWA = findViewById(R.id.textViewWa);

        Glide.with(this)
                .load(GlobalConfig.IMAGE_BASE_URL + stuff.getPhoto())
                .into(imageViewPict);
        textViewName.setText(stuff.getName());
        textViewDesc.setText(stuff.getDescription());
        textViewDate.setText("Ditemukan pada: " + stuff.getDate());
        if(stuff.getTurned().equals("1")) {
            textViewStatus.setText("Status: Sudah bertemu pemilik");
            btnClaim.setVisibility(View.GONE);
        } else {
            textViewStatus.setText("Status: Belum bertemu pemilik");
            btnClaim.setVisibility(View.VISIBLE);
        }

        getStuffDetail(stuff.getId());
    }

    private void getStuffDetail(String stuffId) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<StuffDetailResponse> stuffDetailCall = api.stuffDetail(stuffId);
        stuffDetailCall.enqueue(new Callback<StuffDetailResponse>() {
            @Override
            public void onResponse(Call<StuffDetailResponse> call, Response<StuffDetailResponse> response) {
                if ((response.isSuccessful()) && (response.errorBody() == null)) {
                    String errorCode = response.body().getErrCode();
                    if (errorCode.equals("00")) {
                        tvNama.setText("Nama Penemu: " + response.body().getStuff().getFullname());
                        tvProdi.setText("Fakultas/Prodi: " + response.body().getStuff().getDepartmen() + "/" + response.body().getStuff().getProgram());
                        tvWA.setText("Nomor Whatsapp: " + response.body().getStuff().getPhone());
                    }
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
            public void onFailure(Call<StuffDetailResponse> call, Throwable t) {
                Toast.makeText(StuffDetailActivity.this, getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
