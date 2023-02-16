package pathfinder.junitTests;

import graph.Graph;
import org.junit.Test;
import pathfinder.CampusMap;
import pathfinder.datastructures.Point;

import static org.junit.Assert.assertTrue;

public class TestCampusMap {
    private CampusMap model = new CampusMap();
    private Graph<Point, Double> graph;

    public void setup() {
        graph = model.buildMap("campus_buildings.csv", "campus_paths.csv");
    }

    @Test
    public void testBuildMapNull() throws Exception {
        setup();
        boolean exception = false;

        try {
            model.buildMap(null, null);
        } catch (RuntimeException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testFindPathNullStart() {
        setup();
        boolean exception = false;

        try {
            model.findShortestPath(null, "CS2");
        } catch (IllegalArgumentException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    public void testFindPathNullEnd() {
        setup();
        boolean exception = false;

        try {
            model.findShortestPath("CS2", null);
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
            model.findShortestPath("Not in graph", "CS2");
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
            model.findShortestPath("CS2", "Not in graph");
        } catch (IllegalArgumentException e) {
            exception = true;
        }

        assertTrue(exception);
    }
}
