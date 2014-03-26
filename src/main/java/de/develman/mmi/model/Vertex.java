package de.develman.mmi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Vertex repräsentiert einen Knoten im Graphen
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Vertex
{
    private final Integer key;
    private boolean visited = false;
    private final List<Edge> incomingEdges = new ArrayList<>();
    private final List<Edge> outgoingEdges = new ArrayList<>();

    public Vertex(Integer key)
    {
        this.key = key;
    }

    /**
     * @return Gibt den Schlüssel des Knotens zurück
     */
    public Integer getKey()
    {
        return key;
    }

    /**
     * @return {@code true}, wenn der Knoten besucht wurde, sonst {@code false}
     */
    public boolean isVisited()
    {
        return visited;
    }

    /**
     * Setzt den Status, ob der Knoten besucht wurde oder nicht
     *
     * @param visited Status, ob der Knoten besucht wurde oder nicht
     */
    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }

    /**
     * @return Liste der ankommenden Kanten
     */
    public List<Edge> getIncomingEdges()
    {
        return new ArrayList<>(incomingEdges);
    }

    /**
     * Hinzufügen einer Ankommende Kante
     *
     * @param edge Kante
     */
    public void addIncomingEdge(Edge edge)
    {
        incomingEdges.add(edge);
    }

    /**
     * Löschen einer ankommenden Kante
     *
     * @param edge Kante
     */
    public void removeIncomingEdge(Edge edge)
    {
        incomingEdges.remove(edge);
    }

    /**
     * @return Liste der abgehenden Kanten
     */
    public List<Edge> getOutgoingEdges()
    {
        return new ArrayList<>(outgoingEdges);
    }

    /**
     * Hinzufügen einer abgehenden Kante
     *
     * @param edge Kante
     */
    public void addOutgoingEdge(Edge edge)
    {
        outgoingEdges.add(edge);
    }

    /**
     * Löschen einer abgehenden Kante
     *
     * @param edge Kante
     */
    public void removeOutgoingEdge(Edge edge)
    {
        outgoingEdges.remove(edge);
    }

    /**
     * @return Liste aller nachfolgenden Knoten (Kindknoten)
     */
    public List<Vertex> getSuccessors()
    {
        List<Vertex> successors = new ArrayList<>();
        outgoingEdges.forEach(edge -> successors.add(edge.getSink()));

        return successors;
    }

    /**
     * @return Liste aller vorhergehenden Knoten (Elternknoten)
     */
    public List<Vertex> getPredecessors()
    {
        List<Vertex> predecessors = new ArrayList<>();
        incomingEdges.forEach(edge -> predecessors.add(edge.getSource()));

        return predecessors;
    }

    @Override
    public String toString()
    {
        return key.toString();
    }
}
