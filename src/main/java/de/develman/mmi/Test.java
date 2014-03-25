package de.develman.mmi;

import de.develman.mmi.algorithm.BreadthFirstSearch;
import de.develman.mmi.export.GraphMLExporter;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.parser.FileParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Test
{
    public static void main(String[] args)
    {
        FileParser parser = new FileParser("data/Graph2.txt");
        Graph graph = parser.loadGraph(false);

        String vertexList = graph.printVertexList();
        System.out.println(vertexList);
        String edgeList = graph.printEdgeList();
        System.out.println(edgeList);

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        List<Vertex> startVertices = BreadthFirstSearch.doSearch(vertices.get(0));
        System.out.println("Anzahl der Zusammenhangskomponenten: " + startVertices.size());

        StringBuilder builder = new StringBuilder();
        builder.append("Knoten: {");

        startVertices.forEach(vertex ->
        {
            builder.append(vertex);
            builder.append(",");
        });

        builder.append("}");
        System.out.println(builder.toString());

        GraphMLExporter exporter = new GraphMLExporter(graph);
        String xml = exporter.toGraphML();

        try
        {
            File file = new File("data/graph_out.graphml");
            Files.write(Paths.get(file.toURI()), xml.getBytes("utf-8"), StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
}
