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
        List<Edge> minSpanTree = new ArrayList<>();

        int vertexCount = graph.getVertices().size();
        doPrimInternal(vertexCount, startVertex, minSpanTree);

        return minSpanTree;
    }

    private static void doPrimInternal(int vertexCount, Vertex vertex, List<Edge> minSpanTree)
    {
        vertex.setVisited(true);
        updateAvailableEdgeList(vertex);

        Edge edge = availableEdges.remove(0);
        minSpanTree.add(edge);

        if (minSpanTree.size() < vertexCount - 1)
        {
            doPrimInternal(vertexCount, edge.getSink(), minSpanTree);
        }
    }

    private static void updateAvailableEdgeList(Vertex vertex)
    {
        vertex.getOutgoingEdges().stream().filter(e -> !e.getSink().isVisited()).forEach(e ->
        {
            availableEdges.add(e);
        });

        availableEdges = availableEdges.stream().filter(e -> !e.getSink().isVisited()).sorted(Comparator.comparing(
                e -> e.getWeight())).collect(Collectors.toList());
    }
}
