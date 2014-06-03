package de.develman.mmi.algorithm;

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
public class Dijkstra extends AbstractShortestPath
{
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
        init(graph, startVertex);

        Vertex currentVertex = startVertex;
        do
        {
            currentVertex.getOutgoingEdges().stream().filter(e -> !e.getSink().isVisited()).forEach(e -> updateCost(e));
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

    private Predicate<Map.Entry<Vertex, Double>> unscannedVertex()
    {
        return entry -> !entry.getKey().isVisited();
    }

    private Comparator<Map.Entry<Vertex, Double>> compareWeight()
    {
        return Comparator.comparing(entry -> entry.getValue());
    }
}
