package com.example.ramon.tareafinal.DogAPI;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service{

    @GET(Constants.LIST_ALL_BREEDS)
    Call<JsonObject> getAllBreeds();

    @GET(Constants.RANDOM_IMAGE)
    Call<JsonObject> getRandomImage();

    @GET(Constants.BY_BREED + "{breed}/images")
    Call<JsonObject> getByBreed(@Path("breed") String breed);

    @GET(Constants.BY_BREED + "{breed}/{subBreed}/images")
    Call<JsonObject> getBySubBreed(@Path("breed") String breed, @Path("subBreed") String subBreed);
}
