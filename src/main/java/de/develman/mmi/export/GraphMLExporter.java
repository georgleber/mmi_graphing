package de.develman.mmi.export;

import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import org.apache.commons.lang.StringUtils;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class GraphMLExporter
{
    private final Graph graph;

    public GraphMLExporter(Graph graph)
    {
        this.graph = graph;
    }

    public String toGraphML()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        builder.append(
            "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:y=\"http://www.yworks.com/xml/graphml\" xmlns:yed=\"http://www.yworks.com/xml/yed/3\" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd\">\n");
        builder.append("\t<key for=\"node\" id=\"d6\" yfiles.type=\"nodegraphics\"/>\n");
        builder.append("\t<key for=\"edge\" id=\"d9\" yfiles.type=\"edgegraphics\"/>\n");

        builder.append("\t<graph edgedefault=\"");
        if (graph.isDirected())
        {
            builder.append("directed");
        }
        else
        {
            builder.append("undirected");
        }
        builder.append("\" id=\"G\">\n");

        double y = 0;
        double x = 0;
        for (Vertex v : graph.getVertices())
        {
            String nodeId = getVertexId(v);
            builder.append("\t\t<node id=\"");
            builder.append(nodeId);
            builder.append("\">\n");

            double width = 30.0;
            double height = 30.0;
            int fontSize = 12;

            String fontFamily = "Dialog"; // Segoe UI
            builder.append(
                "\t\t\t<data key=\"d6\">\n\t\t\t\t<y:ShapeNode>\n\t\t\t\t\t<y:Fill color=\"#FFCCEE\" transparent=\"false\"/>\n");
            builder.append("\t\t\t\t\t<y:Geometry height=\"");
            builder.append(height);
            builder.append("\" width=\"");
            builder.append(width);
            builder.append("\" y=\"");
            builder.append(y);
            builder.append("\" x=\"");
            builder.append(x);
            builder.append("\" />\n");
            builder.append("\t\t\t\t\t<y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>\n");
            builder.append("\t\t\t\t\t<y:NodeLabel visible=\"true\"");
            builder.append(" fontFamily=\"");
            builder.append(fontFamily);
            builder.append("\"");
            builder.append(" fontSize=\"");
            builder.append(fontSize);
            builder.append("\"");
            builder.append(" fontStyle=\"plain\">");
            builder.append(v);
            builder.append("</y:NodeLabel>\n");
            builder.append("\t\t\t\t\t<y:Shape type=\"ellipse\"/>\n\t\t\t\t</y:ShapeNode>\n");
            builder.append("\t\t\t</data>\n");
            builder.append("\t\t</node>\n");
            y += 40.0;
            x += 40.0;
        }

        graph.getEdges().forEach(e ->
        {
            String edgeId = getEdgeId(e);
            String sourceNodeId = getVertexId(e.getSource());
            String targetNodeId = getVertexId(e.getSink());

            builder.append("\t\t<edge id=\"");
            builder.append(edgeId);
            builder.append("\" source=\"");
            builder.append(sourceNodeId);
            builder.append("\" target=\"");
            builder.append(targetNodeId);
            builder.append("\">\n");

            builder.append("\t\t\t<data key=\"d9\">\n");
            builder.append("\t\t\t\t<y:PolyLineEdge>\n");
            builder.append("\t\t\t\t\t<y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\"/>\n");
            builder.append("\t\t\t\t\t<y:LineStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>\n");

            if (!graph.isDirected())
            {
                builder.append("\t\t\t\t\t<y:Arrows source=\"none\" target=\"none\"/>\n");
            }
            else
            {
                builder.append("\t\t\t\t\t<y:Arrows source=\"none\" target=\"standard\"/>\n");
            }

            builder.append("\t\t\t\t\t<y:BendStyle smoothed=\"false\"/>\n");
            builder.append("\t\t\t\t</y:PolyLineEdge>\n");
            builder.append("\t\t\t</data>\n");

            builder.append("\t\t</edge>\n");
        });

        builder.append("\t</graph>\n");
        builder.append("</graphml>");

        return builder.toString();
    }

    private int maxRowLength(String str)
    {
        int max = 0;
        for (String row : StringUtils.split(str, "\n"))
        {
            if (row.length() > max)
            {
                max = row.length();
            }
        }
        return max;
    }

    private String getEdgeId(Edge e)
    {
        String edgeId = e.getSource().getKey() + "->" + e.getSink().getKey();
        return "e" + edgeId.hashCode();
    }

    private String getVertexId(Vertex v)
    {
        String nodeId = "" + v.getKey();
        return "n" + nodeId.hashCode();
    }
}
