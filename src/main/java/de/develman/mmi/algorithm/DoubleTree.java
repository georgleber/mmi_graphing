package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
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
        List<Edge> edges = Kruskal.getMinimalSpanningTree(graph);
        return null;
    }
}
