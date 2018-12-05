package com.example.ramon.tareafinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ramon.tareafinal.Adapter.BreedAdapter;
import com.example.ramon.tareafinal.Adapter.ImageAdapter;
import com.example.ramon.tareafinal.DogAPI.Constants;
import com.example.ramon.tareafinal.DogAPI.RetrofitAdapter;
import com.example.ramon.tareafinal.DogAPI.Service;
import com.example.ramon.tareafinal.Model.Breed;
import com.example.ramon.tareafinal.Model.Dog;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogsGallery extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Dog> dogs;
    private ImageAdapter mAdapter;
    private String breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_gallery);

        Bundle bundle = this.getIntent().getExtras();
        breed = bundle.getString("stringBreed");
        Log.i(Constants.TAG,breed);

        buildGallery();
    }

    private void buildGallery() {
        mRecyclerView = findViewById(R.id.imageRecycler);
        dogs = new ArrayList<>();

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ImageAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


        addElements();
    }

    private void addElements() {
        RetrofitAdapter restApiAdapter = new RetrofitAdapter();
        Service service = restApiAdapter.getCharacterService();
        retrofit2.Call<JsonObject> call = service.getByBreed(breed);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    assert response.body() != null;
                    JSONArray jsonArray = new JSONArray(response.body().getAsJsonArray("message").toString());

                    parseBreed(jsonArray);
                    mAdapter.addToList(dogs);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void parseBreed(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++){
            String image = jsonArray.getString(i);
            dogs.add(new Dog(image));
            Log.i(Constants.TAG,image);

        }
    }
}
