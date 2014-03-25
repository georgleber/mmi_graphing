package de.develman.mmi.parser;

import de.develman.mmi.model.Graph;
import de.develman.mmi.parser.impl.AdjacentMatrixLoader;
import de.develman.mmi.parser.impl.EdgeListLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class FileParser
{
    private static final Logger LOG = LoggerFactory.getLogger(FileParser.class);

    private final File file;

    public FileParser(File file)
    {
        this.file = file;
    }

    public Graph loadGraph(boolean directed)
    {
        GraphLoader loader;
        if (isAdjacent())
        {
            LOG.debug("Using AdjacentMatrixLoader for loading graph");
            loader = new AdjacentMatrixLoader(file);
        }
        else
        {
            LOG.debug("Using EdgeListLoader for loading graph");
            loader = new EdgeListLoader(file);
        }

        // FIXME: check if directed
        return loader.loadGraph(directed);
    }

    private boolean isAdjacent()
    {
        boolean adjacent = false;
        try (BufferedReader lineReader = new BufferedReader(new FileReader(file)))
        {
            String firstLine = lineReader.readLine();
            int countVertices = Integer.parseInt(firstLine);

            LOG.debug("Count of vertices is: " + countVertices);

            String secondLine = lineReader.readLine();
            StringTokenizer st = new StringTokenizer(secondLine);
            if (st.countTokens() == countVertices)
            {
                LOG.debug("File content is adjacent");
                adjacent = true;
            }
        }
        catch (IOException ex)
        {
            LOG.error("Could not open file", ex);
            throw new RuntimeException("Error loading file", ex);
        }

        return adjacent;
    }
}
