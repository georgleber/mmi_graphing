package de.develman.mmi.model;

/**
 * @author Georg Henkel
 */
public interface Edge
{
    Vertex getVertexA();

    Vertex getVertexB();

    double getWeight();
}
