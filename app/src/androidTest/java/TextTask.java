import java.util.ArrayList; // import the ArrayList class

public class TextTask implements Task {
    public ArrayList<String> accepted_answers = new ArrayList<>();

    public void TextTask() {
    }

    public void addAnswer(String answer) {
        accepted_answers.add(answer);
    }
    public void removeAnswer(int answer_num) {
        accepted_answers.remove(answer_num);
    }
}
