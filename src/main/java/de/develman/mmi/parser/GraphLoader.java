package de.develman.mmi.parser;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public interface GraphLoader
{
    <T, V extends Vertex<T>> Graph<T, V> loadGraph(boolean directed);
}
