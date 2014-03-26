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
    protected void loadEdges(Graph graph, BufferedReader lineReader) throws IOException
    {
        String strLine;
        while ((strLine = lineReader.readLine()) != null)
        {
            loadEdges(graph, strLine);
        }
    }

    private void loadEdges(Graph graph, String strLine)
    {
        String[] vEntries = strLine.split("\\s+");

        int keySource = Integer.parseInt(vEntries[0]);
        Vertex source = graph.getVertex(keySource);

        int keySink = Integer.parseInt(vEntries[1]);
        Vertex sink = graph.getVertex(keySink);

        Edge edge = new Edge(source, sink);
        graph.addEdge(edge);

        if (!graph.isDirected())
        {
            edge = new Edge(sink, source);
            graph.addEdge(edge);
        }
    }
}
