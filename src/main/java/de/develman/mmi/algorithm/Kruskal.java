package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Kruskal
{
    /**
     *
     * @param graph
     * @return
     */
    public static Graph getMinimalSpanningTree(Graph graph)
    {
        List<Edge> edges = graph.getEdges().parallelStream().sorted(Comparator.comparing(
                e -> e.getWeight())).collect(Collectors.toList());

        Graph minSpanTree = new Graph(true);
        for (Edge edge : edges)
        {
            if (minSpanTree.getVertices().size() != graph.getVertices().size() - 1)
            {
                break;
            }

            if (!DepthFirstSearch.hasPath(edge.getSource(), edge.getSink()))
            {
                minSpanTree.addEdge(edge);
            }
        }

        return minSpanTree;
    }
}
