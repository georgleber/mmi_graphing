package de.develman.mmi.algorithm;

import de.develman.mmi.model.Vertex;
import java.util.*;

/**
 * Die Klasse BreadthFirstSearch implementiert die Breitensuche in einem Graphen.
 *
 * @param <T> Der Typ des Schlüssels eines Knoten
 * @param <V> Der Typ des Knoten
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class BreadthFirstSearch<T, V extends Vertex<T>>
{
    /**
     * Breitensuche vom Startknoten, die alle besuchten Knoten liefert
     *
     * @param startVertex Startknoten
     * @return Liste der Knoten, die von dem Startknoten erreicht werden
     */
    public List<Vertex<T>> doSearch(V startVertex)
    {
        List<Vertex<T>> vertexList = (List<Vertex<T>>) internalDoSearch(startVertex, null, false);
        return vertexList;
    }

    /**
     * Breitensuche von Startknoten zu Endknoten
     *
     * @param startVertex Startknoten
     * @param endVertex Endknoten
     * @return {@code true}, wenn der Endknoten gefunden wurde, anonsten {@code false}
     */
    public boolean doSearch(V startVertex, V endVertex)
    {
        boolean found = (boolean) internalDoSearch(startVertex, endVertex, true);
        return found;
    }

    private Object internalDoSearch(V startVertex, V endVertex, boolean listResult)
    {
        List<Vertex<T>> visitList = new ArrayList<>();

        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(startVertex);

        while (!queue.isEmpty())
        {
            Vertex<T> nextVertex = queue.poll();
            if (!listResult && nextVertex.equals(endVertex))
            {
                return true;
            }

            if (visitList.contains(nextVertex))
            {
                continue;
            }

            visitList.add(nextVertex);
            nextVertex.getSuccessors().forEach(vertex -> queue.add(vertex));
        }

        if (listResult)
        {
            return visitList;
        }
        else
        {
            return false;
        }
    }
}
