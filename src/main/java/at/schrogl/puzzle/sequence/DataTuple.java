package at.schrogl.puzzle.sequence;

import java.util.Objects;

class DataTuple {

    final String input;
    final String expected;

    DataTuple(String input, String expected) {
        this.input = Objects.requireNonNull(input);
        this.expected = Objects.requireNonNull(expected);
    }

    boolean assertCompressed(String actual) {
        return expected.equalsIgnoreCase(actual);
    }
}
