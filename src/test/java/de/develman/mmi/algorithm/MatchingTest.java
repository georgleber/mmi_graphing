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
        Assert.assertTrue(matchings == 3.0);
    }

    private void initModel()
    {
        initData();

        graph = new Graph(true);
        graph.setGroupedVerticeCount(3);
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
        Vertex v2 = new Vertex(2, 1.0);
        vertices.add(v2);
        Vertex v3 = new Vertex(3, -1.0);
        vertices.add(v3);
        Vertex v4 = new Vertex(4, -1.0);
        vertices.add(v4);
        Vertex v5 = new Vertex(5, -1.0);
        vertices.add(v5);

        edges = new ArrayList<>();
        Edge e1 = new Edge(v0, v3, 1.0);
        edges.add(e1);
        Edge e2 = new Edge(v1, v5, 1.0);
        edges.add(e2);
        Edge e3 = new Edge(v2, v4, 10.0);
        edges.add(e3);
    }
}
