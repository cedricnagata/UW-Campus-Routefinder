/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class parses through data in an input file and returns the data in Map.
 */
public class MarvelParser {

    /**
     * Reads in an input file where each line contains a Marvel character and a
     * book they were featured in. These are separated by a comma.
     *
     * @spec.requires filename exists in resources/data
     * @param filename the file that will be read
     * @return a Map containing each character and a list of books they were featured in
     */
    public static Map<String, HashSet<String>> parseData(String filename) {

        List<String> lines = readLines(filename);
        Map<String, HashSet<String>> books = new HashMap<>();

        for (String s: lines) {
            String[] tokens = s.split(",");
            if (books.containsKey(tokens[1])) {
                if (!books.get(tokens[1]).contains(tokens[0])) {
                    books.get(tokens[1]).add(tokens[0]);
                }
            } else {
                HashSet<String> characters = new HashSet<>();
                books.put(tokens[1], characters);
                books.get(tokens[1]).add(tokens[0]);
            }
        }
        return books;
    }

    /**
     * Reads all lines contained within the provided data file, which is located
     * relative to the data/ folder in this parser's classpath.
     *
     * @param filename The file to read.
     * @throws IllegalArgumentException if the file doesn't exist, has an invalid name, or can't be read
     * @return A new {@link List<String>} containing all lines in the file.
     */
    private static List<String> readLines(String filename) {
        // You can use this code as an example for getting a file from the resources folder
        // in a project like this. If you access data files elsewhere in your code, you'll need
        // to use similar code. If you use this code elsewhere, don't forget:
        //   - Replace 'MarvelParser' in `MarvelParser.class' with the name of the class you write this in
        //   - If the class is in src/main, it'll get resources from src/main/resources
        //   - If the class is in src/test, it'll get resources from src/test/resources
        //   - The "/" at the beginning of the path is important
        // Note: Most students won't re-write this code anywhere, this explanation is just for completeness.
        InputStream stream = MarvelParser.class.getResourceAsStream("/data/" + filename);
        if (stream == null) {
            // stream is null if the file doesn't exist.
            // We want to handle this case so we don't try to call
            // readLines and have a null pointer exception.
            throw new IllegalArgumentException("No such file: " + filename);
        }
        return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
    }
}
