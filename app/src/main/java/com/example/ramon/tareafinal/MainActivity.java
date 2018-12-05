package com.example.ramon.tareafinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ramon.tareafinal.Adapter.BreedAdapter;
import com.example.ramon.tareafinal.DogAPI.Constants;
import com.example.ramon.tareafinal.DogAPI.RetrofitAdapter;
import com.example.ramon.tareafinal.DogAPI.Service;
import com.example.ramon.tareafinal.Model.Breed;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Breed> breeds;
    private BreedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildRecyclerView();
        changeActivity();
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.breedsList);
        breeds = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BreedAdapter();
        mRecyclerView.setAdapter(mAdapter);

        addElements();
    }

    private void addElements() {
        RetrofitAdapter restApiAdapter = new RetrofitAdapter();
        Service service = restApiAdapter.getCharacterService();
        retrofit2.Call<JsonObject> call = service.getAllBreeds();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    assert response.body() != null;
                    JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject("message").toString());
                    JSONArray jsonArray = jsonObject.names();

                    parseBreed(jsonArray);
                    mAdapter.addToList(breeds);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void parseBreed(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++){
            String breed = jsonArray.getString(i);
            breeds.add(new Breed(breed));
            Log.i(Constants.TAG,breed);
        }
    }


    public void changeActivity(){
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String breed = breeds.get(mRecyclerView.getChildAdapterPosition(view)).getName();


                Intent intent = new Intent(MainActivity.this, DogsGallery.class);
                intent.putExtra("stringBreed",breed);
                startActivity(intent);
            }
        });
    }

}
