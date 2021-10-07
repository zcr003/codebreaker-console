package edu.cnm.deepdive.codebreaker;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.service.GameRepository;
import edu.cnm.deepdive.codebreaker.service.WebServiceProxy;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class Application {

  //These are static final i.e. constants
  private static final String DEFAULT_POOL = "ABCDEF";
  private static final int DEFAULT_LENGTH = 3;

  //This group would be static non-final (if there was one)

  //This group is final but non-static.
  private final GameRepository repository;

  //neither static nor final
  private Game game;

  //This is a constructor
  private Application(String[] args) throws IOException {
    String pool = DEFAULT_POOL;
    int length = DEFAULT_LENGTH;

    //This is basic form of a switch statement [switch (args.length) w/ braces]
    switch (args.length) {
      // checks if we have any cases, if so it moves on
      default:
        //Deliberate fall-through!
      case 2:
        length = Integer.parseInt(args[1]);
        //Deliberate fall-through!
      case 1:
        pool = args[0];
        break;
      case 0:
        //Do nothing.
    }
    repository = new GameRepository();
    startGame(pool, length);
  }

  private void startGame(String pool, int length) throws IOException {
    game = repository.startGame(pool, length);
  }

  //This runs outside an instance
  public static void main(String[] args) throws IOException {
    Application application = new Application(args);
    // TODO While code is not guessed:
    //    1.Read guess from user input
    //    2.Submit guess to codebreaker service
    //    3. Display guess results

  }


}
