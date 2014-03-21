package de.develman.mmi.parser.impl;

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

        int keyV = Integer.parseInt(vEntries[0]);
        Vertex<Integer> v = new Vertex(keyV);
        graph.addVertex(v);

        int keyW = Integer.parseInt(vEntries[1]);
        Vertex<Integer> w = new Vertex(keyW);
        graph.addVertex(w);

        // graph.addEdge(v, w, 1);
    }

}
