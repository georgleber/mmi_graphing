package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.algorithm.ShortestPath;
import java.util.*;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public abstract class AbstractShortestPath
{
    protected Map<Vertex, Double> distance;
    protected Map<Vertex, Vertex> predecessor;

    protected void init(Graph graph, Vertex startVertex)
    {
        initDistances(graph, startVertex);
        initPredecessors(startVertex);
    }

    protected void updateCost(Edge e)
    {
        Vertex source = e.getSource();
        Vertex sink = e.getSink();
        double newDistance = distance.get(source) + e.getCost();
        if (newDistance < distance.get(sink))
        {
            distance.put(sink, newDistance);
            predecessor.put(sink, source);
        }
    }

    protected ShortestPath loadShortestPath(Vertex startVertex, Vertex endVertex)
    {
        ShortestPath path = null;

        double length = distance.get(endVertex);
        if (!Double.isInfinite(length))
        {
            List<Edge> edges = new ArrayList<>();

            Vertex pred;
            Vertex current = endVertex;
            do
            {
                pred = predecessor.get(current);
                edges.add(pred.getEdgeTo(current.getKey()));

                if (pred == startVertex)
                {
                    break;
                }

                current = pred;
            }
            while (true);

            path = new ShortestPath();
            path.setLength(length);
            path.setEdges(edges);
        }

        return path;
    }

    private void initDistances(Graph graph, Vertex startVertex)
    {
        distance = new HashMap<>(graph.countVertices());
        graph.getVertices().forEach(v -> distance.put(v, Double.POSITIVE_INFINITY));
        distance.put(startVertex, 0.0);
    }

    private void initPredecessors(Vertex startVertex)
    {
        predecessor = new HashMap<>();
        predecessor.put(startVertex, startVertex);
    }
}
