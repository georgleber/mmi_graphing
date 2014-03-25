package de.develman.mmi.parser.impl;

import de.develman.mmi.model.Graph;
import de.develman.mmi.parser.GraphLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public abstract class AbstractLoader implements GraphLoader
{
    private static final Logger LOG = LoggerFactory.getLogger(AbstractLoader.class);

    private final File file;

    public AbstractLoader(File file)
    {
        this.file = file;
    }

    @Override
    public Graph loadGraph(boolean directed)
    {
        Graph graph = new Graph(directed);
        try (BufferedReader lineReader = new BufferedReader(new FileReader(file)))
        {
            readLines(graph, lineReader);
        }
        catch (IOException ex)
        {
            LOG.error("Could not load file", ex);
            throw new RuntimeException("Error loading file", ex);
        }

        return graph;
    }

    protected abstract void readLines(Graph graph, BufferedReader lineReader) throws IOException;
}
