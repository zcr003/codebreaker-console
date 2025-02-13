package edu.cnm.deepdive.codebreaker;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import edu.cnm.deepdive.codebreaker.service.GameRepository;
import edu.cnm.deepdive.codebreaker.service.GameRepository.BadGameException;
import edu.cnm.deepdive.codebreaker.service.GameRepository.BadGuessException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Application {

  //These are static final i.e. constants
  private static final String DEFAULT_POOL = "ABCDEF";
  private static final int DEFAULT_LENGTH = 3;

  //This group would be static non-final (if there was one)

  //This group is final but non-static.
  private final GameRepository repository;
  private final String pool;
  private final int length;
  private final Scanner scanner;
  private final ResourceBundle bundle;

  //neither static nor final
  private Game game;

  //This is a constructor
  private Application(String[] args) {
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

    // [this] statement is the identifier for current instance of this class.
    this.pool = pool;
    this.length = length;
    scanner = new Scanner(System.in);
    bundle = ResourceBundle.getBundle("strings");
  }

  // This is our main method.
  public static void main(String[] args) {
    Application application = new Application(args);

    //Try-catch method that jumps down to line 69 and throws IO ex for invalid entry of connection failure.
    try {
      application.startGame();
      boolean solved = false;
      //Do-while loop - Check condition, if it is not (!)solved then repeats until it is.
      do {
        try {
          String text = application.getGuess();
          Guess guess = application.submitGuess(text);
          application.printGuessResults(guess);
          solved = guess.isSolution();
        } catch (BadGuessException e) {
          System.out.println(application.bundle.getString("invalid_guess"));
        }
      } while (!solved);
    } catch (IOException e) {
      System.out.println(application.bundle.getString("io_exception"));
    } catch (BadGameException e) {
      System.out.println(application.bundle.getString("invalid_game"));
    }
  }

  //Declaration of the startGame method
  private void startGame() throws IOException, BadGameException {
    game = repository.startGame(pool, length);
  }

  //Output to the user
  private String getGuess() {
    System.out.printf(bundle.getString("guess_prompt"),
        game.getLength(), game.getPool());
    return scanner.next().trim();
  }

  //Input from the user
  private Guess submitGuess(String text) throws IOException, BadGuessException {
    return repository.submitGuess(game, text);
  }

  //Result output back to the user
  private void printGuessResults(Guess guess) {
    System.out.printf(bundle.getString("guess_results"),
        guess.getText(), guess.getExactMatches(), guess.getNearMatches());
    if (guess.isSolution()) {
      System.out.println(bundle.getString("solution"));
    }
  }

}
