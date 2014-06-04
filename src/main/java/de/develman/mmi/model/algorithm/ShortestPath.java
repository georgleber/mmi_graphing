package de.develman.mmi.model.algorithm;

import de.develman.mmi.model.Edge;
import java.util.List;

/**
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class ShortestPath
{
    private double length;
    private List<Edge> edges;

    public double getLength()
    {
        return length;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public List<Edge> getEdges()
    {
        return edges;
    }

    public void setEdges(List<Edge> edges)
    {
        this.edges = edges;
    }
}
