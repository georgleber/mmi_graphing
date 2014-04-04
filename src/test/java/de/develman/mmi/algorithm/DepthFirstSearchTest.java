package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class DepthFirstSearchTest
{
    private List<Vertex> vertices;
    private List<Edge> edges;

    @Before
    public void init()
    {
        initModel();
    }

    @Test
    public void testUndirectedAccessibleVertices()
    {
        Graph graph = initGraph(false);
        Vertex v1 = graph.getVertex(1);

        List<Vertex> accessibleVertices = DepthFirstSearch.getAccessibleVertices(v1);
        int[] keys = new int[accessibleVertices.size()];
        for (int i = 0; i < accessibleVertices.size(); i++)
        {
            keys[i] = accessibleVertices.get(i).getKey();
        }

        int[] expected =
        {
            1, 2, 3, 5, 6, 4, 7
        };

        Assert.assertTrue(Arrays.equals(expected, keys));
    }

    @Test
    public void testDirectedAccessibleVertices()
    {
        Graph graph = initGraph(true);
        Vertex v1 = graph.getVertex(1);

        List<Vertex> accessibleVertices = DepthFirstSearch.getAccessibleVertices(v1);
        int[] keys = new int[accessibleVertices.size()];
        for (int i = 0; i < accessibleVertices.size(); i++)
        {
            keys[i] = accessibleVertices.get(i).getKey();
        }

        int[] expected =
        {
            1, 2, 3, 5, 6, 4, 7
        };

        Assert.assertTrue(Arrays.equals(expected, keys));
    }

    @Test
    public void testUndirectedVerticesOnPath()
    {
        Graph graph = initGraph(false);
        Vertex v1 = graph.getVertex(1);
        Vertex v5 = graph.getVertex(5);

        List<Vertex> accessibleVertices = DepthFirstSearch.getVerticesOnPath(v1, v5);
        int[] keys = new int[accessibleVertices.size()];
        for (int i = 0; i < accessibleVertices.size(); i++)
        {
            keys[i] = accessibleVertices.get(i).getKey();
        }

        int[] expected =
        {
            1, 2, 3, 5
        };

        Assert.assertTrue(Arrays.equals(expected, keys));
    }

    @Test
    public void testDirectedVerticesOnPath()
    {
        Graph graph = initGraph(true);
        Vertex v1 = graph.getVertex(1);
        Vertex v5 = graph.getVertex(5);

        List<Vertex> accessibleVertices = DepthFirstSearch.getVerticesOnPath(v1, v5);
        int[] keys = new int[accessibleVertices.size()];
        for (int i = 0; i < accessibleVertices.size(); i++)
        {
            keys[i] = accessibleVertices.get(i).getKey();
        }

        int[] expected =
        {
            1, 2, 3, 5
        };

        Assert.assertTrue(Arrays.equals(expected, keys));
    }

    @Test
    public void testDirectedNotAllVerticesOnPath()
    {
        Graph graph = initGraph(true);
        Vertex v2 = graph.getVertex(2);
        Vertex v7 = graph.getVertex(7);

        List<Vertex> accessibleVertices = DepthFirstSearch.getVerticesOnPath(v2, v7);
        int[] keys = new int[accessibleVertices.size()];
        for (int i = 0; i < accessibleVertices.size(); i++)
        {
            keys[i] = accessibleVertices.get(i).getKey();
        }

        int[] expected =
        {
            2, 3, 5, 6
        };

        Assert.assertTrue(Arrays.equals(expected, keys));
    }

    @Test
    public void testUndirectedHasPath()
    {
        Graph graph = initGraph(false);
        Vertex v1 = graph.getVertex(1);
        Vertex v5 = graph.getVertex(5);

        boolean hasPath = DepthFirstSearch.hasPath(v1, v5);
        Assert.assertTrue(hasPath);
    }

    @Test
    public void testDirectedHasPath()
    {
        Graph graph = initGraph(true);
        Vertex v1 = graph.getVertex(1);
        Vertex v5 = graph.getVertex(5);

        boolean hasPath = DepthFirstSearch.hasPath(v1, v5);
        Assert.assertTrue(hasPath);
    }

    @Test
    public void testDirectedHasNoPath()
    {
        Graph graph = initGraph(true);
        Vertex v2 = graph.getVertex(2);
        Vertex v7 = graph.getVertex(7);

        boolean hasPath = DepthFirstSearch.hasPath(v2, v7);
        Assert.assertFalse(hasPath);
    }

    private Graph initGraph(boolean directed)
    {
        Graph graph = new Graph(directed);

        vertices.forEach(v -> graph.addVertex(v));
        edges.forEach(e -> graph.addEdge(e));

        return graph;
    }

    private void initModel()
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
        Edge edge1 = new Edge(v1, v2);
        edges.add(edge1);
        Edge edge2 = new Edge(v1, v3);
        edges.add(edge2);
        Edge edge3 = new Edge(v1, v4);
        edges.add(edge3);
        Edge edge4 = new Edge(v2, v3);
        edges.add(edge4);
        Edge edge5 = new Edge(v3, v5);
        edges.add(edge5);
        Edge edge6 = new Edge(v3, v6);
        edges.add(edge6);
        Edge edge7 = new Edge(v4, v6);
        edges.add(edge7);
        Edge edge8 = new Edge(v4, v7);
        edges.add(edge8);
    }
}
