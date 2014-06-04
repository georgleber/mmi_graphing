package de.develman.mmi.algorithm;

import de.develman.mmi.exception.NegativeCycleException;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.algorithm.ShortestPath;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse MooreBellmanFord implementiert den Bellman–Ford–Moore-Algorithmus zur Berechnung des Kürzester-Wege-Baumes
 * in einem Digraph
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class MooreBellmanFord extends AbstractShortestPath
{
    /**
     * Sucht nach einem negativen Zykel im Graphen
     *
     * @param graph Graph
     * @param startVertex Startknoten
     * @return Negativer Zykel oder null, wenn keiner gefunden wurde
     */
    public List<Edge> findNegativeCycle(Graph graph, Vertex startVertex)
    {
        init(graph, startVertex);
        calculateDistances(graph);

        distance.keySet().forEach(v ->
        {
            if (distance.get(v) < Double.POSITIVE_INFINITY)
            {
                v.setVisited(true);
            }
        });

        for (Edge e : graph.getEdges())
        {
            if (checkForCycle(e))
            {
                List<Edge> cycle = loadCycle(e, graph.countVertices());
                return cycle;
            }
        }

        return null;
    }

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
        calculateDistances(graph);

        for (Edge e : graph.getEdges())
        {
            if (checkForCycle(e))
            {
                throw new NegativeCycleException();
            }
        }

        return loadShortestPath(startVertex, endVertex);
    }

    private void calculateDistances(Graph graph)
    {
        int count = 0;
        while (count < graph.countVertices() - 1)
        {
            graph.getEdges().forEach(e -> updateCost(e));
            count++;
        }
    }

    private boolean checkForCycle(Edge e)
    {
        boolean hasCycle = false;

        Vertex source = e.getSource();
        Vertex sink = e.getSink();

        double newDistance = distance.get(source) + e.getCost();
        if (newDistance < distance.get(sink))
        {
            hasCycle = true;
        }

        return hasCycle;
    }

    private List<Edge> loadCycle(Edge e, int countVertices)
    {
        Vertex start = e.getSource();
        for (int i = 0; i < countVertices; i++)
        {
            start = predecessor.get(start);
        }

        return extractCycle(start);
    }

    private List<Edge> extractCycle(Vertex start)
    {
        List<Edge> cycle = new ArrayList<>();

        Vertex pred;
        Vertex current = start;
        do
        {
            pred = predecessor.get(current);
            cycle.add(pred.getEdgeTo(current.getKey()));

            if (pred == start)
            {
                break;
            }

            current = pred;
        }
        while (true);

        return cycle;
    }
}
