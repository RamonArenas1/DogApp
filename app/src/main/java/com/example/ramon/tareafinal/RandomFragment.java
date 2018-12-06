package com.example.ramon.tareafinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ramon.tareafinal.Adapter.ImageAdapter;
import com.example.ramon.tareafinal.DogAPI.Constants;
import com.example.ramon.tareafinal.DogAPI.RetrofitAdapter;
import com.example.ramon.tareafinal.DogAPI.Service;
import com.example.ramon.tareafinal.Model.Dog;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RandomFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Dog> dogs;
    private ImageAdapter mAdapter;


    public RandomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_random, container, false);

        mRecyclerView = view.findViewById(R.id.randomRecycler);
        dogs = new ArrayList<>();

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ImageAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        addElements();

        return view;
    }

    private void addElements() {
        RetrofitAdapter restApiAdapter = new RetrofitAdapter();
        Service service = restApiAdapter.getCharacterService();
        retrofit2.Call<JsonObject> call = service.getRandomImage();

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

    private void showErrorPage() {
        Intent intent = new Intent(getActivity(),ErrorActivity.class);
        getActivity().startActivity(intent);
    }

    private void parseBreed(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++){
            String image = jsonArray.getString(i);
            dogs.add(new Dog(image));
            Log.i(Constants.TAG,image);

        }
    }

}
