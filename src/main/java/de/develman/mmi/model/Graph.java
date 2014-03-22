package de.develman.mmi.model;

import de.develman.mmi.exception.DuplicateVertexException;
import de.develman.mmi.exception.MissingVertexException;
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

    public void addVertex(V v) throws DuplicateVertexException
    {
        T key = v.getKey();
        if (vertices.containsKey(key))
        {
            throw new DuplicateVertexException(key);
        }

        vertices.put(key, v);
    }

    public void removeVertex(T key)
    {
        V vertex = vertices.get(key);
        if (vertex != null)
        {
            vertex.getIncomingEdges().forEach(edge -> removeEdge(edge));
            vertex.getOutgoingEdges().forEach(edge -> removeEdge(edge));

            vertices.remove(key);
        }
    }

    public Collection<Edge<T>> getEdges()
    {
        return Collections.unmodifiableCollection(edges);
    }

    public void addEdge(Edge<T> edge)
    {
        Vertex<T> source = edge.getSource();
        if (!containsVertex(source.getKey()))
        {
            throw new MissingVertexException(source.getKey());
        }

        Vertex<T> sink = edge.getSink();
        if (!containsVertex(sink.getKey()))
        {
            throw new MissingVertexException(sink.getKey());
        }

        source.addOutgoingEdge(edge);
        sink.addIncomingEdge(edge);
        edges.add(edge);
    }

    public void removeEdge(Edge<T> edge)
    {
        Vertex<T> source = edge.getSource();
        source.removeOutgoingEdge(edge);

        Vertex<T> sink = edge.getSink();
        sink.removeIncomingEdge(edge);

        edges.remove(edge);
    }

    public String printVertexList()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Knoten: {");

        vertices.values().forEach(vertex ->
        {
            builder.append(vertex);
            builder.append(",");
        });

        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");

        return builder.toString();
    }

    public String printEdgeList()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Kanten: {\n");

        edges.forEach(edge ->
        {
            builder.append(edge.getSource());
            builder.append(" -> ");
            builder.append(edge.getSink());
            builder.append(",\n");
        });

        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");

        return builder.toString();
    }
}
