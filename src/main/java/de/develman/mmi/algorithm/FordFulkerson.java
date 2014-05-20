package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class FordFulkerson
{
    @Inject
    BreadthFirstSearch breadthSearch;

    public double findMaxFlow(Graph graph, Vertex startVertex, Vertex endVertex)
    {
        double maxFlow = 0.0;
        if (startVertex.equals(endVertex))
        {
            return maxFlow;
        }

        Graph residualGraph = initResidualGraph(graph);
        Vertex start = residualGraph.getVertex(startVertex.getKey());
        Vertex end = residualGraph.getVertex(endVertex.getKey());

        List<Edge> path;
        while ((path = findPath(residualGraph, start, end)) != null)
        {
            double minCapacity = path.stream().mapToDouble(Edge::getWeight).min().getAsDouble();
            updateResidualGraph(residualGraph, path, minCapacity);

            maxFlow += minCapacity;
        }

        return maxFlow;
    }

    private Graph initResidualGraph(Graph graph)
    {
        Graph residualGraph = new Graph(graph.isDirected());
        graph.getVertices().forEach(v -> residualGraph.addVertex(new Vertex(v.getKey())));
        graph.getEdges().forEach(e ->
        {
            Vertex source = residualGraph.getVertex(e.getSource().getKey());
            Vertex sink = residualGraph.getVertex(e.getSink().getKey());

            Edge residualEdge = new Edge(source, sink, e.getWeight());
            residualGraph.addEdge(residualEdge);
        });

        return residualGraph;
    }

    private List<Edge> findPath(Graph graph, Vertex start, Vertex end)
    {
        graph.unvisitAllVertices();
        return breadthSearch.getPath(graph, start, end);
    }

    private void updateResidualGraph(Graph residualGraph, List<Edge> path, double minCapacity)
    {
        path.forEach(e ->
        {
            updateEdge(residualGraph, e, minCapacity);
            updateReversiveEdge(residualGraph, e, minCapacity);
        });
    }

    private void updateEdge(Graph residualGraph, Edge edge, double minCapacity)
    {
        double newWeight = edge.getWeight() - minCapacity;
        if (newWeight <= 0)
        {
            residualGraph.removeEdge(edge);
        }
        else
        {
            edge.setWeight(newWeight);
        }
    }

    private void updateReversiveEdge(Graph residualGraph, Edge edge, double minCapacity)
    {
        Edge reversiveEdge = residualGraph.getEdge(edge.getSink(), edge.getSource());
        if (reversiveEdge != null)
        {
            double newWeight = reversiveEdge.getWeight() + minCapacity;
            reversiveEdge.setWeight(newWeight);
        }
        else
        {
            reversiveEdge = new Edge(edge.getSink(), edge.getSource(), minCapacity);
            residualGraph.addEdge(reversiveEdge);
        }
    }
}
