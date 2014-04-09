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

    /**
     * Erstellt ein neues Graph-Objekt
     *
     * @param directed Gibt an, ob der Graph gerichtet ist oder nicht
     */
    public Graph(boolean directed)
    {
        this.directed = directed;
    }

    /**
     * @return {@code true}, wenn der Graph gerichtet ist, sonst {@code false}
     */
    public boolean isDirected()
    {
        return directed;
    }

    /**
     * @return Anzahl der Knoten
     */
    public int countVertices()
    {
        return vertices.keySet().size();
    }

    /**
     * @return Anzahl der Kanten
     */
    public int countEdges()
    {
        return edges.size();
    }

    /**
     * Markiert alle Knoten als nicht besucht
     */
    public void unvisitAllVertices()
    {
        vertices.values().forEach(vertex -> vertex.setVisited(false));
    }

    /**
     * @return Unmodifizierbare Collection aller Knoten
     */
    public Collection<Vertex> getVertices()
    {
        return Collections.unmodifiableCollection(vertices.values());
    }

    /**
     * Prüft ob der Knoten im Graph vorhanden ist
     *
     * @param vertex Knoten
     * @return {@code true}, wenn der Knoten vorhanden ist, sonst {@code false}
     */
    public boolean containsVertex(Vertex vertex)
    {
        return containsVertex(vertex.getKey());
    }

    /**
     * Prüft ob der Knoten im Graph vorhanden ist (wird durch den Schlüssel identifiziert)
     *
     * @param key Schlüssel des Knoten
     * @return {@code true}, wenn der Knoten vorhanden ist, sonst {@code false}
     */
    public boolean containsVertex(Integer key)
    {
        return vertices.containsKey(key);
    }

    /**
     * Hinzufügen eines Knotens zum Graphen
     *
     * @param vertex Knoten
     * @throws DuplicateVertexException
     */
    public void addVertex(Vertex vertex) throws DuplicateVertexException
    {
        Integer key = vertex.getKey();
        if (vertices.containsKey(key))
        {
            throw new DuplicateVertexException(key);
        }

        vertices.put(key, vertex);
    }

    /**
     * Löschen eines Knoten aus dem Graphen
     *
     * @param key Schlüssel des Knoten
     */
    public void removeVertex(Integer key)
    {
        Vertex vertex = vertices.get(key);
        if (vertex != null)
        {
            vertex.getIncomingEdges().forEach(e -> removeEdge(e));
            vertex.getOutgoingEdges().forEach(edge -> removeEdge(edge));

            vertices.remove(key);
        }
    }

    /**
     * Liefert den Knoten anhand des Schlüssels
     *
     * @param key Schlüssel des Knoten
     * @return Der Knoten mit dem Schlüssel, oder {@code null} wenn nicht vorhanden
     */
    public Vertex getVertex(Integer key)
    {
        return vertices.get(key);
    }

    /**
     * @return Liefert eine unmodifizierbare Collection der Kanten
     */
    public Collection<Edge> getEdges()
    {
        return Collections.unmodifiableCollection(edges);
    }

    /**
     * Hinzufügen einer Kante zum Graphen
     *
     * @param edge Kante
     */
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

        if (!isDirected())
        {
            Edge reverseEdge = edge.revert();
            source.addIncomingEdge(reverseEdge);
            sink.addOutgoingEdge(reverseEdge);
        }
    }

    /**
     * Löschen einer Kante aus dem Graphen
     *
     * @param edge Kante
     */
    public void removeEdge(Edge edge)
    {
        Vertex source = edge.getSource();
        source.removeOutgoingEdge(edge);

        Vertex sink = edge.getSink();
        sink.removeIncomingEdge(edge);

        edges.remove(edge);
    }
}
