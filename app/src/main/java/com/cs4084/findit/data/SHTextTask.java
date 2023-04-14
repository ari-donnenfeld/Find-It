package com.cs4084.findit.data;

import java.util.ArrayList; // import the ArrayList class

public class SHTextTask extends SHTask {
    public ArrayList<String> accepted_answers = new ArrayList<>();

    public void SHTextTask() {
    }

    public void addAnswer(String answer) {
        accepted_answers.add(answer);
    }
    public void removeAnswer(int answer_num) {
        accepted_answers.remove(answer_num);
    }
}
