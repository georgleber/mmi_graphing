package de.develman.mmi.model;

import de.develman.mmi.exception.DuplicateVertexException;
import de.develman.mmi.exception.MissingVertexException;
import java.util.*;

/**
 * Die Klasse Graph implementiert einen Graphen
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Graph
{
    private final boolean directed;
    private final Map<Integer, Vertex> vertices = new HashMap<>();
    private final Set<Edge> edges = new HashSet<>();
    
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
    
    public void unvisitAllVertices()
    {
        vertices.values().forEach(vertex -> vertex.setVisited(false));
    }
    
    public Collection<Vertex> getVertices()
    {
        return Collections.unmodifiableCollection(vertices.values());
    }
    
    public boolean containsVertex(Vertex v)
    {
        return containsVertex(v.getKey());
    }
    
    public boolean containsVertex(Integer key)
    {
        return vertices.containsKey(key);
    }
    
    public void addVertex(Vertex v) throws DuplicateVertexException
    {
        Integer key = v.getKey();
        if (vertices.containsKey(key))
        {
            throw new DuplicateVertexException(key);
        }
        
        vertices.put(key, v);
    }
    
    public void removeVertex(Integer key)
    {
        Vertex vertex = vertices.get(key);
        if (vertex != null)
        {
            vertex.getIncomingEdges().forEach(edge -> removeEdge(edge));
            vertex.getOutgoingEdges().forEach(edge -> removeEdge(edge));
            
            vertices.remove(key);
        }
    }
    
    public Vertex getVertex(Integer key)
    {
        return vertices.get(key);
    }
    
    public Collection<Edge> getEdges()
    {
        return Collections.unmodifiableCollection(edges);
    }
    
    public void addEdge(Edge edge)
    {
        Vertex source = edge.getSource();
        if (!containsVertex(source.getKey()))
        {
            throw new MissingVertexException(source.getKey());
        }
        
        Vertex sink = edge.getSink();
        if (!containsVertex(sink.getKey()))
        {
            throw new MissingVertexException(sink.getKey());
        }
        
        source.addOutgoingEdge(edge);
        sink.addIncomingEdge(edge);
        edges.add(edge);
    }
    
    public void removeEdge(Edge edge)
    {
        Vertex source = edge.getSource();
        source.removeOutgoingEdge(edge);
        
        Vertex sink = edge.getSink();
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
