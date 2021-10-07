package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

//Main Class
public class GameRepository {

  private final WebServiceProxy proxy;

  public GameRepository() {
    proxy = WebServiceProxy.getInstance();
  }

  //Game Method.
  public Game startGame(String pool, int length) throws IOException, BadGameException {
    Game game = new Game();
    game.setPool(pool);
    game.setLength(length);
    Call<Game> call = proxy.startGame(game);
    Response<Game> response = call.execute();
    if (!response.isSuccessful()) {
      throw new BadGameException(response.message());
    }
    return response.body();
  }

  //Guess Method.
  public Guess submitGuess(Game game, String text) throws IOException, BadGuessException {
    Guess guess = new Guess();
    guess.setText(text);
    Response<Guess> response = proxy
        .submitGuess(guess, game.getId())
        .execute();
    //ALWAYS parenthesis after an if statement
    if (!response.isSuccessful()) {
      throw new BadGuessException(response.message());
    }
    return response.body();
  }

  //Nested Class.
  public static class BadGuessException extends IllegalArgumentException {

    //This inherits all of IllegalArgsException traits
    public BadGuessException(String message) {
      super(message);
    }
  }

  //Constructor name must match class name w/out exception
  public static class BadGameException extends IllegalArgumentException {

    //This inherits all of IllegalArgsException traits
    public BadGameException(String message) {
      super(message);
    }
  }

}
