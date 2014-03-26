package de.develman.mmi.parser.impl;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
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
            String firstLine = lineReader.readLine();
            int countVertices = Integer.parseInt(firstLine);
            addVertices(graph, countVertices);

            loadEdges(graph, lineReader);
        }
        catch (IOException ex)
        {
            LOG.error("Could not load file", ex);
            throw new RuntimeException("Error loading file", ex);
        }

        return graph;
    }

    protected void addVertices(Graph graph, int countVertices)
    {
        for (int i = 0; i < countVertices; i++)
        {
            Vertex vertex = graph.getVertex(i);
            if (vertex == null)
            {
                vertex = new Vertex(i);
                graph.addVertex(vertex);
            }
        }
    }

    protected abstract void loadEdges(Graph graph, BufferedReader lineReader) throws IOException;
}
