package at.schrogl.puzzle.sequence;

import java.util.ArrayList;
import java.util.List;

public class SequenzPuzzleApplication {

    private final List<DataTuple> input = new ArrayList<>();

    public SequenzPuzzleApplication(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            input.add(new DataTuple(args[i], args[i + 1]));
        }

        input.add(new DataTuple("aaabbccccdd", "a3b2c4d2"));
        input.add(new DataTuple("aaabbcce", "a3b2c2e1"));
    }

    public static void main(String[] args) {
        if (args != null && args.length % 2 != 0) {
            throw new IllegalArgumentException("The quantity of arguments given must be even");
        }

        new SequenzPuzzleApplication(args != null ? args : new String[0]).startTest();
    }

    private void startTest() {
        CompressWorker compressWorker = new CompressWorker();

        for (DataTuple tuple : input) {
            System.out.println(String.format("# Example: %s -> %s", tuple.input, tuple.expected));
            compressWorker.compressAndLogUsingAll(tuple);
            System.out.println();
        }
    }

}
