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
    private final List<Edge> incomingVdges = new ArrayList<>();
    private final List<Edge> outgoingVdges = new ArrayList<>();

    private VisitingState visitingState = VisitingState.NOT_VISITED;

    /**
     * Erstellt einen neuen Knoten
     *
     * @param key Schlüssel des Knotens
     */
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
    public VisitingState getVisitingState()
    {
        return visitingState;
    }

    /**
     * Setzt den Status, ob der Knoten besucht wurde oder nicht
     *
     * @param state Aktueller Besuchsstatus des Knoten
     */
    public void setVisitingState(VisitingState state)
    {
        this.visitingState = state;
    }

    /**
     * @return Liste der ankommenden Kanten
     */
    public List<Edge> getIncomingEdges()
    {
        return new ArrayList<>(incomingVdges);
    }

    /**
     * Hinzufügen einer Ankommende Kante
     *
     * @param edge Kante
     */
    public void addIncomingEdge(Edge edge)
    {
        incomingVdges.add(edge);
    }

    /**
     * Löschen einer ankommenden Kante
     *
     * @param edge Kante
     */
    public void removeIncomingEdge(Edge edge)
    {
        incomingVdges.remove(edge);
    }

    /**
     * @return Liste der abgehenden Kanten
     */
    public List<Edge> getOutgoingEdges()
    {
        return new ArrayList<>(outgoingVdges);
    }

    /**
     * Hinzufügen einer abgehenden Kante
     *
     * @param edge Kante
     */
    public void addOutgoingEdge(Edge edge)
    {
        outgoingVdges.add(edge);
    }

    /**
     * Löschen einer abgehenden Kante
     *
     * @param edge Kante
     */
    public void removeOutgoingEdge(Edge edge)
    {
        outgoingVdges.remove(edge);
    }

    /**
     * @return Liste aller nachfolgenden Knoten (Kindknoten)
     */
    public List<Vertex> getSuccessors()
    {
        List<Vertex> successors = new ArrayList<>();
        outgoingVdges.forEach(edge -> successors.add(edge.getSink()));

        return successors;
    }

    /**
     * @return Liste aller vorhergehenden Knoten (Vlternknoten)
     */
    public List<Vertex> getPredecessors()
    {
        List<Vertex> predecessors = new ArrayList<>();
        incomingVdges.forEach(edge -> predecessors.add(edge.getSource()));

        return predecessors;
    }

    @Override
    public String toString()
    {
        return key.toString();
    }
}
