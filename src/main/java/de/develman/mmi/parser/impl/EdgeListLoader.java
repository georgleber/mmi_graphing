package de.develman.mmi.parser.impl;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class EdgeListLoader extends AbstractLoader
{
    public EdgeListLoader(File file)
    {
        super(file);
    }

    @Override
    protected void readLines(Graph graph, BufferedReader lineReader) throws IOException
    {
        // dismiss first line
        lineReader.readLine();

        String strLine;
        while ((strLine = lineReader.readLine()) != null)
        {
            loadEdges(graph, strLine);
        }
    }

    private void loadEdges(Graph graph, String strLine)
    {
        String[] vEntries = strLine.split("\\s+");

        String keySource = vEntries[0];
        Vertex source = graph.getVertex(keySource);
        if (source == null)
        {
            source = new Vertex(keySource);
            graph.addVertex(source);
        }

        String keySink = vEntries[1];
        Vertex sink = graph.getVertex(keySink);
        if (sink == null)
        {
            sink = new Vertex(keySink);
            graph.addVertex(sink);
        }

        Edge edge = new Edge(source, sink);
        graph.addEdge(edge);
    }
}
