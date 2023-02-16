package graph.junitTests;

import graph.*;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class TestNodes {

    private Node<String> node1 = new Node<>("data");
    private Node<String> node2 = new Node<>("otherData");
    private Node<String> node3 = new Node<>("data");

    @Test
    public void testGetData() {
        assertEquals("otherData", node2.getData());
        assertEquals("data", node1.getData());
    }

    @Test
    public void testHashCodeSame() {
        assertTrue(node1.hashCode() == node3.hashCode());
    }

    @Test
    public void testHashCodeNotSame() {
        assertFalse(node1.equals(node2));
    }

    @Test
    public void testEquals() {
        assertTrue(node1.equals(node3));
    }

    @Test
    public void testNotEquals() {
        assertFalse(node1.equals(node2));
    }

    @Test
    public void testToString() {
        assertTrue(node1.toString() == "data");
    }
}
