package com.cs4084.findit.data;

public class SHClue {
    public String description;
    public int penalty;

    public void Clue(String description, int penalty) {
        this.description = description;
        this.penalty = penalty;
    }
}
