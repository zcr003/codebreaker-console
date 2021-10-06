package edu.cnm.deepdive.codebreaker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceProxy {

  @POST("codes")
  Call<Game> startGame(@Body Game game);

  @POST("codes/{gameId}/guesses")
  Call<Guess> submitGuess(@Body Guess guess, @Path("gameId") String gameId);


  static WebServiceProxy getInstance() {
    return InstanceHolder.INSTANCE;

  }


  class InstanceHolder {

    private static final WebServiceProxy INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
          .create();

      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://ddc-java.services/codebreaker/")
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build();
      INSTANCE = retrofit.create(WebServiceProxy.class);
    }

  }
}
