package de.develman.mmi.algorithm;

import de.develman.mmi.exception.MinimalCostFlowException;
import de.develman.mmi.exception.NegativeCycleException;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.algorithm.ShortestPath;
import java.util.*;
import javax.inject.Inject;

/**
 * Die Klasse SuccessiveShortestPath implementiert den Successive-Shortest-Path Algorithmus zur Berechnung des
 * kostenminimalen Flusses in einem gerichteten Graphen mit Kapazitäten und Kosten
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class SuccessiveShortestPath extends AbstractMinimumCostFlow
{
    @Inject
    MooreBellmanFord mooreBellmanFord;
    @Inject
    BreadthFirstSearch breadthFirstSearch;

    private Map<Vertex, Double> relevantBalances;

    /**
     * Berechnung des kostenminimalen Flusses
     *
     * @param graph gerichteter Graph
     * @return Kosten des kostenminimalen Flusses
     * @throws MinimalCostFlowException
     * @throws NegativeCycleException
     */
    public double findMinimumCostFlow(Graph graph) throws MinimalCostFlowException, NegativeCycleException
    {
    	Graph residualGraph = calculateResidualGraph(graph);
        return calculateMinimalCostFlow(graph, residualGraph);
    }
    
    /**
     * Berechnung den Residualgraphen zu einem gegebenen Graphen
     *  
     * @param graph gerichteter Graph
     * @return Residualgraph mit den minimalen Kosten
     * @throws MinimalCostFlowException
     * @throws NegativeCycleException
     */
    public Graph calculateResidualGraph(Graph graph) throws MinimalCostFlowException, NegativeCycleException
    {
    	 if (!checkVerticesBalanced(graph.getVertices()))
         {
             throw new MinimalCostFlowException("Balancen sind nicht ausgeglichen");
         }

         Graph residualGraph = graph.copy();
         initRelevantBalances(residualGraph);
         updateCapacities(residualGraph);

         while (true)
         {
             residualGraph.unvisitAllVertices();

             Vertex source = findSource(residualGraph);
             if (source == null)
             {
                 break;
             }

             Vertex sink = findSink(source);
             if (sink == null)
             {
                 throw new MinimalCostFlowException("Das Netzwerk ist zu klein (Keine Senke für Quelle [" + source + "] gefunden.");
             }

             ShortestPath path = mooreBellmanFord.findShortestPath(residualGraph, source, sink);
             double minCapacity = path.getEdges().stream().mapToDouble(Edge::getCapacity).min().getAsDouble();
             double minSourceBalance = source.getBalance() - relevantBalances.get(source);
             double minSinkBalance = relevantBalances.get(sink) - sink.getBalance();

             double gamma = calculateGamma(minCapacity, minSourceBalance, minSinkBalance);

             addBalance(source, gamma);
             addBalance(sink, gamma * -1);

             path.getEdges().forEach(e -> updateResidualEdge(residualGraph, e, gamma));
         }
         
         return residualGraph;
    }

    private Vertex findSource(Graph graph)
    {
        for (Vertex v : graph.getVertices())
        {
            if (v.getBalance() - relevantBalances.get(v) > 0.0)
            {
                return v;
            }
        }

        return null;
    }

    private Vertex findSink(Vertex source)
    {
        List<Vertex> vertices = breadthFirstSearch.getAccessibleVertices(source);
        for (Vertex v : vertices)
        {
            if (v == source)
            {
                continue;
            }

            if (v.getBalance() - relevantBalances.get(v) < 0.0)
            {
                return v;
            }
        }

        return null;
    }

    private void updateCapacities(Graph graph)
    {
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.forEach(e ->
        {
            if (e.getCost() < 0.0)
            {
                updateResidualEdge(graph, e, e.getCapacity());
                updateBalances(e, e.getCapacity());
            }
        });
    }

    private void updateBalances(Edge edge, double capacity)
    {
        addBalance(edge.getSource(), capacity);
        addBalance(edge.getSink(), capacity * -1);
    }

    private void addBalance(Vertex vertex, double balance)
    {
        double oldBalance = relevantBalances.get(vertex);
        relevantBalances.put(vertex, oldBalance + balance);
    }

    private void initRelevantBalances(Graph graph)
    {
        relevantBalances = new HashMap<>();
        graph.getVertices().forEach(v -> relevantBalances.put(v, 0.0));
    }

    private double calculateGamma(double a, double b, double c)
    {
        double gamma = a;
        if (gamma > b)
        {
            gamma = b;
        }
        if (gamma > c)
        {
            gamma = c;
        }

        return gamma;
    }
}
