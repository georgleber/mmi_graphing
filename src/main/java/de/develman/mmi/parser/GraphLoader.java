package de.develman.mmi.parser;

import de.develman.mmi.model.Graph;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public interface GraphLoader
{
    Graph loadGraph(boolean directed);
}
