package de.develman.mmi.model.algorithm;

import de.develman.mmi.model.Vertex;
import java.util.List;

/**
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class ShortestPath
{
    private double length;
    private List<Vertex> vertices;

    public double getLength()
    {
        return length;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public List<Vertex> getVertices()
    {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices)
    {
        this.vertices = vertices;
    }
}
