package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Die Klasse NearestNeighbour implementiert den Nearest-Neighbour Algorithmus zur Berechnung der TSP-Tour in einem
 * vollständigen Graphen mit Kantengewichten
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class NearestNeighbour
{
    /**
     * Berechnung einer TSP-Tour
     *
     * @param graph Vollständiger Graph mit Kantengewichten
     * @param startVertex Startknoten
     * @return Liste der Kanten der TSP-Tour
     */
    public List<Edge> findTour(Graph graph, Vertex startVertex)
    {
        List<Edge> tour = new ArrayList<>();

        Vertex vertex = startVertex;
        do
        {
            vertex.setVisited(true);

            Edge bestEdge = getBestEdge(vertex);
            if (bestEdge == null)
            {
                tour.add(vertex.getEdgeTo(startVertex.getKey()));
                break;
            }

            vertex = bestEdge.getSink();
            tour.add(bestEdge);
        }
        while (!allVerticesVisited(graph));

        return tour;
    }

    private boolean allVerticesVisited(Graph graph)
    {
        return graph.getVertices().stream().filter(v -> !v.isVisited()).count() == 0;
    }

    private Edge getBestEdge(Vertex vertex)
    {
        Optional<Edge> foundEdge = vertex.getOutgoingEdges().stream().filter(e -> !e.getSink().isVisited()).sorted(
                Comparator.comparing(Edge::getCapacity)).findFirst();

        Edge bestEdge = null;
        if (foundEdge.isPresent())
        {
            bestEdge = foundEdge.get();
        }

        return bestEdge;
    }
}
