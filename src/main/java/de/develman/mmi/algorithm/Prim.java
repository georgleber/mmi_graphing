package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Die Klasse Prim implementiert den Algorithmus von Prim zur Berechnung eines minimal spannenden Baumes
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Prim
{
    private static List<Edge> availableEdges = new ArrayList<>();

    /**
     * Berechung des minimal spannenden Baums nach Prim
     *
     * @param graph Graph für den der Baum berechnet werden soll
     * @param startVertex Startknoten
     * @return Liste der Kanten des minimal spannenden Baums
     */
    public static List<Edge> getMinimalSpanningTree(Graph graph, Vertex startVertex)
    {
        Vertex vertex = startVertex;

        List<Edge> minSpanTree = new ArrayList<>();
        while (minSpanTree.size() < graph.getVertices().size() - 1)
        {
            vertex.setVisited(true);
            updateAvailableEdgeList(vertex);

            Edge edge = availableEdges.remove(0);
            minSpanTree.add(edge);

            vertex = edge.getSink();
        }

        return minSpanTree;
    }

    private static void updateAvailableEdgeList(Vertex vertex)
    {
        vertex.getOutgoingEdges().stream().filter(e -> !e.getSink().isVisited()).forEach(e ->
        {
            availableEdges.add(e);
        });

        availableEdges = availableEdges.stream().filter(e -> !e.getSink().isVisited()).sorted(Comparator.comparing(
                Edge::getWeight)).collect(Collectors.toList());
    }
}
