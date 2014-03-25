package de.develman.mmi.model;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Edge
{
    protected final Vertex source;
    protected final Vertex sink;

    public Edge(Vertex source, Vertex sink)
    {
        this.source = source;
        this.sink = sink;
    }

    public Vertex getSource()
    {
        return source;
    }

    public Vertex getSink()
    {
        return sink;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(source);
        builder.append(" --> ");
        builder.append(sink);

        return builder.toString();
    }
}
