package de.develman.mmi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Vertex<T>
{
    private final T key;
    private List<Edge<T>> incomingEdges;
    private List<Edge<T>> outgoingEdges;

    public Vertex(T key)
    {
        this.key = key;

        this.incomingEdges = new ArrayList<>(0);
        this.outgoingEdges = new ArrayList<>(0);
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
        outgoingEdges.stream().forEach((edge) -> successors.add(edge.getSink()));

        return successors;
    }

    public List<Vertex<T>> getPredecessors()
    {
        List<Vertex<T>> predecessors = new ArrayList<>();
        incomingEdges.stream().forEach((edge) -> predecessors.add(edge.getSource()));

        return predecessors;
    }

    @Override
    public String toString()
    {
        return key.toString();
    }
}
