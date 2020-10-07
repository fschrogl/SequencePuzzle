package at.schrogl.puzzle.sequence;

/*-
 * #%L
 * SequencePuzzle
 * %%
 * Copyright (C) 2020 Fritz Schrogl
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
