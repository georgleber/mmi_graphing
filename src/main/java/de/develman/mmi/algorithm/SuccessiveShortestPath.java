package de.develman.mmi.algorithm;

import de.develman.mmi.exception.MinimalCostFlowException;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class SuccessiveShortestPath
{
    private Map<Vertex, Double> relevantBalances;

    public double findMinimumCostFlow(Graph graph) throws MinimalCostFlowException
    {
        Graph residualGraph = graph.copy();

        updateCapacities(residualGraph);
        calculateRelevantBalances(residualGraph);

        if (relevantBalances.values().stream().mapToDouble(Double::doubleValue).sum() != 0)
        {

            return calculateMinimumCostFlow(residualGraph);
        }

        return 0.0;
    }

    private double calculateMinimumCostFlow(Graph residualGraph)
    {
        double cost = 0.0;
        for (Edge edge : residualGraph.getEdges())
        {
            cost += edge.getCapacity() * edge.getCost();
        }

        return cost;
    }

    private void updateCapacities(Graph graph)
    {
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.forEach(e ->
        {
            if (e.getCost() >= 0.0)
            {
                e.setCapacity(0.0);
            }
        });
    }

    private void calculateRelevantBalances(Graph graph)
    {
        relevantBalances = new HashMap<>();
        graph.getVertices().forEach(v ->
        {
            double sumOut = v.getOutgoingEdges().stream().mapToDouble(Edge::getCapacity).sum();
            double sumIn = v.getIncomingEdges().stream().mapToDouble(Edge::getCapacity).sum();

            relevantBalances.put(v, sumOut - sumIn);
        });
    }
}
