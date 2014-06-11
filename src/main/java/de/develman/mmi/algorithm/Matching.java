package de.develman.mmi.algorithm;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.util.GraphUtil;
import javax.inject.Inject;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Matching
{
    @Inject
    EdmondsKarp edmondsKarp;

    public int countMatching(Graph graph)
    {
        Vertex superSource = GraphUtil.addSuperSource(graph);
        Vertex superSink = GraphUtil.addSuperSink(graph);
        edmondsKarp.calculateMaxFlowGraph(graph, superSource, superSink);

        return (int) graph.getEdges().stream().filter(e -> e.getCapacity() > 0.0).count();
    }
}
