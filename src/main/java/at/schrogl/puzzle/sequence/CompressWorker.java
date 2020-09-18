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
        return input.trim().chars()
            .boxed()
            .reduce(
                /* The identity method is the starting point and return value if the stream is empty */
                (input.isBlank()) ? "" : input.charAt(0) + "0",
                /* Accumulator does the reduction */
                (partialResult, letterAsInt) -> {
                    String[] tuples = partialResult.split("\\w\\d+", partialResult.length() / 2);
                    String lastTuple = (tuples.length == 0) ? tuples[0] : tuples[tuples.length - 1];
                    if (letterAsInt.intValue() == lastTuple.charAt(0)) {
                        int newCount = Integer.valueOf(lastTuple.substring(1)) + 1;
                        return partialResult.substring(0, partialResult.length() - lastTuple.length()) + lastTuple.charAt(0) + newCount;
                    } else {
                        return partialResult + ((char) letterAsInt.intValue()) + "1";
                    }
                },
                /*
                   The combiner is used in parallel streams. This implementation is NOT capable of parallel execution.
                   Here it is only necessary, so that the compiler is able to infer the type of "partialResult"
                 */
                String::join);
    }

    public String compressUsingMapReduce(String input) {
        // TODO with Java's MapReduce Framework
        return null;
    }

}
