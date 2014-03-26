package de.develman.mmi.algorithm;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.service.LoggingService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Die Klasse DepthFirstSearch implementiert die Tiefensuche in einem Graphen.
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class DepthFirstSearch
{
    @Inject
    LoggingService loggingService;

    /**
     * Tiefensuche vom Startknoten, die alle besuchten Knoten liefert
     *
     * @return Liste der Knoten, die vom Startknoten erreicht werden
     */
    public List<Vertex> doSearch(Vertex startVertex)
    {
        List<Vertex> visitList = new ArrayList<>();
        doSearchInternal(visitList, startVertex, true);

        return visitList;
    }

    /**
     * Tiefensuche vom Startknoten, die alle besuchten Knoten liefert
     *
     * @param startVertex Startknoten
     * @return Liste der Knoten, die vom Startknoten erreicht werden
     */
    public List<Vertex> doSearch(Vertex startVertex, boolean maximal)
    {
        List<Vertex> visitList = new ArrayList<>();
        doSearchInternal(visitList, startVertex, maximal);

        return visitList;
    }

    private void doSearchInternal(List<Vertex> visitList, Vertex startVertex, boolean maximal)
    {
        visitList.add(startVertex);
        startVertex.setVisited(true);

        List<Vertex> neighbors = startVertex.getSuccessors();
        if (!maximal)
        {
            neighbors.addAll(startVertex.getPredecessors());
        }

        neighbors.forEach(vertex ->
        {
            if (!vertex.isVisited())
            {
                doSearchInternal(visitList, vertex, maximal);
            }
        });
    }

    public List<List<Vertex>> loadComponents(Graph graph, Vertex startVertex)
    {
        List<List<Vertex>> components = new ArrayList<>();
        Collection<Vertex> vertices = graph.getVertices();

        boolean allFound = false;

        do
        {
            List<Vertex> foundVertices = new ArrayList<>();
            doSearchInternal(foundVertices, startVertex, false);
            components.add(foundVertices);

            List<Vertex> restVertices = findUnvisitedVertices(vertices, components);
            if (restVertices.isEmpty())
            {
                allFound = true;
            }
            else
            {
                startVertex = restVertices.get(0);
            }
        }
        while (!allFound);

        return components;
    }

    private List<Vertex> findUnvisitedVertices(Collection<Vertex> vertices, List<List<Vertex>> components)
    {
        List<Vertex> foundVertices = new ArrayList<>();
        components.forEach(list ->
        {
            list.forEach(vertex ->
            {
                if (!foundVertices.contains(vertex))
                {
                    foundVertices.add(vertex);
                }
            });
        });

        List<Vertex> restVertices = new ArrayList<>(vertices);
        restVertices.removeAll(foundVertices);

        return restVertices;
    }
}
