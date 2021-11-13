package com.civ.galaxies.api;

import com.civ.galaxies.model.Planet;
import com.civ.galaxies.model.PlanetResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("planets")
    Single<PlanetResponse> getAllPlanets();
}
