package com.vrachieru.commitgame.game;

import java.util.Scanner;

import com.vrachieru.commitgame.repository.GitRepository;
import com.vrachieru.commitgame.repository.Repository;
import com.vrachieru.commitgame.utils.Utils;

public class Game {
  private static Repository repository;

  private static Game instance;

  private static GameStatus status;

  private static GameRound round;

  private static Scanner scanner;

  private static String input;

  private Game() {
    repository = new GitRepository();
    status = new GameStatus();
    round = new GameRound();
    scanner = new Scanner(System.in);

    welcome();
    start();
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }

    return instance;
  }

  public static Repository getRepository() {
    return repository;
  }

  public static GameRound getRound() {
    return round;
  }

  public static void welcome() {
    displayWelcomeMessage();

    repository.displayInfo();
    repository.displayCommiters();
  }

  public static void displayWelcomeMessage() {
    System.out.println("--------------------------------------------------------------");
    System.out.println("                      THE COMMIT GAME                         ");
    System.out.println("--------------------------------------------------------------");
    System.out.println("Welcome! The goal of this game is to guess committers based on");
    System.out.println("their commit messages.\n");
  }

  public static void start() {
    getUserInput("\nReady? PRESS ENTER TO START PLAYING (type 'q' or 'quit' to exit)\n");
    play();
  }

  public static void play() {
    while (true) {
      status.displayGlobalStatus();

      round.computeNewRound();
      round.display();

      getUserInput("Choose your answer:\n> ");

      if (round.isAnswerCorrect(input)) {
        status.addCorrectAnswer();
      } else {
        status.addIncorrectAnswer();
      }

      status.displayRoundStatus();
    }
  }

  private static void getUserInput(String displayMessage) {
    System.out.print(displayMessage);

    input = scanner.nextLine();

    if (input.toLowerCase().startsWith("q")) {
      scanner.close();
      System.exit(0);
    }
  }

}
