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
        // TODO with loop
        return null;
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
