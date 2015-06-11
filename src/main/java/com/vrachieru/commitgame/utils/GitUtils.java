package com.vrachieru.commitgame.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitUtils
{
    /**
     * Get repository
     * 
     * @return
     * @throws IOException
     */
    public static Repository getRepository() throws IOException
    {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.readEnvironment().findGitDir().build();

        return repository;
    }

    /**
     * Get current branch
     * 
     * @param repository
     * @return
     */
    public static String getCurrentBranch(Repository repository)
    {
        try {
            return repository.getBranch();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get commiters
     * 
     * @param repository
     * @return
     */
    public static List<String> getCommiters(Repository repository)
    {
        try {
            Set<String> commiters = new TreeSet<String>();

            RevWalk revWalk = new RevWalk(repository);
            ObjectId HEAD = repository.resolve("HEAD");
            revWalk.markStart(revWalk.parseCommit(HEAD));

            Iterator<RevCommit> it = revWalk.iterator();
            while (it.hasNext()) {
                RevCommit commit = it.next();
                if (commit.getAuthorIdent() != null) {
                    commiters.add(getCommitAuthor(commit));
                }
            }

            revWalk.dispose();

            return new ArrayList<String>(commiters);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * Get commits
     * 
     * @param repository
     * @return
     */
    public static List<RevCommit> getCommits(Repository repository)
    {
        try {
            List<RevCommit> commits = new LinkedList<RevCommit>();

            RevWalk rw = new RevWalk(repository);
            ObjectId HEAD = repository.resolve("HEAD");
            rw.markStart(rw.parseCommit(HEAD));
            Iterator<RevCommit> it = rw.iterator();

            while (it.hasNext()) {
                RevCommit commit = it.next();
                commits.add(commit);
            }
            rw.dispose();
            return commits;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get commit date
     * 
     * @param commit
     * @return
     */
    public static String getCommitDate(RevCommit commit)
    {
        return Utils.timeAgo(commit.getCommitTime());
    }

    /**
     * Get commit message
     * 
     * @param commit
     * @return
     */
    public static String getCommitMessage(RevCommit commit)
    {
        return commit.getFullMessage();
    }

    /**
     * Get author display name
     * 
     * @param commit
     * @return
     */
    public static String getCommitAuthor(RevCommit commit)
    {
        PersonIdent person = commit.getAuthorIdent();

        return person.getName().trim();
    }
}
