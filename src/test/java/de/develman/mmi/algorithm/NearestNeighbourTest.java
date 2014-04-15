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
public class NearestNeighbourTest
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
    public void testNearestNeighbour()
    {
        List<Edge> hamilton = NearestNeighbour.getHamilton(graph, graph.getVertex(1));

        double length = hamilton.stream().mapToDouble(Edge::getWeight).sum();
        Assert.assertTrue(length == 14.0);
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

        edges = new ArrayList<>();
        Edge e1 = new Edge(v1, v2, 7.0);
        edges.add(e1);
        Edge e2 = new Edge(v1, v3, 3.0);
        edges.add(e2);
        Edge e3 = new Edge(v1, v4, 2.0);
        edges.add(e3);
        Edge e4 = new Edge(v1, v5, 2.0);
        edges.add(e4);
        Edge e5 = new Edge(v2, v3, 1.0);
        edges.add(e5);
        Edge e6 = new Edge(v2, v4, 8.0);
        edges.add(e6);
        Edge e7 = new Edge(v2, v5, 5.0);
        edges.add(e7);
        Edge e8 = new Edge(v3, v4, 4.0);
        edges.add(e8);
        Edge e9 = new Edge(v3, v5, 3.0);
        edges.add(e9);
        Edge e10 = new Edge(v4, v5, 6.0);
        edges.add(e10);
    }

}
