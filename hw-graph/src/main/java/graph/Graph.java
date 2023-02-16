package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;

/**
 * <b>Graph</B> represents a Graph made up of Nodes connected by Edges
 * The Nodes store data and the Edges store a label and an end Node.
 * Each node my only appear in the Graph once but can have unlimited edges
 * to and from. The graph class is mutable.
 *
 * Specification fields:
 * @spec.specfield: graph: HashMap<Node>, HashSet<Edge> // Map of Nodes and Edges from each Node
 *
 * Abstract Invariant:
 *  Graph keySet is not null
 *  All Nodes in set are not null
 *  All Edges in set are not null
 */
public class Graph<T extends Comparable<T>, E extends Comparable<E>> {

    private final HashMap<T, HashSet<Edge<E, T>>> graph;
    public static final boolean CHECK_REP_ENABLE = false;

    // Abstraction function: AF(this) = graph g where
    //                       The Nodes of the graph are stored in g
    //                       The Edges from each Node are stored using a keySet of all Nodes
    //                       If there are no Nodes present, the graph

    // Representation invariant:
    //     graph != null
    //     All Nodes in graph keySet are not null
    //     All Edges in set are not null

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() throws RuntimeException {
        if (CHECK_REP_ENABLE) {
            if (graph == null){
                throw new RuntimeException("The graph can't be null");
            }

            for (T n : graph.keySet()) {
                if (n == null) {
                    throw new RuntimeException("node can't be null");
                }
                if (graph.get(n) == null) {
                    throw new RuntimeException("set of edges connecting to node can't be null");
                }
                for (Edge e: graph.get(n)) {
                    if (e == null) {
                        throw new RuntimeException("edge can't be null");
                    }
                }
            }
        }
    }

    /**
     * Creates a new empty Graph stored in a Map
     * @spec.effects creates an empty graph
     */
    public Graph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * Add a Node to the Graph given that the Node does not already exist
     * return true if the Node is added, false if not added
     * @param n Node to be added to the Graph
     * @spec.requires n is not null
     * @spec.modifies this
     * @spec.effects add n to the Graph if it does not already exist
     * @return true if n is added, false if not added
     */
    public boolean addNode(T n) {
        if (n == null) {
            throw new IllegalArgumentException("node can't be null");
        }

        boolean nodeAdded = !graph.containsKey(n);

        if (nodeAdded) {
            graph.put(n, new HashSet<>());
        }

        return nodeAdded;
    }

    /**
     * Adds an Edge to the Graph with a label from a parent Node to a child Node.
     * If the given parent or child Node does not exist, create a new Node.
     * @param label the label of the Edge being added
     * @param parent the parent Node of the Edge being added
     * @param child the child Node of  the Edge being added
     * @spec.modifies this
     * @spec.requires label, parent, child are not null and parent is in the graph
     * @spec.effects adds a new Edge from parent to child Node labeled as label
     * @return true if Edge is added, false if not added
     */
    public boolean addEdge(E label, T parent, T child) {
        if (label == null) {
            throw new IllegalArgumentException("label can't be null");
        }
        if (parent == null || child == null) {
            throw new IllegalArgumentException("node can't be null");
        }

        addNode(parent);
        Edge<E, T> e = new Edge<>(label, parent, child);
        HashSet<Edge<E, T>> parentEdges = graph.get(parent);
        boolean edgeAdded = !parentEdges.contains(e);

        if (edgeAdded) {
            parentEdges.add(e);
        }

        return edgeAdded;
    }

    /**
     * Return all Nodes in the Graph
     * @return all Nodes in the Graph
     */
    public HashSet<T> getNodes() {
        HashSet<T> nodes = new HashSet<> ();

        for (T n: graph.keySet()) {
            nodes.add(n);
        }

        return nodes;
    }

