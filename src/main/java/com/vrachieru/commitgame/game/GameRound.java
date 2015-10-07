package com.vrachieru.commitgame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.vrachieru.commitgame.repository.Commit;

public class GameRound {
  private Commit commit;

  private String answer;

  private String answerKey;

  private List<String> choices;
  
  public GameRound() {
    // nothing here
  }
  
  public GameRound(Commit commit, String answer, String answerKey, List<String> choices) {
    this.commit = commit;
    this.answer = answer;
    this.answerKey = answerKey;
    this.choices = choices;
  }

  public String getAnswer() {
    return this.answer;
  }

  public void computeNewRound() {
    chooseRandomCommit();
    extractAnswerFromCommit();
    computeListOfAnswerChoices();
    extractAnswerKeyFromChoices();
  }

  private void chooseRandomCommit() {
    this.commit = Game.getRepository().getRandomCommit();
  }

  private void extractAnswerFromCommit() {
    this.answer = this.commit.getAuthor();
  }

  private void computeListOfAnswerChoices() {
    Set<String> commiters = Game.getRepository().getCommiters();

    this.choices = new ArrayList<String>(commiters);
    this.choices.remove(this.answer);
    Collections.shuffle(choices);

    this.choices = this.choices.subList(0, choices.size() > 3 ? 4 : choices.size());
    this.choices.add(this.answer);
    Collections.shuffle(choices);
  }

  private void extractAnswerKeyFromChoices() {
    this.answerKey = this.choices.indexOf(this.answer) + 1 + "";
  }

  public void display() {
    this.displayCommit();
    this.displayChoices();
  }

  private void displayCommit() {
    System.out.println("\n(" + this.commit.getDate() + ")");
    System.out.println(this.commit.getMessage());
  }

  private void displayChoices() {
    int i = 1;
    for (String choice : this.choices) {
      System.out.println("[" + i++ + "] " + choice);
    }

    System.out.println();
  }

  public boolean isAnswerCorrect(String providedAnswerKey) {
    return this.answerKey.equals(providedAnswerKey);
  }
}
