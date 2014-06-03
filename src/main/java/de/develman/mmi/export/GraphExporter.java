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
            if (!v.getBalance().isNaN())
            {
                builder.append(",(");
                builder.append(v.getBalance());
                builder.append(")");
            }
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

            if (!Double.isNaN(e.getCapacity()))
            {
                if (!Double.isNaN(e.getCost()))
                {
                    builder.append("\",\"label\":\"");
                    builder.append("(");
                    builder.append(e.getCapacity());
                    builder.append("),");
                    builder.append(e.getCost());
                }
                else
                {
                    builder.append("\",\"label\":\"");
                    builder.append(e.getCapacity());
                }
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
