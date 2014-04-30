package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse TryAllTours implementiert einen Algorithmus zur Berechnung der optimalen TSP-Tour in einem vollständigen
 * Graphen mit Kantengewichten. Dabei werden alle möglichen Touren ausgewertet und die Beste geliefert.
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class TryAllTours
{
    private int vertexCount;
    private Vertex startVertex;

    protected double tourCost;
    protected Double tspCost;
    private List<Edge> tspTour;

    /**
     * Berechnung der optimalen TSP-Tour
     *
     * @param graph Graph für den die optimale Route berechnet werden soll
     * @param startVertex Startknoten
     * @return Liste der Kanten der optimalen TSP-Tour
     */
    public List<Edge> findOptimalTour(Graph graph, Vertex startVertex)
    {
        this.vertexCount = graph.countVertices();
        this.startVertex = startVertex;
        this.tspCost = Double.POSITIVE_INFINITY;

        findOptimalTour(startVertex, new ArrayList<>());
        return tspTour;
    }

    protected boolean nodeVisited(Vertex vertex, List<Edge> currentEdges)
    {
        return vertex.isVisited();
    }

    private void findOptimalTour(Vertex vertex, List<Edge> currentEdges)
    {
        vertex.setVisited(true);
        vertex.getOutgoingEdges().forEach(e ->
        {
            Vertex sink = e.getSink();

            currentEdges.add(e);
            tourCost += e.getWeight();

            if (sink == startVertex && currentEdges.size() == vertexCount)
            {
                checkIfRouteIsBetter(currentEdges);
            }

            if (!nodeVisited(sink, currentEdges))
            {
                findOptimalTour(sink, currentEdges);
            }

            removeLastEdge(currentEdges);
        });

        vertex.setVisited(false);
    }

    private void removeLastEdge(List<Edge> edges)
    {
        Edge lastEdge = edges.remove(edges.size() - 1);
        tourCost -= lastEdge.getWeight();
    }

    private void checkIfRouteIsBetter(List<Edge> edges)
    {
        if (tourCost < tspCost)
        {
            tspCost = tourCost;
            tspTour = new ArrayList<>(edges);
        }
    }
}
