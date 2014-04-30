package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Vertex;
import java.util.List;

/**
 * Die Klasse BranchAndBound implementiert einen Branch-&-Bound Algorithmus zur Berechnung der optimalen TSP-Tour in
 * einem vollständigen Graphen mit Kantengewichten.
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class BranchAndBound extends TryAllTours
{
    @Override
    protected boolean nodeVisited(Vertex vertex, List<Edge> currentEdges)
    {
        return tourCost > tspCost || vertex.isVisited();
    }
}
