package unhas.informatics.moviecatalogue.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import unhas.informatics.moviecatalogue.model.MovieResponse;

public interface ApiEndpoints {
  @GET("discover/{type}")
  Call<MovieResponse> getMovies(@Path("type") String movieType);
}
