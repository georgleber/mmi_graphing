package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Die Klasse Kruskal implementiert den Algorithmus von Kruskal zur Berechnung eines minimal spannenden Baumes
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Kruskal
{
    /**
     * Berechung des minimal spannenden Baums nach Kruskal
     *
     * @param graph Graph für den der Baum berechnet werden soll
     * @return Liste der Kanten des minimal spannenden Baums
     */
    public static List<Edge> getMinimalSpanningTree(Graph graph)
    {
        List<Edge> minSpanTree = new ArrayList<>();

        Iterator<Edge> edges = sortEdges(graph).iterator();
        Map<Vertex, Set<Vertex>> forest = createForest(graph.getVertices());

        while (minSpanTree.size() < graph.getVertices().size() - 1)
        {
            Edge edge = edges.next();

            Set<Vertex> visitedSource = forest.get(edge.getSource());
            Set<Vertex> visitedSink = forest.get(edge.getSink());

            if (visitedSource == visitedSink)
            {
                continue;
            }

            minSpanTree.add(edge);

            if (visitedSource.size() < visitedSink.size())
            {
                visitedSink.addAll(visitedSource);
                visitedSource.forEach(v -> forest.put(v, visitedSink));

            }
            else
            {
                visitedSource.addAll(visitedSink);
                visitedSink.forEach(v -> forest.put(v, visitedSource));
            }
        }

        return minSpanTree;
    }

    private static Map<Vertex, Set<Vertex>> createForest(Collection<Vertex> vertices)
    {
        Map<Vertex, Set<Vertex>> forest = new HashMap<>();
        vertices.forEach((vertex) ->
        {
            Set<Vertex> vs = new HashSet<>();
            vs.add(vertex);

            forest.put(vertex, vs);
        });

        return forest;
    }

    private static List<Edge> sortEdges(Graph graph)
    {
        return graph.getEdges().stream().sorted(Comparator.comparing(Edge::getWeight)).collect(Collectors.toList());
    }
}
