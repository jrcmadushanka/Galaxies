package com.civ.galaxies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.civ.galaxies.api.APIInterface;
import com.civ.galaxies.api.client.APIClient;
import com.civ.galaxies.model.PlanetResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final APIInterface apiInterface = APIClient.getBasicClient().create(APIInterface.class);

    private final MutableLiveData<PlanetResponse> mutableLivePlanets = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasMoreData = new MutableLiveData<>();
    private final MutableLiveData<String> onError = new MutableLiveData<>();
    private int nextPage = 1;

    public LiveData<PlanetResponse> getPlanets() {
        return mutableLivePlanets;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<Boolean> hasMoreData() {
        return hasMoreData;
    }

    public LiveData<String> onError() {
        return onError;
    }

    public void fetchPlanetData() {
        isLoading.setValue(true);
        disposable.add(
                apiInterface.getAllPlanets(nextPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(planetResponse -> {
                            if (planetResponse.getResults() != null) {
                                mutableLivePlanets.postValue(planetResponse);

                                if (planetResponse.getNext() != null) {
                                    hasMoreData.postValue(true);
                                    nextPage++;
                                } else {
                                    hasMoreData.postValue(false);
                                }
                            } else {
                                onError.postValue("Couldn't find planet data.");
                            }
                            isLoading.postValue(false);
                        }, error -> {
                            error.printStackTrace();
                            isLoading.postValue(false);
                            onError.postValue(error.getLocalizedMessage());
                        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
