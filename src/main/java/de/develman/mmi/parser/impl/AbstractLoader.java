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
            addVertices(graph, lineReader);
            loadEdges(graph, lineReader);
        }
        catch (IOException ex)
        {
            LOG.error("Could not load file", ex);
            throw new RuntimeException("Error loading file", ex);
        }

        return graph;
    }

    protected void addVertices(Graph graph, BufferedReader lineReader) throws IOException
    {
        String firstLine = lineReader.readLine().trim();
        int countVertices = Integer.parseInt(firstLine);

        boolean balanced = checkBalance(lineReader);
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

    boolean checkBalance(BufferedReader lineReader) throws IOException
    {
        lineReader.mark(10000);
        String strLine = lineReader.readLine().trim();
        lineReader.reset();

        return strLine.split("\\s+").length == 1;
    }

    protected abstract void loadEdges(Graph graph, BufferedReader lineReader) throws IOException;
}
