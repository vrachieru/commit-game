package com.vrachieru.commitgame.game;

import com.vrachieru.commitgame.utils.Utils;

public class GameStatus
{
    private int totalAnswers;

    private int correctAnswers;

    private int currentStreak;

    private int longestStreak;

    private boolean lastAnswerWasCorrect;

    public GameStatus()
    {
        this.totalAnswers = 0;
        this.correctAnswers = 0;
        this.currentStreak = 0;
        this.longestStreak = 0;
    }

    public void display()
    {
        this.displayRoundStatus();
        Utils.sleep(1);
        Utils.clearConsole();
        this.displayGlobalStatus();
    }

    private void displayRoundStatus()
    {
        if (this.lastAnswerWasCorrect) {
            switch (this.currentStreak) {
                case 2:
                    System.out.println("Two in a row! Good job!");
                    break;
                case 3:
                    System.out.println("Lucky number three!");
                    break;
                case 4:
                    System.out.println("You're un a roll!");
                    break;
                case 5:
                    System.out.println("That's amazing!");
                    break;
                case 6:
                    System.out.println("This is getting ridiculous! I'm not even counting anymore.");
                    System.out.println("Keep up the good work!");
                    break;
                default:
                    System.out.println("Got it!");
            }
        } else {
            System.out.println("Actually it was " + Game.getRound().getAnswer());
        }
    }

    private void displayGlobalStatus()
    {
        System.out.println("Playing in the " + Game.getRepository().getBranch() + " branch.");
        System.out.println("The current streak is " + this.getCurrentStreak() + " with the longest streak being "
            + this.getLongestStreak() + " and an overall guess rate of " + this.getSuccessPercentage() + "% ("
            + this.getCorrectAnswers() + "/" + this.getTotalAnswers() + ").");
    }

    public int getTotalAnswers()
    {
        return this.totalAnswers;
    }

    public int getCorrectAnswers()
    {
        return this.correctAnswers;
    }

    public double getSuccessPercentage()
    {
        return Utils.successPercentage(this.correctAnswers, this.totalAnswers);
    }

    public int getCurrentStreak()
    {
        return this.currentStreak;
    }

    public int getLongestStreak()
    {
        return this.longestStreak;
    }

    private void setLongestStreak()
    {
        if (this.currentStreak > this.longestStreak) {
            this.longestStreak = this.currentStreak;
        }
    }

    public void addCorrectAnswer()
    {
        this.totalAnswers++;
        this.correctAnswers++;
        this.currentStreak++;

        this.lastAnswerWasCorrect = true;

        this.setLongestStreak();
    }

    public void addIncorrectAnswer()
    {
        this.totalAnswers++;
        this.currentStreak = 0;

        this.lastAnswerWasCorrect = false;
    }
}
