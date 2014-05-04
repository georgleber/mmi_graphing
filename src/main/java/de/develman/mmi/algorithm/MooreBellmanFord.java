package de.develman.mmi.algorithm;

import de.develman.mmi.exception.NegativeCycleException;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.algorithm.ShortestPath;

/**
 * Die Klasse MooreBellmanFord implementiert den Bellman–Ford–Moore-Algorithmus zur Berechnung des Kürzester-Wege-Baumes
 * in einem Digraph
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class MooreBellmanFord extends AbstractShortestPath
{
    /**
     * Berechnung des Kürzester-Wege-Baumes
     *
     * @param graph Digraph mit positiven Kantengewichten
     * @param startVertex Startknoten
     * @param endVertex Zielknoten
     * @throws NegativeCycleException
     * @return Liste der Kanten des Kürzester-Wege-Baum
     */
    public ShortestPath findShortestPath(Graph graph, Vertex startVertex, Vertex endVertex) throws NegativeCycleException
    {
        init(graph, startVertex);

        int count = 0;
        while (count < graph.countVertices() - 1)
        {
            graph.getEdges().forEach(e -> updateWeigth(e));
            count++;
        }

        for (Edge e : graph.getEdges())
        {
            checkForCycle(e);
        }

        return loadShortestPath(startVertex, endVertex);
    }

    private void checkForCycle(Edge e) throws NegativeCycleException
    {
        Vertex source = e.getSource();
        Vertex sink = e.getSink();

        double newDistance = distance.get(source) + e.getWeight();
        if (newDistance < distance.get(sink))
        {
            throw new NegativeCycleException();
        }
    }
}
