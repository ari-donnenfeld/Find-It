package com.cs4084.findit.data;

import java.util.ArrayList; // import the ArrayList class

public class SHTextTask extends SHTask {

    // This is just for use in the database
    // I have not found a way to remove it
    // But it should not be needed.
    public ArrayList<String> accepted_answers = new ArrayList<>();

    public void SHTextTask() {
    }

    public void addAnswer(String answer) {
        accepted_answers.add(answer);
    }
    public void removeAnswer(int answer_num) {
        accepted_answers.remove(answer_num);
    }

    @Override
    public String getName() {
        if (!accepted_answers.isEmpty()) {
            return accepted_answers.get(0);
        }
        return "New Text Task";
    }
    public void setName(String name) {
    }
}
