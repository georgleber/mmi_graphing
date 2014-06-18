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
    public Graph loadGraph(boolean directed, boolean balanced, boolean grouped)
    {
        Graph graph = new Graph(directed);
        try (BufferedReader lineReader = new BufferedReader(new FileReader(file)))
        {
            addVertices(graph, lineReader, balanced, grouped);
            loadEdges(graph, lineReader, grouped);
        }
        catch (IOException ex)
        {
            LOG.error("Could not load file", ex);
            throw new RuntimeException("Error loading file", ex);
        }

        return graph;
    }

    protected void addVertices(Graph graph, BufferedReader lineReader, boolean balanced, boolean grouped) throws IOException
    {
        String firstLine = lineReader.readLine().trim();
        int countVertices = Integer.parseInt(firstLine);

        if (grouped)
        {
            String line = lineReader.readLine().trim();
            int groupedVerticeCount = Integer.parseInt(line);
            graph.setGroupedVerticeCount(groupedVerticeCount);
        }

        for (int i = 0; i < countVertices; i++)
        {

            Double balance = Double.NaN;
            if (balanced)
            {
                String line = lineReader.readLine().trim();
                balance = Double.parseDouble(line);
            }

            Vertex vertex = graph.getVertex(i);
            if (vertex == null)
            {
                vertex = new Vertex(i, balance);
                graph.addVertex(vertex);
            }
        }
    }

    protected abstract void loadEdges(Graph graph, BufferedReader lineReader, boolean grouped) throws IOException;
}
