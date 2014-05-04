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
    protected Graph graph;
    protected Map<Vertex, Double> distance;
    protected Vertex[] predecessor;

    protected void init(Graph graph, Vertex startVertex)
    {
        initDistances(graph, startVertex);
        initPredecessors(graph.countVertices(), startVertex);
    }

    protected void updateWeigth(Edge e)
    {
        Vertex source = e.getSource();
        Vertex sink = e.getSink();
        double newDistance = distance.get(source) + e.getWeight();
        if (newDistance < distance.get(sink))
        {
            distance.put(sink, newDistance);
            predecessor[sink.getKey()] = source;
        }
    }

    protected ShortestPath loadShortestPath(Vertex startVertex, Vertex endVertex)
    {
        ShortestPath path = null;

        double length = distance.get(endVertex);
        if (!Double.isInfinite(length))
        {
            List<Vertex> vertices = new ArrayList<>();
            vertices.add(endVertex);

            Vertex pred = endVertex;
            while (pred != startVertex)
            {
                pred = predecessor[pred.getKey()];
                vertices.add(pred);
            }
            Collections.reverse(vertices);

            path = new ShortestPath();
            path.setLength(length);
            path.setVertices(vertices);
        }

        return path;
    }

    private void initDistances(Graph graph, Vertex startVertex)
    {
        distance = new HashMap<>(graph.countVertices());
        graph.getVertices().forEach(v -> distance.put(v, Double.POSITIVE_INFINITY));
        distance.put(startVertex, 0.0);
    }

    private void initPredecessors(int countVertices, Vertex startVertex)
    {
        predecessor = new Vertex[countVertices];
        predecessor[startVertex.getKey()] = startVertex;
    }
}
