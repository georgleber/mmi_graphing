package de.develman.mmi.parser.impl;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.impl.DefaultVertex;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Georg Henkel
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
        Vertex v = new DefaultVertex(keyV);
        graph.addVertex(v);

        int keyW = Integer.parseInt(vEntries[1]);
        Vertex w = new DefaultVertex(keyW);
        graph.addVertex(w);

        graph.addEdge(v, w, 1);
    }

}
