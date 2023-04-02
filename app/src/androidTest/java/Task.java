import java.util.ArrayList;

public interface Task {
    public String description = new String();
    public ArrayList<Clue> clues = new ArrayList<>();
    public default void addClue(Clue clue) {
        clues.add(clue);
    }
    public default void removeClue(int clue_pos) {
        clues.remove(clue_pos);
    }
}
