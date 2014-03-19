package de.develman.mmi.parser.impl;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.impl.UndirectedGraph;
import de.develman.mmi.parser.GraphLoader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Georg Henkel
 */
public abstract class AbstractLoader implements GraphLoader
{
    private static final Logger LOG = LoggerFactory.getLogger(AbstractLoader.class);

    private String fileName;

    public AbstractLoader(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public Graph loadGraph(boolean directed)
    {
        Graph graph;
        try (BufferedReader lineReader = new BufferedReader(new FileReader(fileName)))
        {
            // ignore first line (contains count of vertices)
            String firstLine = lineReader.readLine();
            int countVertices = Integer.parseInt(firstLine);
            graph = new UndirectedGraph(countVertices);

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
