package com.e.newapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ClientInterface.HeroesAPI;
import model.Heroes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAllHeroesActivity extends AppCompatActivity {

    private TextView tvData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData= findViewById(R.id.tvData);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroesAPI heroesAPI = retrofit.create(HeroesAPI.class);

        final Call<List<Heroes>> listCall = heroesAPI.getHeroes();

        listCall.enqueue(new Callback<List<Heroes>>() {
            @Override
            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
                if (!response.isSuccessful()){
                    tvData.setText(response.code());
                }
                List<Heroes> heroesList= response.body();

                for (Heroes heroes :heroesList){
                    String a ="";
                    a += "ID : " +heroes.getId() +"\n";
                    a += "Name : "+heroes.getName() +"\n";
                    a += "Description : "+heroes.getDesc() +"\n";

                    tvData.append(a);
                }
            }

            @Override
            public void onFailure(Call<List<Heroes>> call, Throwable t) {
                tvData.setText("Error" +t.getMessage());

            }
        });
    }
}
