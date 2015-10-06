package com.vrachieru.commitgame.repository;

import java.io.File;
import java.util.Iterator;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import com.vrachieru.commitgame.utils.Utils;

public class GitRepository extends com.vrachieru.commitgame.repository.Repository {
  private org.eclipse.jgit.lib.Repository repository;

  public GitRepository() {
    this(null);
  }
  
  public GitRepository(File repositoryPath) {
    super();
    
    this.parseRepository(repositoryPath);
    this.parseBranch();
    this.parseCommitsAndCommiters();
  }

  private void parseRepository(File repositoryPath) {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    
    try {
      if (repositoryPath != null) {
        this.repository = builder.readEnvironment().findGitDir(repositoryPath).build();
      } else {
        this.repository = builder.readEnvironment().findGitDir().build();
      }
    } catch (Exception ex) {
      System.out.println("Could not determine a git repository in the current path.");
      System.exit(1);
    }
  }

  private void parseBranch() {
    String branch;

    try {
      branch = this.repository.getBranch();
    } catch (Exception e) {
      branch = "unknown";
    }

    setBranch(branch);
  }

  private void parseCommitsAndCommiters() {
    try {
      RevWalk revWalk = new RevWalk(repository);
      ObjectId HEAD = this.repository.resolve("HEAD");
      revWalk.markStart(revWalk.parseCommit(HEAD));

      Iterator<RevCommit> it = revWalk.iterator();
      while (it.hasNext()) {
        RevCommit revCommit = it.next();

        Commit commit = new Commit();
        commit.setAuthor(getCommitAuthor(revCommit));
        commit.setDate(getCommitDate(revCommit));
        commit.setMessage(getCommitMessage(revCommit));

        this.commits.add(commit);
        this.commiters.add(commit.getAuthor());
      }

      revWalk.dispose();
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  private String getCommitAuthor(RevCommit commit) {
    PersonIdent person = commit.getAuthorIdent();
    return person.getName().trim();
  }

  private String getCommitDate(RevCommit commit) {
    return Utils.timeAgo(commit.getCommitTime());
  }

  private String getCommitMessage(RevCommit commit) {
    return commit.getFullMessage();
  }
}
