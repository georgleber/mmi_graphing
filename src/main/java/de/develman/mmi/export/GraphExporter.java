package de.develman.mmi.export;

import de.develman.mmi.model.Graph;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class GraphExporter
{
    private final Graph graph;

    public GraphExporter(Graph graph)
    {
        this.graph = graph;
    }

    public String export()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("{\"vertices\":[");
        graph.getVertices().forEach(v ->
        {
            builder.append("{\"id\":\"");
            builder.append(v);
            builder.append("\",\"label\":\"");
            builder.append(v);
            builder.append("\"},");
        });
        builder.deleteCharAt(builder.length() - 1);

        builder.append("],\"edges\":[");
        graph.getEdges().forEach(e ->
        {
            builder.append("{\"from\":\"");
            builder.append(e.getSource());
            builder.append("\",\"to\":\"");
            builder.append(e.getSink());

            if (!Double.isNaN(e.getWeight()))
            {
                builder.append("\",\"label\":\"");
                builder.append(e.getWeight());
            }

            if (graph.isDirected())
            {
                builder.append("\",\"style\":\"arrow");
            }

            builder.append("\"},");
        });
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]}");

        return builder.toString();
    }
}
