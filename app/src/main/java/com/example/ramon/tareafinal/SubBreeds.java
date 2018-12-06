package com.example.ramon.tareafinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ramon.tareafinal.Adapter.BreedAdapter;
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

public class SubBreeds extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Breed> subBreeds;
    private BreedAdapter mAdapter;
    private String breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_breeds);

        Bundle bundle = this.getIntent().getExtras();
        breed = bundle.getString("stringBreed");

        buildRecyclerView();
        changeActivity();
    }

    private void changeActivity() {
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subBreed = subBreeds.get(mRecyclerView.getChildAdapterPosition(view)).getName();

                Intent intent = new Intent(SubBreeds.this, DogsGallery.class);
                intent.putExtra("stringBreed",breed);
                intent.putExtra("stringSubBreed",subBreed);

                startActivity(intent);
            }
        });
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.subBreeds);
        subBreeds = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BreedAdapter();
        mRecyclerView.setAdapter(mAdapter);

        addElements();
    }

    private void addElements() {
        RetrofitAdapter restApiAdapter = new RetrofitAdapter();
        Service service = restApiAdapter.getCharacterService();
        retrofit2.Call<JsonObject> call = service.listAllSubBreeds(breed);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    assert response.body() != null;
                    JSONArray jsonArray = new JSONArray(response.body().getAsJsonArray("message").toString());

                    subBreeds.add(new Breed("all"));


                    parseBreed(jsonArray);
                    mAdapter.addToList(subBreeds);

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
                    toast.show();
                    showErrorPage();
                }catch (NullPointerException e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
                    toast.show();
                    showErrorPage();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),"Connection - Error",Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    private void showErrorPage() {
        Intent intent = new Intent(SubBreeds.this,ErrorActivity.class);
        startActivity(intent);
    }

    private void parseBreed(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++){
            String subBreed = jsonArray.getString(i);
            subBreeds.add(new Breed(subBreed));
            Log.i(Constants.TAG,subBreed);

        }
    }
}
