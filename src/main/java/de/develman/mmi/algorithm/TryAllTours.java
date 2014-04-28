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
    private Graph graph;
    private Vertex startVertex;

    protected Double tspCost;
    private final List<Edge> tspTour = new ArrayList<>();

    /**
     * Berechnung der optimalen TSP-Tour
     *
     * @param graph Graph für den die optimale Route berechnet werden soll
     * @param startVertex Startknoten
     * @return Liste der Kanten der optimalen TSP-Tour
     */
    public List<Edge> findOptimalTour(Graph graph, Vertex startVertex)
    {
        this.graph = graph;
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
        boolean allEdgesFound = currentEdges.size() == graph.getVertices().size();
        if (vertex == startVertex && allEdgesFound)
        {
            checkIfRouteIsBetter(currentEdges);
        }

        if (nodeVisited(vertex, currentEdges))
        {
            currentEdges.remove(currentEdges.size() - 1);
            return;
        }

        vertex.setVisited(true);
        vertex.getOutgoingEdges().forEach(e ->
        {
            currentEdges.add(e);
            findOptimalTour(e.getSink(), currentEdges);
        });

        vertex.setVisited(false);
        if (currentEdges.size() > 0)
        {
            currentEdges.remove(currentEdges.size() - 1);
        }
    }

    private void checkIfRouteIsBetter(List<Edge> edges)
    {
        double cost = edges.stream().mapToDouble(Edge::getWeight).sum();
        if (cost < tspCost)
        {
            tspCost = cost;

            tspTour.clear();
            tspTour.addAll(edges);
        }
    }
}
