package com.civ.galaxies.api;

import com.civ.galaxies.model.PlanetResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("planets")
    Single<PlanetResponse> getAllPlanets(@Query(value = "page") int page);
}
