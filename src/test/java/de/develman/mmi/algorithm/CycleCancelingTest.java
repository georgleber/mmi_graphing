/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.develman.mmi.algorithm;

import de.develman.mmi.exception.MinimalCostFlowException;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author develman
 */
public class CycleCancelingTest
{
    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private CycleCanceling cycleCanceling;
    private EdmondsKarp edmondsKarp;

    @Before
    public void setUp()
    {
        initModel();
        cycleCanceling = new CycleCanceling();
        cycleCanceling.edmondsKarp = new EdmondsKarp();
        cycleCanceling.edmondsKarp.breadthSearch = new BreadthFirstSearch();
        cycleCanceling.mooreBellmanFord = new MooreBellmanFord();
    }

    @Test
    public void testFindMinimumCostFlow()
    {
        try
        {
            double cost = cycleCanceling.findMinimumCostFlow(graph);
            Assert.assertEquals(28.0, cost, 0.0);
        }
        catch (MinimalCostFlowException ex)
        {
            Assert.fail(ex.getMessage());
        }
    }

    private void initModel()
    {
        initData();

        graph = new Graph(true);
        vertices.forEach(v -> graph.addVertex(v));
        edges.forEach(e -> graph.addEdge(e));
    }

    private void initData()
    {
        vertices = new ArrayList<>();

        Vertex v0 = new Vertex(0, 7.0);
        vertices.add(v0);
        Vertex v1 = new Vertex(1, 0.0);
        vertices.add(v1);
        Vertex v2 = new Vertex(2, -7.0);
        vertices.add(v2);
        Vertex v3 = new Vertex(3, 0.0);
        vertices.add(v3);

        edges = new ArrayList<>();
        Edge e1 = new Edge(v0, v1, 5.0, 2.0);
        edges.add(e1);
        Edge e2 = new Edge(v0, v3, 5.0, 5.0);
        edges.add(e2);
        Edge e3 = new Edge(v1, v2, 5.0, 3.0);
        edges.add(e3);
        Edge e4 = new Edge(v3, v1, 3.0, -4.0);
        edges.add(e4);
        Edge e5 = new Edge(v3, v2, 2.0, -2.0);
        edges.add(e5);
    }
}
