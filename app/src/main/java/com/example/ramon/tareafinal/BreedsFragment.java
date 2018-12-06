package com.example.ramon.tareafinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ramon.tareafinal.Adapter.BreedAdapter;
import com.example.ramon.tareafinal.DogAPI.Constants;
import com.example.ramon.tareafinal.DogAPI.RetrofitAdapter;
import com.example.ramon.tareafinal.DogAPI.Service;
import com.example.ramon.tareafinal.Model.Breed;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BreedsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Breed> breeds;
    private BreedAdapter mAdapter;


    public BreedsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breeds, container, false);

        mRecyclerView = view.findViewById(R.id.breedsList);
        breeds = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BreedAdapter();
        mRecyclerView.setAdapter(mAdapter);

        addElements();
        changeActivity();

        return view;
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
                    Toast toast = Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT);
                    toast.show();
                    showErrorPage();
                }catch (NullPointerException e){
                    Toast toast = Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT);
                    toast.show();
                    showErrorPage();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),"Connection - Error",Toast.LENGTH_SHORT);
                toast.show();
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

    private void showErrorPage() {
        Intent intent = new Intent(getActivity(),ErrorActivity.class);
        getActivity().startActivity(intent);
    }

    public void changeActivity(){
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String breed = breeds.get(mRecyclerView.getChildAdapterPosition(view)).getName();


                Intent intent = new Intent(getActivity(), SubBreeds.class);
                intent.putExtra("stringBreed",breed);
                getActivity().startActivity(intent);
            }
        });
    }

}
