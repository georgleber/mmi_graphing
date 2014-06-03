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
    public Graph getMinimalSpanningTree(Graph graph, Vertex startVertex)
    {
        Vertex vertex = startVertex;

        Graph minSpanTree = new Graph(graph.isDirected());
        while (minSpanTree.getEdges().size() < graph.getVertices().size() - 1)
        {
            vertex.setVisited(true);
            updateAvailableEdgeList(vertex);

            Edge edge = availableEdges.remove(0);
            addEdgeToTree(minSpanTree, edge);

            vertex = edge.getSink();
        }

        return minSpanTree;
    }

    private void updateAvailableEdgeList(Vertex vertex)
    {
        vertex.getOutgoingEdges().stream().filter(e -> !e.getSink().isVisited()).forEach(e ->
        {
            availableEdges.add(e);
        });

        availableEdges = availableEdges.stream().filter(e -> !e.getSink().isVisited()).sorted(Comparator.comparing(
                Edge::getCapacity)).collect(Collectors.toList());
    }

    private void addEdgeToTree(Graph minSpanTree, Edge edge)
    {
        Vertex source = minSpanTree.getVertex(edge.getSource().getKey());
        if (source == null)
        {
            source = new Vertex(edge.getSource().getKey());
            minSpanTree.addVertex(source);
        }

        Vertex sink = minSpanTree.getVertex(edge.getSink().getKey());
        if (sink == null)
        {
            sink = new Vertex(edge.getSink().getKey());
            minSpanTree.addVertex(sink);
        }

        Edge newEdge = new Edge(source, sink, edge.getCapacity());
        minSpanTree.addEdge(newEdge);
    }
}
