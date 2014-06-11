package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class MatchingTest
{
    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private Matching matching;

    @Before
    public void setUp()
    {
        initModel();

        matching = new Matching();
        matching.edmondsKarp = new EdmondsKarp();
        matching.edmondsKarp.breadthSearch = new BreadthFirstSearch();
    }

    @Test
    public void testFindMaxFlow()
    {
        int matchings = matching.countMatching(graph);
        // Assert.assertTrue(matchings == 1.0);
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

        Vertex v0 = new Vertex(0, 1.0);
        vertices.add(v0);
        Vertex v1 = new Vertex(1, 1.0);
        vertices.add(v1);
        Vertex v2 = new Vertex(2, -1.0);
        vertices.add(v2);
        Vertex v3 = new Vertex(3, 1.0);
        vertices.add(v3);
        Vertex v4 = new Vertex(4, -1.0);
        vertices.add(v4);
        Vertex v5 = new Vertex(5, -1.0);
        vertices.add(v5);
        Vertex v6 = new Vertex(6, 1.0);
        vertices.add(v6);

        edges = new ArrayList<>();
        Edge e1 = new Edge(v0, v2, 4.0);
        edges.add(e1);
        Edge e2 = new Edge(v1, v2, 3.0);
        edges.add(e2);
        Edge e3 = new Edge(v3, v5, 10.0);
        edges.add(e3);
        Edge e4 = new Edge(v6, v4, 12.0);
        edges.add(e4);
    }
}
