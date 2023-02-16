package graph.junitTests;

import graph.*;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class TestGraphs {

    private Graph<Node<String>,String> graph = new Graph<>();

    private final Node<String> node1 = new Node<>("data1");
    private final Node<String> node2 = new Node<>("data2");

    private Edge<String,Node<String>> edge1 = new Edge<>("label1", node1, node2);
    private Edge<String,Node<String>> edge2 = new Edge<>("label2", node2, node1);

    /*
     * Empty Graph Tests:
     * Tests for newly constructed graph with no nodes or edges
     */
    @Test
    public void testNumberOfNodes() {
        assertEquals(0, graph.numberOfNodes());
    }

    /*
     * Tests for one Node
     */
    @Test
    public void addOneNode() {
        assertTrue(graph.addNode(node1));
    }

    @Test
    public void testNumberOfNodesOne() {
        graph.addNode(node1);
        assertEquals(1, graph.numberOfNodes());
    }

    @Test
    public void testContainsNodeIncorrect() {
        graph.addNode(node1);
        assertFalse(graph.containsNode(node2));
    }

    /*
     * Tests for adding same node twice
     */
    @Test
    public void testAddNodeSame() {
        graph.addNode(node1);
        assertFalse(graph.addNode(node1));
    }

    @Test
    public void testNumberOfNodesSame() {
        testAddNodeSame();
        assertEquals(1,graph.numberOfNodes());
    }

    /*
     * Tests for two different Nodes
     */
    @Test
    public void testNumberOfNodesTwo() {
        graph.addNode(node1);
        assertTrue(graph.addNode(node2));
        assertEquals(2,graph.numberOfNodes());
    }

    /*
     * Tests for Edges
     */
    @Test
    public void addEdge() {
        graph.addNode(node1);
        graph.addNode(node2);
        assertTrue(graph.addEdge("label", node1, node2));
    }

    @Test
    public void testNumberOfEdgesNone() {
        graph.addNode(node1);
        assertTrue(graph.addNode(node2));
        assertEquals(0,graph.numberOfEdges(node1, node2));
    }

    @Test
    public void testNumberOfEdgesReverse() {
        graph.addNode(node1);
        assertTrue(graph.addNode(node2));
        assertEquals(0,graph.numberOfEdges(node2, node1));
    }

    @Test
    public void testAddEdgeNoParentNode() {
        boolean exception = false;

        graph.addNode(node2);
        graph.addNode(node1);

        try {
            graph.addEdge("label1", null, node2);
        } catch (RuntimeException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testAddEdgeNoChildNode() {
        boolean exception = false;

        graph.addNode(node1);

        try {
            graph.addEdge("label1", node1, null);
        } catch (RuntimeException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testAddEdgeNoNodes() {
        boolean exception = false;

        try {
            graph.addEdge("label1", null, null);
        } catch (RuntimeException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testAddEdgeNoLabel() {
        boolean exception = false;

        graph.addNode(node1);
        graph.addNode(node2);

        try {
            graph.addEdge(null, node1, node2);
        } catch (RuntimeException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testContainsEdgeIncorrectLabel() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge("correctLabel", node1, node2);
        assertFalse(graph.containsEdgeLabel("incorrectLabel"));
    }

    @Test
    public void testContainsEdgeIncorrectNodes() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge("correctLabel", node1, node2);
        assertFalse(graph.containsEdgeNodes(node2, node1));
    }

    @Test
    public void testAddingSameEdge() {
        graph.addNode(node1);
        assertTrue(graph.addNode(node2));
        assertTrue(graph.addEdge("edge_one_two", node1, node2));
        assertFalse(graph.addEdge("edge_one_two",node1, node2));
    }
}
