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
        List<Vertex> orderVertices = depthSearch.getAccessibleVertices(startVertex);

        Vertex lastVertex = null;
        List<Edge> tour = new ArrayList<>();
        for (int i = 0; i < orderVertices.size() - 1; i++)
        {
            int sourceKey = orderVertices.get(i).getKey();
            int sinkKey = orderVertices.get(i + 1).getKey();

            Vertex firstVertex = graph.getVertex(sourceKey);
            Edge edge = firstVertex.getEdgeTo(sinkKey);
            tour.add(edge);

            lastVertex = edge.getSink();
        }

        if (lastVertex != null)
        {
            Edge edge = lastVertex.getEdgeTo(startVertex.getKey());
            tour.add(edge);
        }

        return tour;
    }
}
