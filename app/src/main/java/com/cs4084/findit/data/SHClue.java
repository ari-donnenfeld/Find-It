package com.cs4084.findit.data;

import java.io.Serializable;

public class SHClue implements Serializable {
    public String description;
    public int penalty;

    public void Clue(String description, int penalty) {
        this.description = description;
        this.penalty = penalty;
    }
}