    /**
     * Returns all the child Nodes of a given Node
     * @param n Node to return the children of
     * @spec.requires n != null and graph contains n
     * @return HashSet containing all the child Nodes of n
     */
    public HashSet<T> getChildren(T n) {
        if (n == null) {
            throw new IllegalArgumentException("node can't be null");
        }
        if (!graph.containsKey(n)) {
            throw new IllegalArgumentException("node must be contained in the graph");
        }

        HashSet<T> nodes = new HashSet<> ();

        for (Edge<E, T> e: graph.get(n)) {
            nodes.add(e.getChild());
        }

        return nodes;
    }

    /**
     * Returns all Edges from a parent Node in the Graph
     * @param n Node to get edges from
     * @spec.requires n != null and graph contains n
     * @return a set of Edges from Node n
     */
    public HashSet<Edge<E, T>> getEdges(T n) {
        HashSet<Edge<E, T>> edges = new HashSet<>();

        for (Edge<E, T> e: graph.get(n)) {
            edges.add(e);
        }

        return edges;
    }

    /**
     * Returns all Edges from a parent Node in the Graph
     * @param n Node to get edges from
     * @spec.requires n != null and graph contains n
     * @return a set of Edges from Node n
     */
    public List<String[]> getEdgesLabel(T n) {
        if (!this.containsNode(n)) {
            throw new IllegalArgumentException();
        }

        List<String[]> edgeSet = new ArrayList<>();
        for (Edge<E, T> e: graph.get(n)) {
            String[] edgeAdd = new String[2];
            edgeAdd[0] = e.getLabel().toString();
            edgeAdd[1] = e.getChild().toString();
            edgeSet.add(edgeAdd);
        }

        return edgeSet;
    }


    /**
     * Returns number of Nodes in graph
     * @return number of Nodes in graph
     */
    public int numberOfNodes() {
        return graph.keySet().size();
    }

    /**
     * Returns the number of edges from one Node to another Node
     * @param parent parent Node of edge
     * @param child child Node of edge
     * @spec.requires parent, child != null and parent, child are in graph
     * @throws IllegalArgumentException if either Node is not in graph
     * @return number of Edges from parent to child Node
     */
    public int numberOfEdges(T parent, T child) {
        if (parent == null || child == null)
            throw new IllegalArgumentException("parent and child nodes can't be null");
        if (!(graph.containsKey(parent))){
            throw new IllegalArgumentException("parent node doesn't exist");
        }
        if (!(graph.containsKey(child))){
            throw new IllegalArgumentException("child node doesn't exist");
        }

        int edgeCount = 0;
        HashSet<Edge<E, T>> edges = graph.get(parent);

        for (Edge<E, T> e: edges) {
            if (e.getChild().equals(child))
                edgeCount++;
        }

        return edgeCount;
    }

    /**
     * Returns true if graph is empty
     * @return true if graph is empty
     */
    public boolean isEmpty() {
        return graph.isEmpty();
    }

    /**
     * Returns true if Graph contains the given Node
     * @param n Node to check if graph contains
     * @spec.requires n != null
     * @return true if graph contains n
     */
    public boolean containsNode(T n) {
        if (n == null){
            throw new IllegalArgumentException("node can't be null");
        }

        return graph.containsKey(n);
    }

    /**
     * Return true if Graph contains an Edge with the given label
     * @param label String of edge to check if graph contains
     * @spec.requires label != null
     * @return true if there is an Edge with the given label
     */
    public boolean containsEdgeLabel(E label) {
        boolean edgeExists = false;

        for(T n: graph.keySet()) {
            for (Edge<E, T> e: graph.get(n)) {
                if (e.getLabel().equals(label)) {
                    edgeExists = true;
                }
            }
        }

        return edgeExists;
    }

    /**
     * Return true if Graph contains an Edge between two Nodes
     * @param parent Node for origin of edge to check
     * @param child Node for destination of edge to check
     * @spec.requires parent != null, child != null
     * @return true if there is an Edge between the two given Nodes
     */
    public boolean containsEdgeNodes(T parent, T child) {
        boolean edgeExists = false;

        for (Edge<E, T> e: graph.get(parent)) {
            if (e.getChild().equals(child)) {
                edgeExists = true;
            }
        }

        return edgeExists;
    }
}