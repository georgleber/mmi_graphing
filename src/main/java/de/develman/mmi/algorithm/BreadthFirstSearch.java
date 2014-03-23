package de.develman.mmi.algorithm;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.*;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class BreadthFirstSearch<T, V extends Vertex<T>>
{
    private final Graph<T, V> graph;
    private final List<Vertex<T>> visitList;

    public BreadthFirstSearch(Graph<T, V> graph)
    {
        this.graph = graph;
        this.visitList = new ArrayList<>();
    }

    public List<V> doSearch(V startVertex)
    {
        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(startVertex);

        while (!queue.isEmpty())
        {
            Vertex<T> nextVertex = queue.remove();
            if (visitList.contains(nextVertex))
            {
                continue;
            }

            visitList.add(nextVertex);
            nextVertex.getSuccessors().forEach(vertex -> queue.add(vertex));
        }

        List<V> startVertices = new ArrayList<>();
        startVertices.add(startVertex);

        V notVisited = findNotVisited();
        if (notVisited != null)
        {
            startVertices.addAll(doSearch(notVisited));
        }

        return startVertices;
    }

    private V findNotVisited()
    {
        for (V v : graph.getVertices())
        {
            if (!visitList.contains(v))
            {
                return v;
            }
        }

        return null;
    }
}
