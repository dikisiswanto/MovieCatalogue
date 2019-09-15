package unhas.informatics.moviecatalogue;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unhas.informatics.moviecatalogue.api.ApiClient;
import unhas.informatics.moviecatalogue.api.ApiService;
import unhas.informatics.moviecatalogue.model.Movie;
import unhas.informatics.moviecatalogue.model.MovieResponse;

public class MainViewModel extends ViewModel {
  private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();

  public void setMovies(String movieType) {
	ApiService apiService = ApiClient.getClient().create(ApiService.class);
	Call<MovieResponse> call = apiService.getMovies(movieType);
	call.enqueue(new Callback<MovieResponse>() {
	  @Override
	  public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
	    try {
		  ArrayList<Movie> movies = response.body().getResults();
		  movieList.postValue(movies);
	    } catch (Exception e){

		}
	  }

	  @Override
	  public void onFailure(Call<MovieResponse> call, Throwable t) {
		t.printStackTrace();
	  }
	});
  }

  public LiveData<ArrayList<Movie>> getMovies() {
    return movieList;
  }
}
