package de.develman.mmi.model;

import java.util.*;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Graph<T, V extends Vertex<T>>
{
    private final boolean directed;
    private Map<T, V> vertices = new HashMap<>();
    private Set<Edge<T>> edges = new HashSet<>();

    public Graph(boolean directed)
    {
        this.directed = directed;
    }

    public boolean isDirected()
    {
        return directed;
    }

    public int countVertices()
    {
        return vertices.keySet().size();
    }

    public int countEdges()
    {
        return edges.size();
    }

    public Collection<V> getVertices()
    {
        return Collections.unmodifiableCollection(vertices.values());
    }

    public boolean containsVertex(V v)
    {
        return containsVertex(v.getKey());
    }

    public boolean containsVertex(T key)
    {
        return vertices.containsKey(key);
    }

    public void addVertex(V v)
    {
        T key = v.getKey();
        if (vertices.containsKey(key))
        {
            // Todo: handle duplicates
        }

        vertices.put(key, v);
    }

    public void removeVertex(T key)
    {
        V vertex = vertices.get(key);
        if (vertex != null)
        {
            // TODO: remove edges
            vertices.remove(key);
        }
    }

    public Collection<Edge<T>> getEdges()
    {
        return Collections.unmodifiableCollection(edges);
    }

    public String printVertexList()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Knoten: {");

        vertices.values().stream().forEach((V x) ->
        {
            builder.append(x);
            builder.append(",");
        });

        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");

        return builder.toString();
    }

    public void printEdgeList()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Kanten: {\n");

        boolean minor = false;
        /*
         for (Edge edge : edges)
         {
         if (minor)
         {
         builder.append(",\n");
         }

         builder.append(edge.getVertexA());
         builder.append(" -> ");
         builder.append(edge.getVertexB());
         minor = true;
         }
         */

        builder.append("}");
        System.out.println(builder);
    }
}
