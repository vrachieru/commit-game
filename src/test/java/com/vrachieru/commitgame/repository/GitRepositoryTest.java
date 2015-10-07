package com.vrachieru.commitgame.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.lib.PersonIdent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vrachieru.commitgame.utils.FileUtils;
import com.vrachieru.commitgame.utils.GitUtils;

public class GitRepositoryTest {
  private static final String TEST_REPO = "test-repo";

  private File repositoryPath;

  @Before
  public void setupTestRepository() throws Exception {
    createGitRepository();
    createCommits();
  }

  private void createGitRepository() throws Exception {
    FileUtils.deleteDirectory(GitUtils.getRepositoryFile(TEST_REPO));
    this.repositoryPath = GitUtils.init(TEST_REPO).getDirectory();
  }

  private void createCommits() throws Exception {
    List<PersonIdent> commiters = new ArrayList<PersonIdent>();
    commiters.add(new PersonIdent("Peter Parker", "peter.parker@domain.com"));
    commiters.add(new PersonIdent("Clark Kent", "clark.kent@domain.com"));
    commiters.add(new PersonIdent("Diana Price", "diana.price@domain.com"));
    commiters.add(new PersonIdent("Bruce Wayne", "bruce.wayne@domain.com"));
    commiters.add(new PersonIdent("Shayera Hol", "shayera.hol@domain.com"));

    GitUtils.commit(this.repositoryPath, "spidey", commiters.get(0), "My spidey senses are tingling.");
    GitUtils.commit(this.repositoryPath, "kripton", commiters.get(1), "It's not an 'S', in my world it means hope.");
    GitUtils.commit(this.repositoryPath, "themyscira", commiters.get(2), "It'm a princess, dammit.");
    GitUtils.commit(this.repositoryPath, "chemistry", commiters.get(0), "I have no idea what I'm doing.");
    GitUtils.commit(this.repositoryPath, "bats", commiters.get(3), "I'm batman.");
  }
  
  @Test
  public void getBranch() {
    GitRepository gitRepository = new GitRepository(repositoryPath);
    
    Assert.assertEquals("master", gitRepository.getBranch());
  }
  
  @Test
  public void getCommiters(){
    GitRepository gitRepository = new GitRepository(repositoryPath);

    Set<String> commiters = gitRepository.getCommiters();
    
    boolean commiterFound = false;
    
    for (String commiter : commiters) {
      if ("Bruce Wayne".equals(commiter)) {
        commiterFound = true;
        break;
      }
    }
    
    Assert.assertTrue(commiterFound);
  }
  
  @Test
  public void getCommits() {
    GitRepository gitRepository = new GitRepository(repositoryPath);

    List<Commit> commits = gitRepository.getCommits();
    
    boolean commitFound = false;
    
    for (Commit commit : commits) {
      if (commit.getAuthor().equals("Diana Price") && commit.getMessage().equals("It'm a princess, dammit.")) {
        commitFound = true;
      }
    }
    
    Assert.assertTrue(commitFound);
  }
  
  @Test
  public void getNumberOfCommiters() {
    GitRepository gitRepository = new GitRepository(repositoryPath);

    Set<String> commiters = gitRepository.getCommiters();
    Assert.assertEquals(commiters.size(), gitRepository.getNumberOfCommiters());

    Assert.assertEquals(4, gitRepository.getNumberOfCommiters());
  }

  @Test
  public void getNumberOfCommits() {
    GitRepository gitRepository = new GitRepository(repositoryPath);

    List<Commit> commits = gitRepository.getCommits();
    Assert.assertEquals(commits.size(), gitRepository.getNumberOfCommits());

    Assert.assertEquals(5, gitRepository.getNumberOfCommits());
  }
}
