package at.schrogl.puzzle.sequence;

public class CompressWorker {

    public void compressAndLogUsingAll(DataTuple tuple) {
        String actual = compressUsingLoop(tuple.input);
        System.out.println(String.format("\t%s\t\t= %s (%s)", "loop", actual, tuple.assertCompressed(actual) ? "OK" : "NOK"));

        actual = compressUsingStreams(tuple.input);
        System.out.println(String.format("\t%s\t\t= %s (%s)", "streams", actual, tuple.assertCompressed(actual) ? "OK" : "NOK"));

        actual = compressUsingMapReduce(tuple.input);
        System.out.println(String.format("\t%s\t= %s (%s)", "mapReduce", actual, tuple.assertCompressed(actual) ? "OK" : "NOK"));
    }

    public String compressUsingLoop(String input) {
        StringBuilder compressedInput = new StringBuilder();

        char currentChar = input.charAt(0);
        int compressCounter = 1;
        for (int i = 1; i < input.length(); i++) {
            if (currentChar == input.charAt(i)) {
                compressCounter++;
            } else {
                compressedInput.append(currentChar).append(compressCounter);
                currentChar = input.charAt(i);
                compressCounter = 1;
            }
        }
        compressedInput.append(currentChar).append(compressCounter);

        return compressedInput.toString();
    }

    public String compressUsingStreams(String input) {
        // TODO with Java's Stream API
        return null;
    }

    public String compressUsingMapReduce(String input) {
        // TODO with Java's MapReduce Framework
        return null;
    }

}
