package com.vrachieru.commitgame.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    commiters.add(new PersonIdent("John Doe", "john.doe@domain.com"));

    GitUtils.add(this.repositoryPath, "test.txt", commiters.get(0), "Add test file");
  }

  @Test
  public void getNumberOfCommits() {
    GitRepository gitRepository = new GitRepository(repositoryPath);

    List<Commit> commits = gitRepository.getCommits();
    Assert.assertEquals(commits.size(), gitRepository.getNumberOfCommits());

    Assert.assertEquals(1, gitRepository.getNumberOfCommits());
  }
}
