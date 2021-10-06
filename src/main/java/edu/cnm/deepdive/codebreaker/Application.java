package edu.cnm.deepdive.codebreaker;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.service.WebServiceProxy;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class Application {

  public Application() throws IOException {
  }

  public static void main(String[] args) throws IOException {

  // TODO Read command-line args for pool & length.
    String pool = "ABCDEF"; //FIXME Read from args
    int length = 3; //FIXME Read from args
    Game game = startGame (pool, length);
    System.out.printf("Game id = %s%n", game.getId());
    // TODO While code is not guessed:
    //    1.Read guess from user input
    //    2.Submit guess to codebreaker service
    //    3. Display guess results

  }




  private static Game startGame(String pool, int length) throws IOException {
    WebServiceProxy proxy = WebServiceProxy.getInstance();
    Game game = new Game();
    game.setPool(pool);
    game.setLength(length);
    Call<Game> call = proxy.startGame(game);
    Response<Game> response = call.execute();
    if (!response.isSuccessful()) {
      throw new RuntimeException(response.message());
    }
      return response.body();

  }
}
