package com.vrachieru.commitgame.game;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import com.vrachieru.commitgame.utils.*;

public class Game
{
    private static Repository repository = null;

    private static List<RevCommit> commits = null;

    private static List<String> commiters = null;

    private static String input = null;

    private static Scanner inputReader = new Scanner(System.in);

    private static int total = 0;

    private static int guessed = 0;

    private static int currentStreak = 0;

    private static int longestStreak = 0;

    /**
     * Initialize the game
     */
    public static void init()
    {
        try {
            repository = GitUtils.getRepository();

            commits = GitUtils.getCommits(repository);
            commiters = GitUtils.getCommiters(repository);

            welcome();
        } catch (IOException e) {
            System.out.println("The game could not be started!");
            System.out.println("Please make sure that your current path is a repository.");
        }
    }
    
    /**
     * Get input from the player
     */
    public static void getInput() {
        input = inputReader.nextLine();
        
        if (input.equals("quit")){
            System.exit(0);
        }
    }

    /**
     * Display welcome screen
     */
    public static void welcome()
    {
        System.out.println("--------------------------------------------------------------");
        System.out.println("                      THE COMMIT GAME                         ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Welcome! The goal of this game is to guess committers based on");
        System.out.println("their commit messages.\n");

        System.out.println("You're playing in a repo with " + commits.size() + " commits and " + commiters.size()
            + " committers.\n");

        showCommiters();
        startGame();
    }

    /**
     * Show full list of commiters for the repository
     */
    public static void showCommiters()
    {
        int i = 0;
        for (String commiter : commiters) {
            System.out.println("[" + (++i) + "] " + commiter);
        }
    }

    /**
     * Start the game
     */
    public static void startGame()
    {
        System.out.println("\nReady? PRESS ENTER TO START PLAYING (type 'quit' to exit)");

        getInput();
        playGame();
    }

    /**
     * Play the game
     */
    public static void playGame()
    {
        while (true) {
            System.out.println("\n----------------------------------------------------------\n");

            System.out.println("Playing in the " + GitUtils.getCurrentBranch(repository) + " branch.");

            System.out.println("The current streak is " + currentStreak + " with the longest streak being "
                + longestStreak + " and an overall guess rate of " + Utils.successPercentage(guessed, total)
                + "% (" + guessed + "/" + total + ").");

            String author = getEntry();
            String answer = getChoices(author);

            System.out.print("Choose your answer:\n> ");
            getInput();

            if (answer.equals(input)) {
                guessed++;
                currentStreak++;

                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak;
                }

                if (currentStreak == 2) {
                    System.out.println("Two in a row! Good job!");
                } else if (currentStreak == 3) {
                    System.out.println("Lucky number three!");
                } else if (currentStreak == 4) {
                    System.out.println("You're un a roll!");
                } else if (currentStreak == 5) {
                    System.out.println("That's amazing!");
                } else if (currentStreak == 6) {
                    System.out.println("This is getting ridiculous! I'm not even counting anymore.");
                    System.out.println("Keep up the good work!");
                } else {
                    System.out.println("Got it!");
                }
            } else {
                currentStreak = 0;
                System.out.println("Actually it was " + author);
            }

            total++;
        }
    }

    /**
     * Get the choices list for each commit
     * 
     * @param answer the name of the author assigned to the commit
     * @return the index of the correct answer
     */
    public static String getChoices(String answer)
    {
        List<String> choices = commiters;
        choices.remove(answer);
        Collections.shuffle(choices);

        choices = choices.subList(0, choices.size() > 3 ? 4 : choices.size());
        choices.add(answer);
        Collections.shuffle(choices);

        for (int i = 0; i < choices.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + choices.get(i));
        }
        System.out.println();

        return choices.indexOf(answer) + 1 + "";

    }

    /**
     * Get a new random entry to feed to the player
     * 
     * @return the author of the commit representing the correct answer
     */
    public static String getEntry()
    {
        RevCommit commit = getRandomCommit();

        System.out.println("\n(" + GitUtils.getCommitDate(commit) + ")");
        System.out.println(GitUtils.getCommitMessage(commit));

        return GitUtils.getCommitAuthor(commit);
    }

    /**
     * Get a random commit from the list
     * 
     * @return raw commit
     */
    public static RevCommit getRandomCommit()
    {
        return commits.get(Utils.getRandomNumber(commits.size()));
    }
}
