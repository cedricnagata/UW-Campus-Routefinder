package pathfinder;

import graph.Edge;
import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

public class DijkstrasAlgorithm<T extends Comparable<T>> {
    /**
     * If the loop terminates then there exist no path from the start to the end.
     * @spec.requires Edge weights must be greater than or equal to 0.0
     * @param <T> Generic Type for Path
     * @param graph Graph to find path in
     * @param start T for start node
     * @param dest T for dest node
     * @return Returns an ArrayList of Edges that has a path from first to second
     * 		   returns an empty ArrayList if no path is found.
     */
    public static <T extends Comparable<T>> Path<T> findPath(Graph<T, Double> graph, T start, T dest) {

        PriorityQueue<Path<T>> active = new PriorityQueue<Path<T>>(new Comparator<Path<T>>() {
            @Override
            public int compare(Path<T> o1, Path<T> o2) {
                if(o1.getCost() < o2.getCost()) {
                    return -1;
                } else if (o1.getCost() > o2.getCost()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        Set<T> finished = new HashSet<>();

        Path<T> start2start = new Path<>(start);
        start2start.extend(start, 0.0);
        active.add(start2start);

        while(!active.isEmpty()) {
            Path<T> minPath = active.poll();

            T minDest = minPath.getEnd();

            if(minDest.equals(dest)) {
                return minPath;
            }

            if(!finished.contains(minDest)) {
                for(Edge<Double, T> e: graph.getEdges(minDest)) {
                    if(!finished.contains(minDest)) {
                        Path<T> newPath = minPath.extend(e.getChild(), e.getLabel());
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
        }
        return null;
    }
}
