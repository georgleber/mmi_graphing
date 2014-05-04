package de.develman.mmi.algorithm;

import de.develman.mmi.exception.NegativeCycleException;
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
public class MooreBellmanFordTest
{
    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private MooreBellmanFord mooreBellmanFord;

    @Before
    public void setUp()
    {
        initModel();
        mooreBellmanFord = new MooreBellmanFord();
    }

    @Test
    public void testMooreBellmanFordDirected()
    {
        graph = new Graph(true);
        vertices.forEach(v -> graph.addVertex(v));
        edges.forEach(e -> graph.addEdge(e));

        Vertex v3 = graph.getVertex(2);

        try
        {
            ShortestPath path = mooreBellmanFord.findShortestPath(graph, graph.getFirstVertex(), v3);
            Assert.assertEquals("Expected -98.0 but is: " + path.getLength(), -98.0, path.getLength(), 0.0);
        }
        catch (NegativeCycleException ex)
        {
            Assert.fail("Negative cycle detected.");
        }
    }

    // @Test
    public void testMooreBellmanFordUndirected()
    {
        graph = new Graph(false);
        vertices.forEach(v -> graph.addVertex(v));
        edges.forEach(e -> graph.addEdge(e));

        Vertex v6 = graph.getVertex(5);

        try
        {
            ShortestPath path = mooreBellmanFord.findShortestPath(graph, graph.getFirstVertex(), v6);
            Assert.assertEquals("Expected 5.0 but is: " + path.getLength(), 5.0, path.getLength(), 0.0);
        }
        catch (NegativeCycleException ex)
        {
            Assert.fail("Negative cycle detected.");
        }
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

        edges = new ArrayList<>();
        Edge e1 = new Edge(v3, v2, 1.0);
        edges.add(e1);
        Edge e2 = new Edge(v4, v3, 1.0);
        edges.add(e2);
        Edge e3 = new Edge(v2, v4, 1.0);
        edges.add(e3);
        Edge e4 = new Edge(v5, v4, -100.0);
        edges.add(e4);
        Edge e5 = new Edge(v5, v2, 2.0);
        edges.add(e5);
        Edge e6 = new Edge(v1, v5, 1.0);
        edges.add(e6);
        Edge e7 = new Edge(v1, v2, 4.0);
        edges.add(e7);
    }
}
