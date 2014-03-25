package de.develman.mmi.algorithm;

import de.develman.mmi.model.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DepthFirstSearch implementiert die Tiefensuche in einem Graphen.
 * 
 * @author Georg Henkel <georg@develman.de>
 */
public class DepthFirstSearch
{
    /**
     * Tiefensuche vom Startknoten, die alle besuchten Knoten liefert
     * 
     * @param startVertex Startknoten
     * @return Liste der Knoten, die vom Startknoten erreicht werden
     */
    public static List<Vertex> doSearch(Vertex startVertex)
    {
        List<Vertex> visitList = new ArrayList<>();
        visitList.add(startVertex);
        
        startVertex.getSuccessors().forEach(vertex ->
        {
            if (!visitList.contains(vertex)) {
                List<Vertex> recList = doSearch(vertex);
                visitList.addAll(recList);
            }
        });
        
        return visitList;
    }
}
