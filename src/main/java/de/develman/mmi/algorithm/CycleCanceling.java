package de.develman.mmi.algorithm;

import de.develman.mmi.exception.MinimalCostFlowException;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.util.GraphUtil;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;

/**
 * Die Klasse CycleCanceling implementiert den Cycle-Cancelling Algorithmus zur Berechnung des kostenminimalen Flusses
 * in einem gerichteten Graphen mit Kapazitäten und Kosten
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class CycleCanceling extends AbstractMinimumCostFlow
{
    @Inject
    EdmondsKarp edmondsKarp;
    @Inject
    MooreBellmanFord mooreBellmanFord;

    /**
     * Berechnung des kostenminimalen Flusses
     *
     * @param graph gerichteter Graph
     * @return Kosten des kostenminimalen Flusses
     * @throws MinimalCostFlowException
     */
    public double findMinimumCostFlow(Graph graph) throws MinimalCostFlowException
    {
        if (!checkVerticesBalanced(graph.getVertices()))
        {
            throw new MinimalCostFlowException("Balancen sind nicht ausgeglichen");
        }

        Graph residualGraph = graph.copy();
        Vertex superSource = GraphUtil.addSuperSource(residualGraph);
        Vertex superSink = GraphUtil.addSuperSink(residualGraph);

        if (!checkConvenientCapacity(residualGraph, superSource, superSink))
        {
            throw new MinimalCostFlowException("Kapazitäten sind nicht ausgeglichen");
        }

        List<Edge> cycle;
        while ((cycle = getNegativeCycle(residualGraph)) != null)
        {
            double gamma = cycle.stream().mapToDouble(Edge::getCapacity).min().getAsDouble();

            cycle.forEach(e ->
            {
                updateResidualEdge(residualGraph, e, gamma);
            });
        }

        return calculateMinimalCostFlow(graph, residualGraph);
    }

    private List<Edge> getNegativeCycle(Graph graph)
    {
        graph.unvisitAllVertices();
        List<Edge> cycle = null;

        do
        {
            Vertex vertex = findUnvisitVertex(graph);
            if (vertex == null)
            {
                break;
            }

            cycle = mooreBellmanFord.findNegativeCycle(graph, vertex);
        }
        while (cycle == null);

        return cycle;
    }

    private Vertex findUnvisitVertex(Graph graph)
    {
        Optional<Vertex> possibleVertex = graph.getVertices().stream().filter(v -> !v.isVisited()).findFirst();

        Vertex unvisitVertex = null;
        if (possibleVertex.isPresent())
        {
            unvisitVertex = possibleVertex.get();
        }

        return unvisitVertex;
    }

    private boolean checkConvenientCapacity(Graph graph, Vertex superSource, Vertex superSink)
    {
        double totalCapacity = superSource.getOutgoingEdges().stream().mapToDouble(Edge::getCapacity).sum();
        double maxFlow = edmondsKarp.calculateMaxFlowGraph(graph, superSource, superSink);

        return maxFlow == totalCapacity;
    }
}
