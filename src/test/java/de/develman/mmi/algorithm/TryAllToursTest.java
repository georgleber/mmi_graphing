package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class TryAllToursTest
{
    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private TryAllTours tryAllTours;

    @Before
    public void setUp()
    {
        initModel();
        tryAllTours = new TryAllTours();
    }

    @Test
    public void testTryAllTours()
    {
        Vertex v1 = graph.getVertex(1);
        List<Edge> tour = tryAllTours.findOptimalTour(graph, v1);

        double cost = tour.stream().mapToDouble(Edge::getCapacity).sum();
        Assert.assertTrue(cost == 17.0);
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

        edges = new ArrayList<>();
        Edge e1 = new Edge(v1, v2, 8.0);
        edges.add(e1);
        Edge e2 = new Edge(v1, v3, 4.0);
        edges.add(e2);
        Edge e3 = new Edge(v1, v4, 26.0);
        edges.add(e3);
        Edge e4 = new Edge(v2, v3, 5.0);
        edges.add(e4);
        Edge e5 = new Edge(v2, v4, 2.0);
        edges.add(e5);
        Edge e6 = new Edge(v3, v4, 3.0);
        edges.add(e6);
    }
}
