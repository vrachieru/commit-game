package com.vrachieru.commitgame.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.vrachieru.commitgame.utils.Utils;

public class Repository {
  protected String repository;

  protected String branch;

  protected List<Commit> commits;

  protected Set<String> commiters;

  public Repository() {
    this.repository = "unknown";
    this.branch = "unknown";
    this.commits = new ArrayList<Commit>();
    this.commiters = new TreeSet<String>();
  }

  public Repository(String repository, String branch, List<Commit> commits, Set<String> commiters) {
    this.repository = repository;
    this.branch = branch;
    this.commits = commits;
    this.commiters = commiters;
  }

  public void displayInfo() {
    System.out.println("You're playing in a repo with " + this.getNumberOfCommits()
        + " commits and " + this.getNumberOfCommiters() + " committers.\n");
  }

  public void displayCommiters() {
    int i = 0;
    for (String commiter : this.getCommiters()) {
      System.out.println("[" + (++i) + "] " + commiter);
    }
  }

  public String getBranch() {
    return this.branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public List<Commit> getCommits() {
    return this.commits;
  }

  public int getNumberOfCommits() {
    return getCommits().size();
  }

  public Commit getRandomCommit() {
    return this.commits.get(Utils.getRandomNumber(getNumberOfCommits()));
  }

  public void addCommit(Commit commit) {
    commits.add(commit);
  }

  public Set<String> getCommiters() {
    return this.commiters;
  }

  public int getNumberOfCommiters() {
    return getCommiters().size();
  }

  public void addCommiter(String commiter) {
    commiters.add(commiter);
  }
}
