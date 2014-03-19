package de.develman.mmi.model.impl;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Georg Henkel
 */
public class UndirectedGraph implements Graph
{
    private Vertex[] vertices;
    private List<Edge> edges;

    public UndirectedGraph(int countVertices)
    {
        vertices = new Vertex[countVertices];
        edges = new ArrayList<>();
    }

    @Override
    public int countVertices()
    {
        return vertices.length;
    }

    @Override
    public int countEdges()
    {
        return edges.size();
    }

    @Override
    public void addEdge(Vertex v, Vertex w)
    {
        Edge edge = new DefaultEdge(v, w);
        edges.add(edge);
    }

    @Override
    public void addEdge(Vertex v, Vertex w, double weight)
    {
        Edge edge = new DefaultEdge(v, w, weight);
        edges.add(edge);
    }

    @Override
    public void removeEdge(Edge e)
    {
        edges.remove(e);
    }

    @Override
    public void addVertex(Vertex v)
    {
        if (vertices[v.key()] == null)
        {
            vertices[v.key()] = v;
        }
    }

    @Override
    public void removeVertex(Vertex v)
    {
        vertices[v.key()] = null;
    }

    @Override
    public void printVertexList()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Knoten: {");

        boolean minor = false;
        for (Vertex vertex : vertices)
        {
            if (minor)
            {
                builder.append(",");
            }

            builder.append(vertex);
            minor = true;
        }

        builder.append("}");
        System.out.println(builder);
    }

    @Override
    public void printEdgeList()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Kanten: {\n");

        boolean minor = false;
        for (Edge edge : edges)
        {
            if (minor)
            {
                builder.append(",\n");
            }

            builder.append(edge.getVertexA());
            builder.append(" -> ");
            builder.append(edge.getVertexB());
            minor = true;
        }

        builder.append("}");
        System.out.println(builder);
    }

}
