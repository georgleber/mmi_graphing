package de.develman.mmi.parser;

import de.develman.mmi.model.Graph;

/**
 * @author Georg Henkel
 */
public interface GraphLoader
{
    Graph loadGraph(boolean directed);
}
