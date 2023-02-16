package marvel;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.*;

public class MarvelPaths {
    /**
     * Builds a graph with characters as nodes connected by edges being the books they
     * were featured in.
     *
     * @param filename file to parse data and turn into graph
     * @spec.requires characterData != null
     * @return a graph of characters connected by books they are featured in
     */
    public static Graph<String, String> buildGraph(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("filename cannot be null.");
        }

        Map<String, HashSet<String>> books = MarvelParser.parseData(filename);
        Graph<String, String> graph = new Graph<String, String>();

        for (String b: books.keySet()) {
            HashSet<String> characters = books.get(b);
            for (String parent : characters) {
                for (String child : characters) {
                    graph.addNode(parent);
                    if (!parent.equals(child)) {
                        graph.addEdge(b, parent, child);
                    }
                }
            }
        }

        return graph;
    }

    /**
     * Finds the shortest path between two characters.
     *
     * @param graph Graph of characters to use
     * @param start String data of Node to start path from
     * @param end String data of Node to end path on
     * @return the shortest path from start node to end node, or null if
     * no path exists between the two
     * @throws IllegalArgumentException if either start or end does not exist
     * in graph or if graph == null, start == null, end == null
     */
    public static List<String[]> findPath(Graph<String, String> graph, String start, String end) {

        if (graph == null) {
            throw new IllegalArgumentException("graph can't be null");
        }

        if (start == null || end == null) {
            throw new IllegalArgumentException("start and end can't be null");
        }

        if (!(graph.containsNode(start))) {
            throw new IllegalArgumentException("start character is not in the graph");
        }

        if (!(graph.containsNode(end))) {
            throw new IllegalArgumentException("end character is not in the graph");
        }

        Queue<String> nodeList = new LinkedList<>();
        Map<String, List<String[]>> paths = new HashMap<>();

        nodeList.add(start);
        paths.put(start, new ArrayList<>());

        while (!(nodeList.isEmpty())) {
            String character = nodeList.remove();

            if (character.equals(end)) {
                return paths.get(character);
            } else {
                List<String[]> stringList = graph.getEdgesLabel(character);

                for (String[] edgeData : stringList) {
                    if (!(paths.containsKey(edgeData[1]))) {

                        List<String[]> path = new ArrayList<>();
                        path.addAll(paths.get(character));

                        path.add(edgeData);
                        paths.put(edgeData[1], path);

                        nodeList.add(edgeData[1]);
                    }
                }
            }
        }

        return null;
    }


    /**
     * Allows user to type in two characters and find the
     * shortest path of those two characters.
     *
     * @param args arguments for main method
     */

    public static void main(String[] args) {
        Graph<String, String> graph = MarvelPaths.buildGraph("marvel.csv");
        System.out.println("Find the shortest path for two Marvel characters.");
        Scanner reader = new Scanner(System.in);
        String start, end;

        boolean tryAgain = true;

        while (tryAgain) {
            System.out.print("Please type in a character: ");
            start = reader.nextLine();
            System.out.print("Please type in another character: ");
            end = reader.nextLine();

            if (!(graph.containsNode(start) || graph.containsNode(end))) {
                System.out.println("unknown: " + start);
                System.out.println("unknown: " + end);
            } else if (!(graph.containsNode(start))) {
                System.out.println("unknown: " + start);
            } else if (!(graph.containsNode(end))) {
                System.out.println("unknown: " + end);
            } else {
                String currentNode = start.toString();
                String result = "path from " + start + " to " + end + ":";
                List<String[]> path = MarvelPaths.findPath(graph, start.toString(), end.toString());

                if (path == null) {
                    result += "\n" + "no path found";
                } else {
                    for (String[] e : path) {
                        result += "\n" + currentNode + " to " + e[1] +
                                " via " + e[0];
                        currentNode = e[1];
                    }
                }

                System.out.println(result);
            }
            System.out.print("Try again? ");

            String response = reader.nextLine();
            response = response.toLowerCase();

            if (response.charAt(0) != 'y' || response.length() == 0) {
                tryAgain = false;
            } else {
                tryAgain = true;
            }
        }

        reader.close();
    }

}
