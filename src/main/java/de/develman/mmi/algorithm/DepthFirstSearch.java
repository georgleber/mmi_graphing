package de.develman.mmi.algorithm;

import de.develman.mmi.model.Vertex;
import de.develman.mmi.service.LoggingService;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Die Klasse DepthFirstSearch implementiert die Tiefensuche in einem Graphen.
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class DepthFirstSearch
{
    private static final Logger LOG = LoggerFactory.getLogger(DepthFirstSearch.class);

    @Inject
    LoggingService loggingService;
    
    /**
     * Tiefensuche vom Startknoten, die alle besuchten Knoten liefert
     *
     * @param startVertex Startknoten
     * @return Liste der Knoten, die vom Startknoten erreicht werden
     */
    public List<Vertex> doSearch(Vertex startVertex)
    {
        String message = "Running DFS with start vertex: " + startVertex;
        LOG.debug(message);
        loggingService.log(message);

        List<Vertex> visitList = new ArrayList<>();
        visitList.add(startVertex);

        startVertex.getSuccessors().forEach(vertex ->
        {
            if (!visitList.contains(vertex))
            {
                List<Vertex> recList = doSearch(vertex);
                visitList.addAll(recList);
            }
        });

        return visitList;
    }
}
