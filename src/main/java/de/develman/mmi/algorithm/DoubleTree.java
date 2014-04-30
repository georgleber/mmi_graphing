package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Die Klasse DoubleTree implementiert den Doppelter-Baum-Algorithmus zur Berechnung der TSP-Tour in einem vollständigen
 * Graphen mit Kantengewichten (TSP)
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class DoubleTree
{
    @Inject
    DepthFirstSearch depthSearch;
    @Inject
    Kruskal kruskal;

    /**
     * Berechnung der TSP-Tour
     *
     * @param graph Vollständiger Graph mit Kantengewichten
     * @return Liste der Kanten der TSP-Tour
     */
    public List<Edge> findTour(Graph graph)
    {
        Graph minSpanTree = kruskal.getMinimalSpanningTree(graph);

        Vertex startVertex = minSpanTree.getFirstVertex();
        List<Vertex> orderedVertices = depthSearch.getAccessibleVertices(startVertex);

        List<Edge> tour = new ArrayList<>();
        for (int i = 0; i < orderedVertices.size() - 1; i++)
        {
            int sourceKey = orderedVertices.get(i).getKey();
            int sinkKey = orderedVertices.get(i + 1).getKey();

            Vertex sourceVertex = graph.getVertex(sourceKey);
            addEdgeToTour(tour, sourceVertex, sinkKey);
        }

        Vertex lastVertex = orderedVertices.get(orderedVertices.size() - 1);
        addEdgeToTour(tour, lastVertex, startVertex.getKey());

        return tour;
    }

    private void addEdgeToTour(List<Edge> tour, Vertex sourceVertex, int sinkKey)
    {
        Edge edge = sourceVertex.getEdgeTo(sinkKey);
        tour.add(edge);
    }
}
