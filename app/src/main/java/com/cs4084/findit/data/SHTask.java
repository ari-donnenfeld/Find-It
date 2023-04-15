package com.cs4084.findit.data;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class SHTask implements Serializable {
    public String description = "";
    public ArrayList<SHClue> clues = new ArrayList<>();

    public void addClue(SHClue clue) {
        clues.add(clue);
    }
    public void removeClue(int clue_pos) {
        clues.remove(clue_pos);
    }

    public String getName() {
        return "Nothing";
    }
}
