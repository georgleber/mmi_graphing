package de.develman.mmi.algorithm;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DepthFirstSearch implementiert die Tiefensuche in einem Graphen.
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class DepthFirstSearch
{
    /**
     * Tiefensuche vom Startknoten, die alle besuchten Knoten liefert
     *
     * @param startVertex Startknoten
     * @return Liste der Knoten, die vom Startknoten erreicht werden
     */
    public static List<Vertex> doSearch(Vertex startVertex)
    {
        List<Vertex> visitList = new ArrayList<>();
        doSearchInternal(visitList, startVertex);

        return visitList;
    }
    
    /**
     * Liefert die Anzahl an Zusammenhangskomponenten
     * 
     * @param graph Graph
     * @param startVertex Startknoten
     * @return Liste der Knoten, die vom Startknoten erreicht werden
     */
    public static int countComponents(Graph graph, Vertex startVertex)
    {
        int countComponents = 0;
        List<Vertex> vertices = new ArrayList<>(graph.getVertices());

        boolean allFound = false;
        do
        {
            countComponents++;
            
            startVertex = findComponent(vertices, startVertex);
            if (startVertex == null)
            {
                allFound = true;
            }
        }
        while (!allFound);

        return countComponents;
    }

    private static void doSearchInternal(List<Vertex> visitList, Vertex startVertex)
    {
        visitList.add(startVertex);
        startVertex.setVisited(true);

        List<Vertex> neighbors = startVertex.getSuccessors();
        neighbors.forEach(vertex ->
        {
            if (!vertex.isVisited())
            {
                doSearchInternal(visitList, vertex);
            }
        });
    }

    private static Vertex findComponent(List<Vertex> vertices, Vertex startVertex)
    {
        Vertex nextStartVertex = null;

        List<Vertex> foundVertices = new ArrayList<>();
        doSearchInternal(foundVertices, startVertex);

        vertices.removeAll(foundVertices);
        if (!vertices.isEmpty())
        {
            nextStartVertex = vertices.get(0);
        }

        return nextStartVertex;
    }
}
