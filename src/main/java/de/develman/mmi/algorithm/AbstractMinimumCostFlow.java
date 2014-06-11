package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.Collection;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public abstract class AbstractMinimumCostFlow
{
    protected void updateResidualEdge(Graph graph, Edge edge, double capacity)
    {
        updateEdge(graph, edge, capacity);
        updateReversiveEdge(graph, edge, capacity);
    }

    protected boolean checkVerticesBalanced(Collection<Vertex> vertices)
    {
        return vertices.stream().mapToDouble(Vertex::getBalance).sum() == 0;
    }

    protected double calculateMinimalCostFlow(Graph graph, Graph residualGraph)
    {
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

    private void updateEdge(Graph graph, Edge edge, double capacity)
    {
        double newCapacity = edge.getCapacity() - capacity;
        if (newCapacity <= 0)
        {
            graph.removeEdge(edge);
        }
        else
        {
            edge.setCapacity(newCapacity);
        }
    }

    private void updateReversiveEdge(Graph graph, Edge edge, double capacity)
    {
        Edge reversiveEdge = graph.getEdge(edge.getSink(), edge.getSource());
        if (reversiveEdge != null)
        {
            double newCapacity = reversiveEdge.getCapacity() + capacity;
            reversiveEdge.setCapacity(newCapacity);
        }
        else
        {
            double cost = edge.getCost() != 0.0 ? edge.getCost() * - 1 : 0.0;
            reversiveEdge = new Edge(edge.getSink(), edge.getSource(), capacity, cost);
            graph.addEdge(reversiveEdge);
        }
    }
}
