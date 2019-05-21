package com.e.newapplication;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import ClientInterface.HeroesAPI;
import model.Heroes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddHeroActivity extends AppCompatActivity {
    
    private EditText etName,etDesc;
    private ImageView imgHero;
    private Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hero);
        
        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        imgHero = findViewById(R.id.imgHero);
        btnSave = findViewById(R.id.btnSave);

        loadFromUrl();
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHero();
            }
        });
    }

    private void StrictMode()
    {
        android.os.StrictMode.ThreadPolicy policy= new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);

    }

    private void loadFromUrl() {
        StrictMode();
        try
        {
            String imgUrl="https://pmcvariety.files.wordpress.com/2019/03/spider-man-shattered-dimensions.png?w=805";
            URL url = new URL(imgUrl);
            imgHero.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(AddHeroActivity.this, "Error",Toast.LENGTH_SHORT).show();
        }


    }

    private void AddHero() {

        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroesAPI heroesAPI = retrofit.create(HeroesAPI.class);

        Call<Void> voidCall = heroesAPI.addHero(name,desc);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(AddHeroActivity.this,"Code "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AddHeroActivity.this, "Successfully Added",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(AddHeroActivity.this, "Error "+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
