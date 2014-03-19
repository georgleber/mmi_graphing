package de.develman.mmi.model.impl;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Georg Henkel
 */
public class UndirectedGraph implements Graph
{
    private Map<Vertex, List<Edge>> vertexEdges;

    public UndirectedGraph(int countVertices)
    {
        vertexEdges = new HashMap<>();
    }

    @Override
    public int countVertices()
    {
        return vertexEdges.keySet().size();
    }

    @Override
    public int countEdges()
    {
        int count = 0;
        for (Vertex v : vertexEdges.keySet())
        {
            count += vertexEdges.get(v).size();
        }

        return count;
    }

    @Override
    public void addEdge(Vertex v, Vertex w)
    {
        Edge edge = new DefaultEdge(v, w);
        //edges.add(edge);
    }

    @Override
    public void addEdge(Vertex v, Vertex w, double weight)
    {
        Edge edge = new DefaultEdge(v, w, weight);
        // edges.add(edge);
    }

    @Override
    public void removeEdge(Edge e)
    {
        // edges.remove(e);
    }

    @Override
    public void addVertex(Vertex v)
    {
        if (!vertexEdges.containsKey(v))
        {
            vertexEdges.put(v, null);
        }
    }

    @Override
    public void removeVertex(Vertex v)
    {
        vertexEdges.remove(v);
    }

    @Override
    public List<Vertex> getSuccessors(Vertex v)
    {
        return null;
    }

    @Override
    public List<Vertex> getPredecessors(Vertex v)
    {
        return null;
    }

    @Override
    public List<Vertex> getNeighbors(Vertex v)
    {
        return null;
    }

    @Override
    public void printVertexList()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Knoten: {");

        boolean minor = false;
        for (Vertex vertex : vertexEdges.keySet())
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
        /*
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
         */

        builder.append("}");
        System.out.println(builder);
    }

}
