package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import javax.inject.Inject;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Matching
{
    @Inject
    EdmondsKarp edmondsKarp;

    public int countMatching(Graph graph)
    {
        Graph residualGraph = graph.copy();
        Vertex superSource = addSuperSource(residualGraph);
        Vertex superSink = addSuperSink(residualGraph);

        edmondsKarp.calculateMaxFlowGraph(residualGraph, superSource, superSink);
        return extractEdgesWithFlow(graph, residualGraph);
    }

    private int extractEdgesWithFlow(Graph graph, Graph residualGraph)
    {
        int countEdgesWithFlow = 0;
        for (Edge edge : graph.getEdges())
        {
            Vertex source = residualGraph.getVertex(edge.getSource().getKey());
            Vertex sink = residualGraph.getVertex(edge.getSink().getKey());

            Edge residualEdge = residualGraph.getEdge(sink, source);
            if (residualEdge != null)
            {
                countEdgesWithFlow++;
            }
        }

        return countEdgesWithFlow;
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
