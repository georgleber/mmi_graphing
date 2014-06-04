package de.develman.mmi.algorithm;

import de.develman.mmi.exception.MinimalCostFlowException;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Die Klasse CycleCanceling implementiert den Cycle-Cancelling Algorithmus zur Berechnung des kostenminimalen Flusses
 * in einem gerichteten Graphen mit Kapazitäten und Kosten
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class CycleCanceling
{
    private static final Logger LOG = LoggerFactory.getLogger(CycleCanceling.class);

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
        while ((cycle = getNegativeCycle(residualGraph, superSink)) != null)
        {
            double gamma = cycle.stream().mapToDouble(Edge::getCapacity).min().getAsDouble();

            cycle.forEach(e ->
            {
                updateEdge(residualGraph, e, gamma);
                updateReversiveEdge(residualGraph, e, gamma);
            });
        }

        double cost = 0.0;
        for (Edge residualEdge : residualGraph.getEdges())
        {
            Vertex source = graph.getVertex(residualEdge.getSource().getKey());
            Vertex sink = graph.getVertex(residualEdge.getSink().getKey());

            if (source == null || sink == null)
            {
                continue;
            }

            Edge originalEdge = graph.getEdge(sink, source);
            if (originalEdge != null)
            {
                cost += residualEdge.getCapacity() * originalEdge.getCost();
            }
        }

        return cost;
    }

    private List<Edge> getNegativeCycle(Graph graph, Vertex vertex)
    {
        List<Edge> cycle = mooreBellmanFord.findNegativeCycle(graph, vertex);
        LOG.debug("Found cycle: " + cycle);

        return cycle;
    }

    private boolean checkConvenientCapacity(Graph graph, Vertex superSource, Vertex superSink)
    {
        double totalCapacity = superSource.getOutgoingEdges().stream().mapToDouble(Edge::getCapacity).sum();
        double maxFlow = edmondsKarp.calculateMaxFlowGraph(graph, superSource, superSink);

        return maxFlow == totalCapacity;
    }

    private boolean checkVerticesBalanced(Collection<Vertex> vertices)
    {
        return vertices.stream().mapToDouble(Vertex::getBalance).sum() == 0;
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

    private void updateEdge(Graph graph, Edge edge, double gamma)
    {
        double newCapacity = edge.getCapacity() - gamma;
        if (newCapacity <= 0)
        {
            graph.removeEdge(edge);
        }
        else
        {
            edge.setCapacity(newCapacity);
        }
    }

    private void updateReversiveEdge(Graph graph, Edge edge, double gamma)
    {
        Edge reversiveEdge = graph.getEdge(edge.getSink(), edge.getSource());
        if (reversiveEdge != null)
        {
            double newCapacity = reversiveEdge.getCapacity() + gamma;
            reversiveEdge.setCapacity(newCapacity);
        }
        else
        {
            double cost = edge.getCost() != 0.0 ? edge.getCost() * - 1 : 0.0;
            reversiveEdge = new Edge(edge.getSink(), edge.getSource(), gamma, cost);
            graph.addEdge(reversiveEdge);
        }
    }
}
