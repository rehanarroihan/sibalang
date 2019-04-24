package com.uyab.sibalang;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.uyab.sibalang.Util.GlobalConfig;
import com.uyab.sibalang.Util.RetrofitErrorUtils;
import com.uyab.sibalang.adapter.StuffAdapter;
import com.uyab.sibalang.api.ApiClient;
import com.uyab.sibalang.api.ApiInterface;
import com.uyab.sibalang.model.ErrorResponse;
import com.uyab.sibalang.model.Stuff;
import com.uyab.sibalang.model.StuffResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private RecyclerView rv;
    private ArrayList<Stuff> stuff;
    private StuffAdapter stuffAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.INVISIBLE);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, NewStuffActivity.class));
            }
        });

        rv = findViewById(R.id.recyclerViewList);
        stuff = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(layoutManager);
        stuffAdapter = new StuffAdapter(stuff, new StuffAdapter.StuffInterface() {
            @Override
            public void doClick(int post) {

            }
        }, MainActivity.this);
        rv.setAdapter(stuffAdapter);
        getData();

        NestedScrollView mainSV = findViewById(R.id.nestedScrollViewMain);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mainSV.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    //Log.d(TAG, "Y: " + String.valueOf(scrollY));
                    if (scrollY > 75) {
                        toolbar.setVisibility(View.VISIBLE);
                    }
                    if (scrollY < 75) {
                        toolbar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    private void getData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<StuffResponse> stuffCall = api.stuffList();
        stuffCall.enqueue(new Callback<StuffResponse>() {
            @Override
            public void onResponse(Call<StuffResponse> call, Response<StuffResponse> response) {
                if ((response.isSuccessful()) && (response.errorBody() == null)) {
                    String errorCode = response.body().getErrCode();
                    if (errorCode.equals("00")) {
                        stuff.add(response.body().getStuff());
                        stuffAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    ErrorResponse error = RetrofitErrorUtils.parseError(response);
                    String errorMessage = error.message();
                    if(response.code() == 400) {
                        errorMessage = getResources().getString(R.string.bad_request);
                    }
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StuffResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        });
    }

}