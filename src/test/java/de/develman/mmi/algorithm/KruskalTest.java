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
public class KruskalTest
{
    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;

    @Before
    public void setUp()
    {
        initModel();
    }

    @Test
    public void testKruskal()
    {
        Graph minSpanTree = Kruskal.getMinimalSpanningTree(graph);

        double cost = minSpanTree.getEdges().stream().mapToDouble(Edge::getWeight).sum();
        Assert.assertEquals(9.0, cost);
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

        edges = new ArrayList<>();
        Edge e1 = new Edge(v1, v2, 1.0);
        edges.add(e1);
        Edge e2 = new Edge(v1, v7, 1.0);
        edges.add(e2);
        Edge e3 = new Edge(v2, v3, 2.0);
        edges.add(e3);
        Edge e4 = new Edge(v2, v7, 1.0);
        edges.add(e4);
        Edge e5 = new Edge(v3, v4, 2.0);
        edges.add(e5);
        Edge e6 = new Edge(v3, v5, 3.0);
        edges.add(e6);
        Edge e7 = new Edge(v4, v5, 4.0);
        edges.add(e7);
        Edge e8 = new Edge(v5, v6, 2.0);
        edges.add(e8);
        Edge e9 = new Edge(v5, v7, 1.0);
        edges.add(e9);
    }
}
