package com.vrachieru.commitgame.repository;

import org.junit.Assert;
import org.junit.Test;

import com.vrachieru.commitgame.game.GameStatus;

public class GameStatusTest {
  @Test
  public void currentStreak() {
    GameStatus gameStatus = new GameStatus();

    gameStatus.addCorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addIncorrectAnswer();
    
    Assert.assertEquals(0, gameStatus.getCurrentStreak());
    
    gameStatus.addCorrectAnswer();
    gameStatus.addCorrectAnswer();
    
    Assert.assertEquals(2, gameStatus.getCurrentStreak());
    
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();

    Assert.assertEquals(1, gameStatus.getCurrentStreak());
  }

  @Test
  public void longestStreak() {
    GameStatus gameStatus = new GameStatus();

    gameStatus.addCorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();

    Assert.assertEquals(1, gameStatus.getLongestStreak());

    gameStatus.addCorrectAnswer();
    gameStatus.addCorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();
    gameStatus.addCorrectAnswer();

    Assert.assertEquals(3, gameStatus.getLongestStreak());

    gameStatus.addIncorrectAnswer();
    gameStatus.addIncorrectAnswer();

    Assert.assertEquals(3, gameStatus.getLongestStreak());
  }

  @Test
  public void successPercentage() {
    GameStatus gameStatus = new GameStatus();

    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();

    Assert.assertEquals(50.0, gameStatus.getSuccessPercentage(), 0.05);

    gameStatus.addIncorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();

    Assert.assertEquals(42.85, gameStatus.getSuccessPercentage(), 0.05);

    gameStatus.addCorrectAnswer();
    gameStatus.addCorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();

    Assert.assertEquals(54.54, gameStatus.getSuccessPercentage(), 0.05);
  }
  
  
  @Test
  public void lastAnswerWasCorrect() {
    GameStatus gameStatus = new GameStatus();
    
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();
    gameStatus.addCorrectAnswer();
    
    Assert.assertTrue(gameStatus.isLastAnswerCorrect());
    
    gameStatus.addIncorrectAnswer();
    
    Assert.assertFalse(gameStatus.isLastAnswerCorrect());
    
    gameStatus.addCorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();
    
    Assert.assertTrue(gameStatus.isLastAnswerCorrect());
    
    gameStatus.addIncorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addIncorrectAnswer();
    gameStatus.addCorrectAnswer();
    
    Assert.assertTrue(gameStatus.isLastAnswerCorrect());
  }

}
