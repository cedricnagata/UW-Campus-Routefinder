package graph.junitTests;

import graph.*;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class TestEdges {
    private Node<String> node1 = new Node<>("data1");
    private Node<String> node2 = new Node<>("data2");

    private Edge<String, Node<String>> edge1 = new Edge<>("label1", node2, node1);
    private Edge<String, Node<String>> edge2 = new Edge<>("label2", node2, node1);
    private Edge<String, Node<String>> edge3 = new Edge<>("label2", node2, node1);

    @Test
    public void testGetLabel() {
        assertEquals("label1", edge1.getLabel());
    }

    @Test
    public void testGetChild() {
        assertEquals(node1, edge1.getChild());
    }

    @Test
    public void testHashCodeSame() {
        assertTrue(edge2.hashCode() == edge3.hashCode());
    }

    @Test
    public void testHashCodeNotSame() {
        assertFalse(edge1.hashCode() == edge2.hashCode());
    }

    @Test
    public void testEquals() {
        assertTrue(edge3.equals(edge2));
    }

    @Test
    public void testNotEquals() {
        assertFalse(edge1.equals(edge2));
    }

    @Test
    public void testToString() {
        assertEquals(edge1.toString(), "label1(data1)");
    }
}
