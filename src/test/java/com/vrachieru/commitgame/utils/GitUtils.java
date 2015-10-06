package com.vrachieru.commitgame.utils;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import org.junit.*;

public class GitUtils {
  public static File getRepositoryFile(String repoName) throws Exception {
    File localRepositoryDirectory = new File("target", "repository");
    File localGitRepositoryDirectory = new File(localRepositoryDirectory, "git");
    File localGitRepository = new File(localGitRepositoryDirectory, repoName);
    return localGitRepository;
  }

  public static boolean exists(String repoName) throws Exception {
    return getRepositoryFile(repoName).exists();
  }

  public static Repository init(String repoName) throws Exception {
    File localDirectory = getRepositoryFile(repoName);
    File gitDirectory = new File(localDirectory, ".git");
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository = builder.setGitDir(gitDirectory).readEnvironment().findGitDir().build();

    if (!gitDirectory.exists()) {
      repository.create();
    }

    return repository;
  }

  public static void add(File repositoryPath, String filePath, PersonIdent author,
      String commitMessage) throws Exception {
    File file = new File(repositoryPath.getParentFile(), filePath);
    Assert.assertTrue(FileUtils.createFile(file));

    Git git = Git.open(repositoryPath);
    git.add().addFilepattern(filePath).call();
    RevCommit commit =
        git.commit().setOnly(filePath).setMessage(commitMessage).setAuthor(author)
            .setCommitter(author).call();
    Assert.assertNotNull(commit);
  }
}
