package com.vrachieru.commitgame.repository;

public class Commit
{
    private String author;

    private String date;

    private String message;

    public Commit()
    {
        // nothing here
    }

    public Commit(String author, String date, String message)
    {
        this.author = author;
        this.date = date;
        this.message = message;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getDate()
    {
        return this.date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
