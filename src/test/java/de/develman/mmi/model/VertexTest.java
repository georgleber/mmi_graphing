package de.develman.mmi.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class VertexTest
{
    private Vertex vertex1;
    private Edge inEdge1;
    private Edge outEdge1;

    private List<Vertex> vertexList;

    @Before
    public void init()
    {
        initModel();
    }

    @Test
    public void correctOutgoingEdgeCount()
    {
        int size = vertex1.getOutgoingEdges().size();
        Assert.assertEquals(2, size);

        vertex1.removeOutgoingEdge(outEdge1);
        size = vertex1.getOutgoingEdges().size();
        Assert.assertEquals(1, size);
    }

    @Test
    public void correctIncomingEdgeCount()
    {
        int size = vertex1.getIncomingEdges().size();
        Assert.assertEquals(2, size);

        vertex1.removeIncomingEdge(inEdge1);
        size = vertex1.getIncomingEdges().size();
        Assert.assertEquals(1, size);
    }

    @Test
    public void testCorrectSuccessors()
    {
        List<Vertex> successors = vertex1.getSuccessors();
        Assert.assertEquals(2, successors.size());

        Assert.assertEquals(vertexList.get(0), successors.get(0));
        Assert.assertEquals(vertexList.get(2), successors.get(1));
    }

    @Test
    public void testCorrectPredecessors()
    {
        List<Vertex> predecessors = vertex1.getPredecessors();
        Assert.assertEquals(2, predecessors.size());

        Assert.assertEquals(vertexList.get(0), predecessors.get(0));
        Assert.assertEquals(vertexList.get(1), predecessors.get(1));
    }

    private void initModel()
    {
        vertexList = new ArrayList<>();

        vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        vertexList.add(vertex2);
        vertexList.add(vertex3);
        vertexList.add(vertex4);

        inEdge1 = new Edge(vertex2, vertex1);
        Edge inEdge2 = new Edge(vertex3, vertex1);
        vertex1.addIncomingEdge(inEdge1);
        vertex1.addIncomingEdge(inEdge2);

        outEdge1 = new Edge(vertex1, vertex2);
        Edge outEdge2 = new Edge(vertex1, vertex4);
        vertex1.addOutgoingEdge(outEdge1);
        vertex1.addOutgoingEdge(outEdge2);
    }
}
