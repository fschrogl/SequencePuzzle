package at.schrogl.puzzle.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

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
        return ForkJoinPool
            .commonPool()
            .invoke(new ForkJoinCompressionTask(input, false));

    }

    private static class ForkJoinCompressionTask extends RecursiveTask<String> {

        private final String inputChunk;
        private final boolean isFork;

        public ForkJoinCompressionTask(String inputChunck, boolean isFork) {
            this.inputChunk = inputChunck == null ? "" : inputChunck.trim();
            this.isFork = isFork;
        }

        @Override
        protected String compute() {
            if (isFork) {
                return String.format("%c%d", inputChunk.charAt(0), inputChunk.length());
            } else {
                List<ForkJoinTask<String>> tasks = new ArrayList<>();

                int startIndex = 0;
                for (int currentIndex = 1; currentIndex < inputChunk.length(); currentIndex++) {
                    if (inputChunk.charAt(startIndex) != inputChunk.charAt(currentIndex)) {
                        tasks.add(
                            new ForkJoinCompressionTask(inputChunk.substring(startIndex, currentIndex), true).fork()
                        );
                        startIndex = currentIndex;
                    }
                }
                tasks.add(
                    new ForkJoinCompressionTask(inputChunk.substring(startIndex), true).fork()
                );

                return tasks.stream()
                    .map(ForkJoinTask::join)
                    .collect(Collectors.joining());
            }
        }
    }

}
