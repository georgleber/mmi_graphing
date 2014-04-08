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
     * Berechung des Minimal-Spannenden Baums nach Kruskal
     *
     * @param graph Graph für den der Baum berechnet werden soll
     * @return Minimal-Spannender Baum
     */
    public static List<Edge> getMinimalSpanningTree(Graph graph)
    {
        List<Edge> minSpanTree = new ArrayList<>();

        Iterator<Edge> edges = sortEdges(graph).iterator();
        Map<Vertex, Set<Vertex>> forest = createForest(graph.getVertices());

        while (edges.hasNext())
        {
            Edge nextEdge = edges.next();
            edges.remove();

            Set<Vertex> visitedSource = forest.get(nextEdge.getSource());
            Set<Vertex> visitedSink = forest.get(nextEdge.getSink());
            if (visitedSource.equals(visitedSink))
            {
                continue;
            }

            minSpanTree.add(nextEdge);
            visitedSource.addAll(visitedSink);
            visitedSource.stream().forEach(v -> forest.put(v, visitedSource));

            if (visitedSource.size() == graph.getVertices().size())
            {
                break;
            }
        }

        return minSpanTree;
    }

    private static Map<Vertex, Set<Vertex>> createForest(Collection<Vertex> vertices)
    {
        Map<Vertex, Set<Vertex>> forest = new HashMap<>();
        vertices.stream().forEach((vertex) ->
        {
            Set<Vertex> vs = new HashSet<>();
            vs.add(vertex);

            forest.put(vertex, vs);
        });

        return forest;
    }

    private static List<Edge> sortEdges(Graph graph)
    {
        return graph.getEdges().parallelStream().sorted(Comparator.comparing(
                e -> e.getWeight())).collect(Collectors.toList());
    }
}
