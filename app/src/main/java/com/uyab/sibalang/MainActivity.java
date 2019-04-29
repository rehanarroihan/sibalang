package com.uyab.sibalang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;
import com.uyab.sibalang.Util.GlobalConfig;
import com.uyab.sibalang.Util.RetrofitErrorUtils;
import com.uyab.sibalang.adapter.StuffAdapter;
import com.uyab.sibalang.adapter.StuffColumnAdapter;
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
    private RecyclerView rvLost, rvFound;
    private ArrayList<Stuff> lostList, foundList;
    private ArrayList<Stuff> lostListAll = new ArrayList<>();
    private ArrayList<Stuff> foundListAll = new ArrayList<>();
    private StuffAdapter stuffAdapter;
    private StuffColumnAdapter stuffColumnAdapter;

    boolean isFiltered;
    ArrayList<Integer> mListLostFilter = new ArrayList<>();
    ArrayList<Integer> mListFoundFilter = new ArrayList<>();
    String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Si Balang");

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mQuery = newText.toLowerCase();
                        doFilter(mQuery);
                        return true;
                    }
                }
        );

        FloatingActionButton btnAddFound = findViewById(R.id.buttonAddFound);
        FloatingActionButton btnAddLost = findViewById(R.id.buttonAddLost);
        btnAddFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this, NewStuffActivity.class);
                i.putExtra(NewStuffActivity.INPUT_TYPE, "found");
                startActivity(i);
            }
        });
        btnAddLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewStuffActivity.class);
                i.putExtra(NewStuffActivity.INPUT_TYPE, "lost");
                startActivity(i);
            }
        });

        rvLost = findViewById(R.id.recyclerViewLost);
        lostList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvLost.setLayoutManager(layoutManager);
        stuffColumnAdapter = new StuffColumnAdapter(lostList, new StuffColumnAdapter.StuffInterface() {
            @Override
            public void doClick(int post) {
                Stuff stuff = new Stuff();
                stuff = lostList.get(post);

                Intent i = new Intent(MainActivity.this, StuffDetailActivity.class);
                i.putExtra(StuffDetailActivity.STUFF_DATA, stuff);
                startActivity(i);
            }
        }, MainActivity.this);
        rvLost.setAdapter(stuffColumnAdapter);

        rvFound = findViewById(R.id.recyclerViewFound);
        foundList = new ArrayList<>();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(MainActivity.this);
        rvFound.setLayoutManager(layoutManager2);
        stuffAdapter = new StuffAdapter(foundList, new StuffAdapter.StuffInterface() {
            @Override
            public void doClick(int post) {
                Stuff stuff = new Stuff();
                stuff = foundList.get(post);

                Intent i = new Intent(MainActivity.this, StuffDetailActivity.class);
                i.putExtra(StuffDetailActivity.STUFF_DATA, stuff);
                startActivity(i);
            }
        }, MainActivity.this);
        rvFound.setAdapter(stuffAdapter);

        getData();
    }

    private void doFilter(String query) {
        if (!isFiltered) {
            lostListAll.clear();
            foundListAll.clear();
            lostListAll.addAll(lostList);
            foundListAll.addAll(foundList);
            isFiltered = true;
        }

        lostList.clear();
        foundList.clear();
        if (query == null || query.isEmpty()) {
            lostList.addAll(lostListAll);
            foundList.addAll(foundListAll);
            isFiltered = false;
        } else {
            mListLostFilter.clear();
            for (int i = 0; i < lostListAll.size(); i++) {
                Stuff stuff= lostListAll.get(i);
                if (stuff.getName().toLowerCase().contains(query) ||
                        stuff.getDescription().toLowerCase().contains(query)) {
                    lostList.add(stuff);
                    mListLostFilter.add(i);
                }
            }

            mListFoundFilter.clear();
            for (int i = 0; i < foundListAll.size(); i++) {
                Stuff stuff = foundListAll.get(i);
                if (stuff.getName().toLowerCase().contains(query) ||
                        stuff.getDescription().toLowerCase().contains(query)) {
                    foundList.add(stuff);
                    mListFoundFilter.add(i);
                }
            }
        }
        stuffAdapter.notifyDataSetChanged();
        stuffColumnAdapter.notifyDataSetChanged();
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
                        separateDataType(response.body().getStuff());
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
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

    private void separateDataType(ArrayList<Stuff> list) {
        lostList.clear();
        foundList.clear();
        if(list.size() > 0) {
            for (Stuff st : list) {
                if (st.getType().equals("lost")) {
                    lostList.add(st);
                } else {
                    foundList.add(st);
                }
            }
        }
        stuffAdapter.notifyDataSetChanged();
        stuffColumnAdapter.notifyDataSetChanged();
    }

    private void doLogout() {
        Prefs.putBoolean(GlobalConfig.IS_LOGGED_IN, false);
        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                doLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
