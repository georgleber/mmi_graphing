package de.develman.mmi.model;

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

    void printVertexList();

    void printEdgeList();
}
