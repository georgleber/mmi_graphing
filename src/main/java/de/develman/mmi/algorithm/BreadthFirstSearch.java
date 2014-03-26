package de.develman.mmi.algorithm;

import de.develman.mmi.model.Vertex;
import de.develman.mmi.service.LoggingService;
import java.util.*;
import javax.inject.Inject;
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

    @Inject
    LoggingService loggingService;

    /**
     * Breitensuche vom Startknoten, die alle besuchten Knoten liefert
     *
     * @param startVertex Startknoten
     * @return Liste der Knoten, die von dem Startknoten erreicht werden
     */
    public List<Vertex> doSearch(Vertex startVertex)
    {
        List<Vertex> vertexList = doSearch(startVertex, null);
        return vertexList;
    }

    /**
     * Breitensuche von Startknoten zu Endknoten
     *
     * @param startVertex Startknoten
     * @param endVertex Endknoten
     * @return {@code true}, wenn der Endknoten gefunden wurde, anonsten {@code false}
     */
    public List<Vertex> doSearch(Vertex startVertex, Vertex endVertex)
    {
        String message = "Running BFS with start vertex: " + startVertex + " and end vertex: " + endVertex;
        LOG.debug(message);
        loggingService.log(message);

        List<Vertex> visitList = new ArrayList<>();

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(startVertex);

        while (!queue.isEmpty())
        {
            Vertex nextVertex = queue.poll();
            if (nextVertex == null || visitList.contains(nextVertex))
            {
                continue;
            }

            visitList.add(nextVertex);
            if (nextVertex.equals(endVertex))
            {
                LOG.info("end vertex found: " + nextVertex);
                break;
            }

            nextVertex.getSuccessors().forEach(vertex -> queue.add(vertex));
        }

        return visitList;
    }
}
