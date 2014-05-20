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
public class EdmondsKarpTest
{
    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private EdmondsKarp edmondsKarp;

    @Before
    public void setUp()
    {
        initModel();
        BreadthFirstSearch breadthSearch = new BreadthFirstSearch();
        edmondsKarp = new EdmondsKarp();
        edmondsKarp.breadthSearch = breadthSearch;
    }

    @Test
    public void testFindMaxFlow()
    {
        Vertex v1 = graph.getVertex(1);
        Vertex v4 = graph.getVertex(6);
        double maxFlow = edmondsKarp.findMaxFlow(graph, v1, v4);

        Assert.assertTrue(maxFlow == 23.0);
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

        edges = new ArrayList<>();
        Edge e1 = new Edge(v1, v2, 16.0);
        edges.add(e1);
        Edge e2 = new Edge(v1, v3, 13.0);
        edges.add(e2);
        Edge e3 = new Edge(v2, v3, 10.0);
        edges.add(e3);
        Edge e4 = new Edge(v2, v4, 12.0);
        edges.add(e4);
        Edge e5 = new Edge(v3, v2, 4.0);
        edges.add(e5);
        Edge e6 = new Edge(v3, v5, 14.0);
        edges.add(e6);
        Edge e7 = new Edge(v4, v3, 9.0);
        edges.add(e7);
        Edge e8 = new Edge(v4, v6, 20.0);
        edges.add(e8);
        Edge e9 = new Edge(v5, v4, 7.0);
        edges.add(e9);
        Edge e10 = new Edge(v5, v6, 4.0);
        edges.add(e10);
    }
}
