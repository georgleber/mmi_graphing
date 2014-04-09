package de.develman.mmi.algorithm;

import de.develman.mmi.model.Vertex;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Die Klasse BreadthFirstSearch implementiert die Breitensuche in einem Graphen
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class BreadthFirstSearch
{
    private static final Logger LOG = LoggerFactory.getLogger(BreadthFirstSearch.class);

    /**
     * Breitensuche vom Startknoten, die alle besuchten Knoten liefert
     *
     * @param startVertex Startknoten
     * @return Liste der Knoten, die von dem Startknoten erreicht werden
     */
    public static List<Vertex> getAccessibleVertices(Vertex startVertex)
    {
        List<Vertex> vertexList = getVerticesOnPath(startVertex, null);
        return vertexList;
    }

    /**
     * Prüft, ob es einen Weg zwischen Start- und Endknoten gibt
     *
     * @param startVertex Startknoten
     * @param endVertex Endknoten
     * @return {@code true}, wenn ein Weg gefunden wurde
     */
    public static boolean hasPath(Vertex startVertex, Vertex endVertex)
    {
        boolean pathFound = false;

        List<Vertex> foundVertices = getVerticesOnPath(startVertex, endVertex);
        if (foundVertices.contains(startVertex) && foundVertices.contains(endVertex))
        {
            pathFound = true;
        }

        return pathFound;
    }

    /**
     * Breitensuche von Startknoten zu Endknoten
     *
     * @param startVertex Startknoten
     * @param endVertex Endknoten
     * @return Liste der besuchten Knoten, von Startknoten bis Endknoten
     */
    public static List<Vertex> getVerticesOnPath(Vertex startVertex, Vertex endVertex)
    {
        startVertex.setVisited(true);
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(startVertex);

        List<Vertex> visitList = new ArrayList<>();
        while (!queue.isEmpty())
        {
            Vertex nextVertex = queue.poll();
            visitList.add(nextVertex);

            if (nextVertex.equals(endVertex))
            {
                LOG.info("end vertex found: " + nextVertex);
                break;
            }

            nextVertex.getSuccessors().stream().filter(v -> !v.isVisited()).forEach(vertex ->
            {
                vertex.setVisited(true);
                queue.add(vertex);
            });
        }

        return visitList;
    }
}
