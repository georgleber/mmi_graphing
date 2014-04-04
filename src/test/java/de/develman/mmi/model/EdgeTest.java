/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.develman.mmi.model;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author develman
 */
public class EdgeTest
{
    private Edge edge;
    private Vertex v1;
    private Vertex v2;

    @Before
    public void init()
    {
        initModel();
    }

    @Test
    public void testCorrectEnds()
    {
        Assert.assertEquals(v1, edge.getSource());
        Assert.assertEquals(v2, edge.getSink());
    }

    @Test
    public void testReverseEdge()
    {
        Edge reverse = edge.revert();

        Assert.assertEquals(v2, reverse.getSource());
        Assert.assertEquals(v1, reverse.getSink());
    }

    private void initModel()
    {
        v1 = new Vertex(1);
        v2 = new Vertex(2);
        edge = new Edge(v1, v2);
    }
}
