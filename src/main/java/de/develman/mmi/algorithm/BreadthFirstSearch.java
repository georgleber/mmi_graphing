package de.develman.mmi.algorithm;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
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

    private Map<Vertex, Vertex> parentVertexMap;

    /**
     * Breitensuche vom Startknoten, die alle besuchten Knoten liefert
     *
     * @param startVertex Startknoten
     * @return Liste der Knoten, die von dem Startknoten erreicht werden
     */
    public List<Vertex> getAccessibleVertices(Vertex startVertex)
    {
        return getVerticesOnPath(startVertex, null);
    }

    /**
     * Prüft, ob es einen Weg zwischen Start- und Endknoten gibt
     *
     * @param startVertex Startknoten
     * @param endVertex Endknoten
     * @return {@code true}, wenn ein Weg gefunden wurde
     */
    public boolean hasPath(Vertex startVertex, Vertex endVertex)
    {
        boolean pathFound = false;

        List<Vertex> foundVertices = getVerticesOnPath(startVertex, endVertex);
        if (foundVertices.contains(startVertex) && foundVertices.contains(endVertex))
        {
            pathFound = true;
        }

        return pathFound;
    }

    public List<Edge> getPath(Graph graph, Vertex startVertex, Vertex endVertex)
    {
        parentVertexMap = new HashMap<>();

        List<Edge> path = null;
        List<Vertex> foundVertices = getVerticesOnPath(startVertex, endVertex);
        if (foundVertices.contains(startVertex) && foundVertices.contains(endVertex))
        {
            path = constructPath(graph, endVertex);
        }

        return path;
    }

    /**
     * Breitensuche von Startknoten zu Endknoten
     *
     * @param startVertex Startknoten
     * @param endVertex Endknoten
     * @return Liste der besuchten Knoten, von Startknoten bis Endknoten
     */
    public List<Vertex> getVerticesOnPath(Vertex startVertex, Vertex endVertex)
    {
        startVertex.setVisited(true);
        parentVertexMap.put(startVertex, null);

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
                parentVertexMap.put(vertex, nextVertex);

                vertex.setVisited(true);
                queue.add(vertex);
            });
        }

        return visitList;
    }

    protected List<Edge> constructPath(Graph graph, Vertex vertex)
    {
        List<Edge> path = new ArrayList<>();
        while (parentVertexMap.get(vertex) != null)
        {
            Vertex parent = parentVertexMap.get(vertex);
            Edge edge = graph.getEdge(parent, vertex);
            if (edge != null)
            {
                path.add(edge);
            }

            vertex = parent;
        }

        Collections.reverse(path);
        return path;
    }
}
