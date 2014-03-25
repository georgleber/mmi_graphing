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
public class AdjacentMatrixLoader extends AbstractLoader
{
    public AdjacentMatrixLoader(File file)
    {
        super(file);
    }

    @Override
    protected void readLines(Graph graph, BufferedReader lineReader) throws IOException
    {
        // dismiss first line
        lineReader.readLine();

        int cnt = 0;
        String strLine;
        while ((strLine = lineReader.readLine()) != null)
        {
            String label = Integer.toString(cnt);
            Vertex source = graph.getVertex(label);
            if (source == null)
            {
                source = new Vertex(label);
                graph.addVertex(source);
            }

            loadEdges(graph, source, strLine);

            cnt++;
        }
    }

    private void loadEdges(Graph graph, Vertex source, String strLine)
    {
        String[] vEntries = strLine.split("\\s+");
        for (int i = 0; i < vEntries.length; i++)
        {
            int value = Integer.parseInt(vEntries[i]);
            if (value > 0)
            {
                String label = Integer.toString(i);
                Vertex sink = graph.getVertex(label);
                if (sink == null)
                {
                    sink = new Vertex(label);
                    graph.addVertex(sink);
                }

                Edge edge = new Edge(source, sink);
                graph.addEdge(edge);
            }
        }
    }
}
