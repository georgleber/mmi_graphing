package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.algorithm.ShortestPath;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class DijkstraTest
{
    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private Dijkstra dijkstra;

    @Before
    public void setUp()
    {
        initModel();
        dijkstra = new Dijkstra();
    }

    @Test
    public void testDijkstraDirected()
    {
        graph = new Graph(true);
        vertices.forEach(v -> graph.addVertex(v));
        edges.forEach(e -> graph.addEdge(e));

        Vertex v6 = graph.getVertex(5);
        ShortestPath path = dijkstra.findShortestPath(graph, graph.getFirstVertex(), v6);
        Assert.assertEquals("Expected 8.0 but is: " + path.getLength(), 8.0, path.getLength(), 0.0);
    }

    @Test
    public void testDijkstraUndirected()
    {
        graph = new Graph(false);
        vertices.forEach(v -> graph.addVertex(v));
        edges.forEach(e -> graph.addEdge(e));

        Vertex v6 = graph.getVertex(5);
        ShortestPath path = dijkstra.findShortestPath(graph, graph.getFirstVertex(), v6);
        Assert.assertEquals("Expected 5.0 but is: " + path.getLength(), 5.0, path.getLength(), 0.0);
    }

    private void initModel()
    {
        vertices = new ArrayList<>();

        Vertex v1 = new Vertex(0);
        vertices.add(v1);
        Vertex v2 = new Vertex(1);
        vertices.add(v2);
        Vertex v3 = new Vertex(2);
        vertices.add(v3);
        Vertex v4 = new Vertex(3);
        vertices.add(v4);
        Vertex v5 = new Vertex(4);
        vertices.add(v5);
        Vertex v6 = new Vertex(5);
        vertices.add(v6);

        edges = new ArrayList<>();
        Edge e1 = new Edge(v1, v2, 1.0);
        edges.add(e1);
        Edge e2 = new Edge(v1, v5, 7.0);
        edges.add(e2);
        Edge e3 = new Edge(v2, v3, 3.0);
        edges.add(e3);
        Edge e4 = new Edge(v3, v4, 8.0);
        edges.add(e4);
        Edge e5 = new Edge(v3, v5, 3.0);
        edges.add(e5);
        Edge e6 = new Edge(v3, v6, 6.0);
        edges.add(e6);
        Edge e7 = new Edge(v5, v6, 1.0);
        edges.add(e7);
        Edge e8 = new Edge(v6, v4, 3.0);
        edges.add(e8);
        Edge e9 = new Edge(v6, v3, 1.0);
        edges.add(e9);
    }
}
