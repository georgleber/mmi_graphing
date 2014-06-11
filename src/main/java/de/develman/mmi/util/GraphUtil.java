package de.develman.mmi.util;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Helfer-Klasse zur Erstellung einer Super-Quelle bzw. -Senke
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class GraphUtil
{
    public static Vertex addSuperSource(Graph graph)
    {
        Vertex superSource = new Vertex(-1);
        graph.addVertex(superSource);

        List<Vertex> sources = balancedVertices(graph, true);
        sources.forEach(v ->
        {
            Edge edge = new Edge(superSource, v, v.getBalance(), 0.0);
            graph.addEdge(edge, 0);
        });

        return superSource;
    }

    public static Vertex addSuperSink(Graph graph)
    {
        Vertex superSink = new Vertex(-2);
        graph.addVertex(superSink);
        List<Vertex> sinks = balancedVertices(graph, false);
        sinks.forEach(v ->
        {
            double balance = v.getBalance() * -1;
            Edge edge = new Edge(v, superSink, balance, 0.0);
            graph.addEdge(edge);
        });

        return superSink;
    }

    private static List<Vertex> balancedVertices(Graph graph, boolean greaterZero)
    {
        return graph.getVertices().stream().filter(balanceFilter(greaterZero)).collect(
                Collectors.toList());
    }

    private static Predicate<Vertex> balanceFilter(boolean greaterZero)
    {
        Predicate<Vertex> pred;
        if (greaterZero)
        {
            pred = v -> v.getBalance() > 0;
        }
        else
        {
            pred = v -> v.getBalance() < 0;
        }

        return pred;
    }
}
