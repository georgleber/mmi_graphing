package de.develman.mmi.algorithm;

import de.develman.mmi.exception.MinimalCostFlowException;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
        Vertex superSource = addSuperSource(residualGraph);
        Vertex superSink = addSuperSink(residualGraph);

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

    private Vertex addSuperSource(Graph graph)
    {
        Vertex superSource = new Vertex(-1);
        graph.addVertex(superSource);

        List<Vertex> sources = balancedVertices(graph, true);
        sources.forEach(v ->
        {
            Edge edge = new Edge(superSource, v, v.getBalance(), 0.0);
            graph.addEdge(edge, 0);
        });

        return superSource;
    }

    private Vertex addSuperSink(Graph graph)
    {
        Vertex superSink = new Vertex(-2);
        graph.addVertex(superSink);
        List<Vertex> sinks = balancedVertices(graph, false);
        sinks.forEach(v ->
        {
            double balance = v.getBalance() * -1;
            Edge edge = new Edge(v, superSink, balance, 0.0);
            graph.addEdge(edge);
        });

        return superSink;
    }

    private List<Vertex> balancedVertices(Graph graph, boolean greaterZero)
    {
        return graph.getVertices().stream().filter(balanceFilter(greaterZero)).collect(
                Collectors.toList());
    }

    private Predicate<Vertex> balanceFilter(boolean greaterZero)
    {
        Predicate<Vertex> pred;
        if (greaterZero)
        {
            pred = v -> v.getBalance() > 0;
        }
        else
        {
            pred = v -> v.getBalance() < 0;
        }

        return pred;
    }
}
