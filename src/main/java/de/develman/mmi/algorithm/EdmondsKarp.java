package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.List;
import javax.inject.Inject;

/**
 * Die Klasse EdmondsKarp implementiert den Edmonds-Karp Algorithmus zur Berechnung des maximalen Flusses in einem
 * gerichteten Graphen
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class EdmondsKarp
{
    @Inject
    BreadthFirstSearch breadthSearch;

    /**
     * Berechnung des maximalen Flusses im Residualgraphen
     *
     * @param residualGraph Residualgraph
     * @param startVertex Startknoten
     * @param endVertex Endknoten
     * @return Liefert den maximalen Fluss im Graphen
     */
    public double calculateMaxFlowGraph(Graph residualGraph, Vertex startVertex, Vertex endVertex)
    {
        return calculateFlow(residualGraph, startVertex, endVertex);
    }

    /**
     * Berechnung des maximalen Flusses
     *
     * @param graph Gerichteter Graph
     * @param startVertex Startknoten
     * @param endVertex Endknoten
     * @return Liefert den maximalen Fluss im Graphen
     */
    public double findMaxFlow(Graph graph, Vertex startVertex, Vertex endVertex)
    {
        double maxFlow = 0.0;
        if (startVertex.equals(endVertex))
        {
            return maxFlow;
        }

        Graph residualGraph = graph.copy();
        Vertex start = residualGraph.getVertex(startVertex.getKey());
        Vertex end = residualGraph.getVertex(endVertex.getKey());

        return calculateFlow(residualGraph, start, end);
    }

    private double calculateFlow(Graph residualGraph, Vertex start, Vertex end)
    {
        double maxFlow = 0.0;

        List<Edge> path;
        while ((path = findPath(residualGraph, start, end)) != null)
        {
            double minCapacity = path.stream().mapToDouble(Edge::getCapacity).min().getAsDouble();
            updateResidualGraph(residualGraph, path, minCapacity);

            maxFlow += minCapacity;
        }

        return maxFlow;
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
        double newCapacity = edge.getCapacity() - minCapacity;
        if (newCapacity <= 0)
        {
            residualGraph.removeEdge(edge);
        }
        else
        {
            edge.setCapacity(newCapacity);
        }
    }

    private void updateReversiveEdge(Graph residualGraph, Edge edge, double minCapacity)
    {
        Edge reversiveEdge = residualGraph.getEdge(edge.getSink(), edge.getSource());
        if (reversiveEdge != null)
        {
            double newCapacity = reversiveEdge.getCapacity() + minCapacity;
            reversiveEdge.setCapacity(newCapacity);
        }
        else
        {
            double cost = edge.getCost() != 0.0 ? edge.getCost() * - 1 : 0.0;
            reversiveEdge = new Edge(edge.getSink(), edge.getSource(), minCapacity, cost);
            residualGraph.addEdge(reversiveEdge);
        }
    }
}
