package com.uyab.sibalang;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.uyab.sibalang.model.GeneralResponse;
import com.uyab.sibalang.model.Stuff;
import com.uyab.sibalang.model.StuffDetail;
import com.uyab.sibalang.model.StuffDetailResponse;
import com.uyab.sibalang.model.StuffResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StuffDetailActivity extends AppCompatActivity {
    public static final String STUFF_DATA = "STUFF_DATA";
    private TextView tvNama, tvProdi, tvWA, tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final Stuff stuff = intent.getParcelableExtra(STUFF_DATA);

        String title;
        String dataState;
        if(stuff.getType().equals("lost")) {
            dataState = "Data Pencari";
            title = "Detail Barang Hilang";
        } else {
            dataState = "Data Penemu";
            title = "Detail Barang Ditemukan";
        }
        setTitle(title);

        ImageView imageViewPict = findViewById(R.id.imageViewPict);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewDesc = findViewById(R.id.textViewDesc);
        TextView textViewStatus = findViewById(R.id.textViewStatus);
        Button btnClaim = findViewById(R.id.buttonClaim);

        btnClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StuffDetailActivity.this);
                builder.setMessage("Apakah anda akan meng claim barang ini ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                doClaim(stuff.getId());
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.create().show();
            }
        });

        tvNama = findViewById(R.id.textViewUser);
        tvProdi = findViewById(R.id.textViewFakultas);
        tvWA = findViewById(R.id.textViewWa);
        tvState = findViewById(R.id.textViewState);
        tvState.setText(dataState);

        Glide.with(this)
                .load(GlobalConfig.IMAGE_BASE_URL + stuff.getPhoto())
                .into(imageViewPict);
        textViewName.setText(stuff.getName());
        textViewDesc.setText(stuff.getDescription());
        textViewDate.setText("Ditemukan pada: " + stuff.getDate());
        textViewStatus.setText("Status: Belum bertemu pemilik");

        getStuffDetail(stuff.getId());
    }

    private void doClaim(String id) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse> stuffTurnCall = api.turnStuff(id, Prefs.getString(GlobalConfig.USER_ID, null));
        stuffTurnCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if ((response.isSuccessful()) && (response.errorBody() == null)) {
                    String errorCode = response.body().getErrCode();
                    if (errorCode.equals("00")) {
                        MainActivity.self.finish();
                        Intent intent = new Intent(StuffDetailActivity.this, MainActivity.class);
                        startActivityForResult(intent, GlobalConfig.REFRESH_REQUEST_CODE);
                        finish();
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
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(StuffDetailActivity.this, getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        });
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
                        tvNama.setText("Nama: " + response.body().getStuff().getFullname());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
