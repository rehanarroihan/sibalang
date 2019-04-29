package com.uyab.sibalang;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.livinglifetechway.quickpermissions.annotations.WithPermissions;
import com.pixplicity.easyprefs.library.Prefs;
import com.uyab.sibalang.Util.GlobalConfig;
import com.uyab.sibalang.Util.RetrofitErrorUtils;
import com.uyab.sibalang.api.ApiClient;
import com.uyab.sibalang.api.ApiInterface;
import com.uyab.sibalang.model.ErrorResponse;
import com.uyab.sibalang.model.GeneralResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewStuffActivity extends AppCompatActivity {
    public static String INPUT_TYPE = "INPUT_TYPE";

    private static final int REQUEST_IMAGE_GET = 1;

    private ImageView ivPicture;
    private EditText etName, etDescription;
    private String imagePath, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stuff);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type = getIntent().getStringExtra(INPUT_TYPE);
        String title;
        if(type.equals("lost")) {
            title = "Laporan Kehilangan";
        } else {
            title = "Laporan Penemuan";
        }
        setTitle(title);

        etName = findViewById(R.id.editTextStuff);
        etDescription = findViewById(R.id.editTextDescription);
        ivPicture = findViewById(R.id.imageViewPreview);

        Button btChoose = findViewById(R.id.buttonPilihFile);
        btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });

        Button btnUpload = findViewById(R.id.buttonInput);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpload();
            }
        });
    }

    @WithPermissions(
            permissions = {Manifest.permission.READ_EXTERNAL_STORAGE},
            rationaleMessage = "Mohon aktifkan perizinan agar dapat mengambil foto",
            permanentlyDeniedMessage = "Foto tidak dapat diambil jika anda tidak memberikan izin"
    )
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    private void doUpload() {
        if(imagePath == null) {
            Toast.makeText(this, "Silahkan pilih gambar", Toast.LENGTH_SHORT).show();
            return;
        }

        //Processing image to upload
        File file = new File(imagePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imageRequest = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);

        //Processing another data to send
        RequestBody id_user = RequestBody.create(MediaType.parse("text/plain"), Prefs.getString(GlobalConfig.USER_ID, null));
        RequestBody requestType = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etName.getText().toString());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), etDescription.getText().toString());

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse> uploadCall = api.addStuff(
                id_user,
                requestType,
                name,
                description,
                imageRequest
        );
        uploadCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if ((response.isSuccessful()) && (response.errorBody() == null)) {
                    String errorCode = response.body().getErrCode();
                    if (errorCode.equals("00")) {
                        MainActivity.self.finish();
                        Intent intent = new Intent(NewStuffActivity.this, MainActivity.class);
                        startActivityForResult(intent, GlobalConfig.REFRESH_REQUEST_CODE);
                        finish();
                    }
                    Toast.makeText(NewStuffActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    ErrorResponse error = RetrofitErrorUtils.parseError(response);
                    String errorMessage = error.message();
                    if(response.code() == 400) {
                        errorMessage = getResources().getString(R.string.bad_request);
                    }
                    Toast.makeText(NewStuffActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(NewStuffActivity.this, getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            imagePath = getRealPathFromURIPath(imageUri, NewStuffActivity.this);
            ivPicture.setImageURI(imageUri);
            ivPicture.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
