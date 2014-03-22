package de.develman.mmi.parser.impl;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Georg Henkel <georg@develman.de>
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
        // dismiss first line
        lineReader.readLine();

        int cnt = 0;
        String strLine;
        while ((strLine = lineReader.readLine()) != null)
        {
            Vertex<Integer> source = new Vertex<>(cnt);
            if (!graph.containsVertex(source))
            {
                graph.addVertex(source);
            }

            loadEdges(graph, source, strLine);

            cnt++;
        }
    }

    private void loadEdges(Graph graph, Vertex source, String strLine)
    {
        String[] vEntries = strLine.split("\\s");
        for (int i = 0; i < vEntries.length; i++)
        {
            int value = Integer.parseInt(vEntries[i]);
            if (value > 0)
            {
                Vertex<Integer> sink = new Vertex(i);
                if (!graph.containsVertex(sink))
                {
                    graph.addVertex(sink);
                }

                Edge<Integer> edge = new Edge<>(source, sink);
                graph.addEdge(edge);
            }
        }
    }
}
