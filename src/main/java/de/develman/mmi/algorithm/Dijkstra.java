package de.develman.mmi.algorithm;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
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
    public double getLengthOfShortestPath(Graph graph, Vertex startVertex, Vertex endVertex)
    {
        initDistances(graph, startVertex);
        initPredecessors(graph.countVertices(), startVertex);

        Vertex currentVertex = startVertex;
        do
        {
            currentVertex.getOutgoingEdges().forEach(e ->
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

        return distance.get(endVertex);
    }

    private Vertex getNextVertex()
    {
        Optional<Map.Entry<Vertex, Double>> unscannedEntry = distance.entrySet().stream().filter(isUnscanned()).sorted(
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

    private Predicate<Map.Entry<Vertex, Double>> isUnscanned()
    {
        return entry -> entry.getKey().isVisited();

    }

    private Comparator<Map.Entry<Vertex, Double>> compareWeight()
    {
        return Comparator.comparing(entry -> entry.getValue());
    }
}
