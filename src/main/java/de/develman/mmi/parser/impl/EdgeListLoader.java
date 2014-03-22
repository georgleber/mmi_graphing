package de.develman.mmi.parser.impl;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class EdgeListLoader extends AbstractLoader
{
    public EdgeListLoader(String fileName)
    {
        super(fileName);
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
        String[] vEntries = strLine.split("\\s");

        int keySource = Integer.parseInt(vEntries[0]);
        Vertex<Integer> source = new Vertex(keySource);
        if (!graph.containsVertex(source))
        {
            graph.addVertex(source);
        }

        int keySink = Integer.parseInt(vEntries[1]);
        Vertex<Integer> sink = new Vertex(keySink);
        if (!graph.containsVertex(sink))
        {
            graph.addVertex(sink);
        }

        Edge<Integer> edge = new Edge<>(source, sink);
        graph.addEdge(edge);
    }
}
