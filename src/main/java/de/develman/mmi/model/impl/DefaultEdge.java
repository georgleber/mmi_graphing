package de.develman.mmi.model.impl;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.WeighingMethod;

/**
 * @author Georg Henkel
 */
public class DefaultEdge implements Edge
{
    private Vertex vertexA;
    private Vertex vertexB;
    private double weight;

    public DefaultEdge(Vertex vertexA, Vertex vertexB)
    {
        this.vertexA = vertexA;
        this.vertexB = vertexB;

        this.weight = 1;
    }

    public DefaultEdge(Vertex vertexA, Vertex vertexB, double weight)
    {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.weight = weight;
    }

    @Override
    public Vertex getVertexA()
    {
        return vertexA;
    }

    public void setVertexA(Vertex v)
    {
        this.vertexA = v;
    }

    @Override
    public Vertex getVertexB()
    {
        return vertexB;
    }

    public void setVertexB(Vertex v)
    {
        this.vertexB = v;
    }

    @Override
    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public double getWeight(WeighingMethod method)
    {
        return method.calculate(weight);
    }
}
