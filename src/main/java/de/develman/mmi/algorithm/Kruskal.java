package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Kruskal
{
    public static List<Edge> search(Graph graph)
    {
        List<Edge> edges = graph.getEdges().parallelStream().sorted(Comparator.comparing(
                e -> e.getWeight())).collect(Collectors.toList());

        List<Edge> minSpanTree = new ArrayList<>();
        for (Edge edge : edges)
        {
            if (minSpanTree.size() != graph.getVertices().size() - 1)
            {
                break;
            }

            if (!detectCycle(minSpanTree, edge))
            {
                minSpanTree.add(edge);
            }
        }

        return minSpanTree;
    }

    private static boolean detectCycle(List<Edge> edgeList, Edge edge)
    {

        return true;
    }
}
