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

package marvel.scriptTestRunner;

import graph.Edge;
import graph.Graph;
import graph.Node;
import marvel.MarvelParser;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, Graph<String, String>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) || (inputLine.charAt(0) == '#')) {
                output.println(inputLine);
            } else {
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "LoadGraph":
                    loadGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void loadGraph(List<String> arguments) throws Exception {
        if (arguments.size() != 2) {
            throw new CommandException("arguments are not suitable");
        }

        String graphName = arguments.get(0);
        String filename = arguments.get(1);

        loadGraph(graphName, filename);
    }

    private void loadGraph(String graphName, String filename) throws Exception {
        graphs.put(graphName, MarvelPaths.buildGraph(filename));
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("arguments are not suitable");
        }

        String graph = arguments.get(0);
        String node1 = arguments.get(1).replace('_', ' ');
        String node2 = arguments.get(2).replace('_', ' ');

        //Node n1 = new Node(node1);
        //Node n2 = new Node(node2);

        findPath(graph, node1, node2);
    }

    private void findPath(String graphName, String node1, String node2) {
        Graph<String, String> graph = graphs.get(graphName);

        if ((!graph.containsNode(node1)) && (!graph.containsNode(node2))) {
            output.println("unknown: " + node1);
            output.println("unknown: " + node2);
        } else if (!(graph.containsNode(node1))) {
            output.println("unknown: " + node1);
        } else if (!(graph.containsNode(node2))) {
            output.println("unknown: " + node2);
        } else {
            String currNode = node1;
            String result = "path from " + node1 + " to " + node2 + ":";
            List<String[]> path = MarvelPaths.findPath(graph, node1, node2);

            if (node1.equals(node2)) {
                result += "";
            } else if (path == null) {
                result += "\n" + "no path found";
            } else {
                for (String[] e : path) {
                    result += "\n" + currNode + " to " + e[1] + " via " + e[0];
                    currNode = e[1];
                }
            }

            output.println(result);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<String, String>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String, String> graph = graphs.get(graphName);
        graph.addNode(nodeName);

        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName, String edgeLabel) {
        Graph<String, String> graph = graphs.get(graphName);
        graph.addEdge(edgeLabel, parentName, childName);

        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName +
                " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String, String> graph = graphs.get(graphName);
        String nodeList = graphName + " contains:";

        TreeSet<String> nodes = new TreeSet<String>(new Comparator<String>() {
            public int compare(String node1, String node2) {
                if (!node1.equals(node2)) {
                    return node1.compareTo(node2);
                }

                return 0;
            }
        });

        nodes.addAll(graph.getNodes());

        for (String n: nodes) {
            nodeList += " " + n;
        }

        output.println(nodeList);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String, String> graph = graphs.get(graphName);
        String childList = "the children of " + parentName + " in " + graphName + " are:";

        TreeSet<Edge<String, String>> edges = new TreeSet<Edge<String, String>>(new Comparator<Edge<String, String>>() {
            public int compare(Edge<String, String> edge1, Edge<String, String> edge2) {
                if(!(edge1.getChild().equals(edge2.getChild()))) {
                    return edge1.getChild().compareTo(edge2.getChild());
                }
                if (!(edge1.getLabel().equals(edge2.getLabel()))) {
                    return edge1.getLabel().compareTo(edge2.getLabel());
                }
                return 0;
            }
        });

        edges.addAll(graph.getEdges(parentName));

        for (Edge<String, String> e: edges) {
            childList += " " + e.getChild() + "(" + e.getLabel() +")";
        }

        output.println(childList);
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
