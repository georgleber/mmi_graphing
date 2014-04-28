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
    public Graph getMinimalSpanningTree(Graph graph)
    {
        Graph minSpanTree = new Graph(graph.isDirected());

        Iterator<Edge> edges = sortEdges(graph).iterator();
        Map<Vertex, Set<Vertex>> forest = createForest(graph.getVertices());

        while (minSpanTree.getEdges().size() < graph.getVertices().size() - 1)
        {
            Edge edge = edges.next();

            Set<Vertex> visitedSource = forest.get(edge.getSource());
            Set<Vertex> visitedSink = forest.get(edge.getSink());

            if (visitedSource == visitedSink)
            {
                continue;
            }

            addEdgeToTree(minSpanTree, edge);

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

    private Map<Vertex, Set<Vertex>> createForest(Collection<Vertex> vertices)
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

    private List<Edge> sortEdges(Graph graph)
    {
        return graph.getEdges().stream().sorted(Comparator.comparing(Edge::getWeight)).collect(Collectors.toList());
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

        Edge newEdge = new Edge(source, sink, edge.getWeight());
        minSpanTree.addEdge(newEdge);
    }
}
