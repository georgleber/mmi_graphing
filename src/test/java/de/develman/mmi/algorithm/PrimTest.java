package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class PrimTest
{
    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private Prim prim;

    @Before
    public void setUp()
    {
        initModel();
        prim = new Prim();
    }

    @Test
    public void testPrim()
    {
        Graph minSpanTree = prim.getMinimalSpanningTree(graph, graph.getVertex(1));

        double cost = minSpanTree.getEdges().stream().mapToDouble(Edge::getWeight).sum();
        Assert.assertEquals(15.0, cost);
    }

    private void initModel()
    {
        initData();

        graph = new Graph(false);
        vertices.forEach(v -> graph.addVertex(v));
        edges.forEach(e -> graph.addEdge(e));
    }

    private void initData()
    {
        vertices = new ArrayList<>();

        Vertex v1 = new Vertex(1);
        vertices.add(v1);
        Vertex v2 = new Vertex(2);
        vertices.add(v2);
        Vertex v3 = new Vertex(3);
        vertices.add(v3);
        Vertex v4 = new Vertex(4);
        vertices.add(v4);
        Vertex v5 = new Vertex(5);
        vertices.add(v5);
        Vertex v6 = new Vertex(6);
        vertices.add(v6);
        Vertex v7 = new Vertex(7);
        vertices.add(v7);
        Vertex v8 = new Vertex(8);
        vertices.add(v8);
        Vertex v9 = new Vertex(9);
        vertices.add(v9);
        Vertex v10 = new Vertex(10);
        vertices.add(v10);
        Vertex v11 = new Vertex(11);
        vertices.add(v11);

        edges = new ArrayList<>();
        Edge e1 = new Edge(v1, v2, 3.0);
        edges.add(e1);
        Edge e2 = new Edge(v1, v4, 2.0);
        edges.add(e2);
        Edge e3 = new Edge(v1, v5, 1.0);
        edges.add(e3);
        Edge e4 = new Edge(v2, v4, 2.0);
        edges.add(e4);
        Edge e5 = new Edge(v2, v6, 1.0);
        edges.add(e5);
        Edge e6 = new Edge(v2, v3, 3.0);
        edges.add(e6);
        Edge e7 = new Edge(v3, v6, 5.0);
        edges.add(e7);
        Edge e8 = new Edge(v3, v7, 2.0);
        edges.add(e8);
        Edge e9 = new Edge(v3, v8, 1.0);
        edges.add(e9);
        Edge e10 = new Edge(v4, v5, 1.0);
        edges.add(e10);
        Edge e11 = new Edge(v4, v6, 3.0);
        edges.add(e11);
        Edge e12 = new Edge(v5, v6, 2.0);
        edges.add(e12);
        Edge e13 = new Edge(v5, v10, 10.0);
        edges.add(e13);
        Edge e14 = new Edge(v6, v7, 2.0);
        edges.add(e14);
        Edge e15 = new Edge(v6, v10, 4.0);
        edges.add(e15);
        Edge e16 = new Edge(v7, v11, 3.0);
        edges.add(e16);
        Edge e17 = new Edge(v8, v11, 2.0);
        edges.add(e17);
        Edge e18 = new Edge(v10, v11, 2.0);
        edges.add(e18);
        Edge e19 = new Edge(v9, v10, 1.0);
        edges.add(e19);
    }
}
