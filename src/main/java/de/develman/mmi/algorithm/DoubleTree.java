package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DoubleTree implementiert den Doppelter-Baum-Algorithmus zur Berechnung der optimalen Tour in einem
 * vollständigen Graphen mit Kantengewichten (TSP)
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class DoubleTree
{
    /**
     * Berechnung der optimalen Tour
     *
     * @param graph Vollständiger Graph mit Kantengewichten
     * @return Liste der Kanten der optimalen Tour
     */
    public static List<Edge> getHamilton(Graph graph)
    {
        Graph minSpanTree = Kruskal.getMinimalSpanningTree(graph);

        Vertex startVertex = minSpanTree.getFirstVertex();
        List<Vertex> orderVertices = DepthFirstSearch.getAccessibleVertices(startVertex);

        Vertex lastVertex = null;
        List<Edge> usedEdges = new ArrayList<>();
        for (int i = 0; i < orderVertices.size() - 1; i++)
        {
            int sourceKey = orderVertices.get(i).getKey();
            int sinkKey = orderVertices.get(i + 1).getKey();

            Vertex firstVertex = graph.getVertex(sourceKey);
            Edge edge = firstVertex.getEdgeTo(sinkKey);
            usedEdges.add(edge);

            lastVertex = edge.getSink();
        }

        if (lastVertex != null)
        {
            Edge edge = lastVertex.getEdgeTo(startVertex.getKey());
            usedEdges.add(edge);
        }

        return usedEdges;
    }
}
