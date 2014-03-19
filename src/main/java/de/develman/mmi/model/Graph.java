package de.develman.mmi.model;

import java.util.List;

/**
 * @author Georg Henkel
 */
public interface Graph
{
    int countVertices();

    int countEdges();

    void addEdge(Vertex v, Vertex w);

    void addEdge(Vertex v, Vertex w, double weight);

    void removeEdge(Edge e);

    void addVertex(Vertex v);

    void removeVertex(Vertex v);

    List<Vertex> getSuccessors(Vertex v);

    List<Vertex> getPredecessors(Vertex v);

    List<Vertex> getNeighbors(Vertex v);

    void printVertexList();

    void printEdgeList();
}
