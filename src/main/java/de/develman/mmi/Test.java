package de.develman.mmi;

import de.develman.mmi.model.Graph;
import de.develman.mmi.parser.FileParser;

public class Test
{
    public static void main(String[] args)
    {
        FileParser parser1 = new FileParser("data/Graph1.txt");
        Graph graph1 = parser1.loadGraph(false);

        String vertexList1 = graph1.printVertexList();
        System.out.println(vertexList1);
        String edgeList1 = graph1.printEdgeList();
        System.out.println(edgeList1);

        FileParser parser2 = new FileParser("data/Graph2.txt");
        Graph graph2 = parser2.loadGraph(true);

        String vertexList2 = graph2.printVertexList();
        System.out.println(vertexList2);
        String edgeList2 = graph2.printEdgeList();
        System.out.println(edgeList2);
    }
}
