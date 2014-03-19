package de.develman.mmi.parser.impl;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.impl.DefaultVertex;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Georg Henkel
 */
public class AdjacentMatrixLoader extends AbstractLoader
{
    public AdjacentMatrixLoader(String fileName)
    {
        super(fileName);
    }

    @Override
    protected void readLines(Graph graph, BufferedReader lineReader) throws IOException
    {
        int cnt = 0;
        String strLine;
        while ((strLine = lineReader.readLine()) != null)
        {
            Vertex v = new DefaultVertex(cnt);
            graph.addVertex(v);

            loadEdges(graph, v, strLine);

            cnt++;
        }
    }

    private void loadEdges(Graph graph, Vertex v, String strLine)
    {
        String[] vEntries = strLine.split("\\s");
        for (int i = 0; i < vEntries.length; i++)
        {
            int value = Integer.parseInt(vEntries[i]);
            if (value > 0)
            {
                Vertex w = new DefaultVertex(i);
                graph.addVertex(w);

                graph.addEdge(v, w, value);
            }
        }
    }
}
