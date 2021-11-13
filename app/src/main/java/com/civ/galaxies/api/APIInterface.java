package com.civ.galaxies.api;

import com.civ.galaxies.model.PlanetResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("planets")
    Single<PlanetResponse> getAllPlanets();
}
