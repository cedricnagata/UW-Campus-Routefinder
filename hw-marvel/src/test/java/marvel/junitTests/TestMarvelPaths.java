package marvel.junitTests;

import graph.Graph;
import marvel.MarvelPaths;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestMarvelPaths {

    private Graph<String, String> graph;
    public void setup() {
        graph = MarvelPaths.buildGraph("staffSuperheroes.csv");
    }

    @Test
    public void testBuildGraphNull() throws Exception {
        setup();
        boolean exception = false;

        try {
            MarvelPaths.buildGraph(null);
        } catch (RuntimeException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testFindPathNullGraph() {
        setup();
        boolean exception = false;

        try {
            MarvelPaths.findPath(null, "Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard");
        } catch (IllegalArgumentException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testFindPathNullStartNode() {
        setup();
        boolean exception = false;

        try {
            MarvelPaths.findPath(graph, null, "Ernst-the-Bicycling-Wizard");
        } catch (IllegalArgumentException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testFindPathNullEndNode() {
        setup();
        boolean exception = false;

        try {
            MarvelPaths.findPath(graph, "Ernst-the-Bicycling-Wizard", null);
        } catch (IllegalArgumentException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testFindPathStartNotInGraph() {
        setup();
        boolean exception = false;

        try {
            MarvelPaths.findPath(graph, "Not in graph", null);
        } catch (IllegalArgumentException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testFindPathEndNotInGraph() {
        setup();
        boolean exception = false;

        try {
            MarvelPaths.findPath(graph, null, "Not in graph");
        } catch (IllegalArgumentException e) {
            exception = true;
        }

        assertTrue(exception);
    }
}
