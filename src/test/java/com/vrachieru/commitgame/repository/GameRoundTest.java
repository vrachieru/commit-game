package com.vrachieru.commitgame.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vrachieru.commitgame.game.GameRound;

public class GameRoundTest {

  @Test
  public void isAnswerCorrect() {
    Commit commit = new Commit();
    commit.setDate("(25 years, 6 months ago)");
    commit.setAuthor("Pablo Escobar");
    commit.setMessage("Plata o plomo");
    
    String answer = "Pablo Emilio Escobar Gaviria";
    
    List<String> choices = new ArrayList<String>();
    choices.add("Juan Pablo Escobar");
    choices.add("Luis Carlos Galan");
    choices.add(answer);
    choices.add("Jorge Luis Ochoa Vasquez");
    choices.add("Jose Gonzalo Rodrguez Gacha");
    
    String answerKey = (choices.indexOf(answer) + 1) + "";
    
    GameRound gameRound = new GameRound(commit, answer, answerKey, choices);
    
    Assert.assertFalse(gameRound.isAnswerCorrect("1"));
    Assert.assertFalse(gameRound.isAnswerCorrect("2"));
    Assert.assertTrue(gameRound.isAnswerCorrect(answerKey));
    Assert.assertFalse(gameRound.isAnswerCorrect("4"));
    Assert.assertFalse(gameRound.isAnswerCorrect("5"));
  }
}
