package unhas.informatics.moviecatalogue.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import unhas.informatics.moviecatalogue.BuildConfig;

public class ApiClient {
  private static Retrofit retrofit = null;

  public static Retrofit getClient() {

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
      @Override
      public okhttp3.Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl httpUrl = originalRequest.url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        originalRequest = originalRequest.newBuilder()
                .url(httpUrl)
                .build();

        return chain.proceed(originalRequest);
      }
    }).build();

    if (retrofit==null) {
      retrofit = new Retrofit.Builder()
              .client(okHttpClient)
              .baseUrl(BuildConfig.API_BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
    }
    return retrofit;
  }

}
