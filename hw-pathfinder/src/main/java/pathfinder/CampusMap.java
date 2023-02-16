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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

public class CampusMap implements ModelAPI {

    List<CampusBuilding> buildingList;
    Graph<Point, Double> graph;

    // Abstraction function: AF(this) = CampusMap cp where
    //                       The Points parsed from a file are stored in a graph g
    //                       The Edges from each Point are stored as Doubles
    //                       The shortest path from two points can be calculated based
    //                       on the lowest total Double value it takes to get there

    // Representation invariant:
    //     graph != null
    //     All Points in graph keySet are not null
    //     All Doubles in set are not null
    //     buildingList != null
    //     All CampusBuildings in List are not null

    public CampusMap() {
        buildingList = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        graph = buildMap("campus_buildings.csv", "campus_paths.csv");
    }

    @Override
    public boolean shortNameExists(String shortName) {
        return buildingNames().containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        return buildingNames().get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        Map<String, String> buildingNames = new HashMap<>();

        for (CampusBuilding cb : buildingList) {
            if (!buildingNames.containsKey(cb.getShortName())) {
                buildingNames.put(cb.getShortName(), cb.getLongName());
            }
        }

        return buildingNames;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        Point startPoint = findPoint(startShortName);
        Point endPoint = findPoint(endShortName);
        Path<Point> shortestPath = DijkstrasAlgorithm.findPath(graph, startPoint, endPoint);

        return shortestPath;
    }

    private Point findPoint(String name) {
        if(!shortNameExists(name)) {
            throw new IllegalArgumentException("name must exist on campus.");
        }
        for (CampusBuilding cb : buildingList) {
            if (cb.getShortName().equals(name)) {
                for(Point p: graph.getNodes()) {
                    if(p.getX() == cb.getX() && p.getY() == cb.getY()) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Builds a graph with points as nodes connected by edges being the distance between them
     * @param buildFile String for buildings filename
     * @param pathFile String for paths filename
     * @return a graph of points connected by distances between them
     */
    public static Graph<Point, Double> buildMap(String buildFile, String pathFile) {
        Graph<Point, Double> graph = new Graph<>();

        List<CampusBuilding> buildingList = CampusPathsParser.parseCampusBuildings(buildFile);
        List<CampusPath> pathList = CampusPathsParser.parseCampusPaths(pathFile);

        for (CampusBuilding cb : buildingList) {
            if (!graph.containsNode(new Point(cb.getX(), cb.getY()))) {
                graph.addNode(new Point(cb.getX(), cb.getY()));
            }
        }
        for (CampusPath cp: pathList) {
            Point start = new Point(cp.getX1(), cp.getY1());
            Point end = new Point(cp.getX2(), cp.getY2());

            if (!graph.containsNode(start)) {
                graph.addNode(start);
            } else if (!graph.containsNode(end)) {
                graph.addNode(end);
            }

            graph.addEdge(cp.getDistance(), start, end);
        }

        return graph;
    }
}
