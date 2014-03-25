package de.develman.mmi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Vertex repräsentiert einen Knoten im Graphen
 *
 * @param <T> Typ des Schlüssels des Knoten
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Vertex<T>
{
    private final T key;
    private final List<Edge<T>> incomingEdges = new ArrayList<>();
    private final List<Edge<T>> outgoingEdges = new ArrayList<>();

    public Vertex(T key)
    {
        this.key = key;
    }

    public T getKey()
    {
        return key;
    }

    public List<Edge<T>> getIncomingEdges()
    {
        return new ArrayList<>(incomingEdges);
    }

    public void addIncomingEdge(Edge<T> edge)
    {
        incomingEdges.add(edge);
    }

    public void removeIncomingEdge(Edge<T> edge)
    {
        incomingEdges.remove(edge);
    }

    public List<Edge<T>> getOutgoingEdges()
    {
        return new ArrayList<>(outgoingEdges);
    }

    public void addOutgoingEdge(Edge<T> edge)
    {
        outgoingEdges.add(edge);
    }

    public void removeOutgoingEdge(Edge<T> edge)
    {
        outgoingEdges.remove(edge);
    }

    public List<Vertex<T>> getSuccessors()
    {
        List<Vertex<T>> successors = new ArrayList<>();
        outgoingEdges.forEach(edge -> successors.add(edge.getSink()));

        return successors;
    }

    public List<Vertex<T>> getPredecessors()
    {
        List<Vertex<T>> predecessors = new ArrayList<>();
        incomingEdges.forEach(edge -> predecessors.add(edge.getSource()));

        return predecessors;
    }

    @Override
    public String toString()
    {
        return key.toString();
    }
}
