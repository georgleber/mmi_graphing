package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import javax.inject.Inject;

/**
 * Die Klasse Matching implementiert den Matching Algorithmus zur Berechnung Anzahl an "gematchten" Kanten in einem
 * gerichteten bipartiten Graphen
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Matching
{
    @Inject
    EdmondsKarp edmondsKarp;

    /**
     * Berechnung der Anzahl an Matchings
     *
     * @param graph Graph
     * @return Liefert die Anzahl der "gematchten" Kanten
     */
    public int countMatching(Graph graph)
    {
        Graph residualGraph = graph.copy();
        Vertex superSource = addSuperSource(residualGraph);
        Vertex superSink = addSuperSink(residualGraph);

        return (int) edmondsKarp.calculateMaxFlowGraph(residualGraph, superSource, superSink);
    }

    private Vertex addSuperSource(Graph graph)
    {
        Vertex superSource = new Vertex(-1);
        graph.addVertex(superSource);

        graph.getVertices().stream().filter(v -> v.getKey() < graph.getGroupedVerticeCount()).forEach(v ->
        {
            Edge edge = new Edge(superSource, v, 1.0, 0.0);
            graph.addEdge(edge, 0);
        });

        return superSource;
    }

    private Vertex addSuperSink(Graph graph)
    {
        Vertex superSink = new Vertex(-2);
        graph.addVertex(superSink);

        graph.getVertices().stream().filter(v -> v.getKey() >= graph.getGroupedVerticeCount()).forEach(v ->
        {
            Edge edge = new Edge(v, superSink, 1.0, 0.0);
            graph.addEdge(edge, 0);
        });

        return superSink;
    }
}
