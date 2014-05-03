package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.algorithm.ShortestPath;
import java.util.*;
import java.util.function.Predicate;

/**
 * Die Klasse Dijkstra implementiert den Algorithmus von Dijkstra zur Berechnung des Kürzester-Wege-Baumes in einem
 * Digraph mit positiven Kantengewichten
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Dijkstra
{
    private Map<Vertex, Double> distance;
    private Vertex[] predecessor;

    /**
     * Berechnung des Kürzester-Wege-Baumes
     *
     * @param graph Digraph mit positiven Kantengewichten
     * @param startVertex Startknoten
     * @param endVertex Zielknoten
     * @return Liste der Kanten des Kürzester-Wege-Baum
     */
    public ShortestPath findShortestPath(Graph graph, Vertex startVertex, Vertex endVertex)
    {
        initDistances(graph, startVertex);
        initPredecessors(graph.countVertices(), startVertex);

        Vertex currentVertex = startVertex;
        do
        {
            currentVertex.getOutgoingEdges().stream().filter(unscannedSink()).forEach(e ->
            {
                Vertex source = e.getSource();
                Vertex sink = e.getSink();

                double newDistance = distance.get(source) + e.getWeight();
                if (newDistance < distance.get(sink))
                {
                    distance.put(sink, newDistance);
                    predecessor[sink.getKey()] = source;
                }
            });

            currentVertex.setVisited(true);
            currentVertex = getNextVertex();
            if (currentVertex == null)
            {
                break;
            }
        }
        while (true);

        return loadShortestPath(startVertex, endVertex);
    }

    private Vertex getNextVertex()
    {
        Optional<Map.Entry<Vertex, Double>> unscannedEntry = distance.entrySet().stream().filter(unscannedVertex()).sorted(
                compareWeight()).findFirst();

        Vertex nextVertex = null;
        if (unscannedEntry.isPresent())
        {
            nextVertex = unscannedEntry.get().getKey();
        }

        return nextVertex;
    }

    private void initDistances(Graph graph, Vertex startVertex)
    {
        distance = new HashMap<>(graph.countVertices());
        graph.getVertices().forEach(v -> distance.put(v, Double.POSITIVE_INFINITY));
        distance.put(startVertex, 0.0);
    }

    private void initPredecessors(int countVertices, Vertex startVertex)
    {
        predecessor = new Vertex[countVertices];
        predecessor[startVertex.getKey()] = startVertex;

    }

    private Predicate<Map.Entry<Vertex, Double>> unscannedVertex()
    {
        return entry -> !entry.getKey().isVisited();
    }

    private Predicate<Edge> unscannedSink()
    {
        return e -> !e.getSink().isVisited();
    }

    private Comparator<Map.Entry<Vertex, Double>> compareWeight()
    {
        return Comparator.comparing(entry -> entry.getValue());
    }

    private ShortestPath loadShortestPath(Vertex startVertex, Vertex endVertex)
    {
        ShortestPath path = null;

        double length = distance.get(endVertex);
        if (!Double.isInfinite(length))
        {
            List<Vertex> vertices = new ArrayList<>();
            vertices.add(endVertex);

            Vertex pred = endVertex;
            while (pred != startVertex)
            {
                pred = predecessor[pred.getKey()];
                vertices.add(pred);
            }
            Collections.reverse(vertices);

            path = new ShortestPath();
            path.setLength(length);
            path.setVertices(vertices);
        }

        return path;
    }

}
