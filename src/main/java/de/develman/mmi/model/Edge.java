package de.develman.mmi.model;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Edge<T>
{
    protected final Vertex<T> source;
    protected final Vertex<T> sink;

    public Edge(Vertex<T> source, Vertex<T> sink)
    {
        this.source = source;
        this.sink = sink;
    }

    public Vertex<T> getSource()
    {
        return source;
    }

    public Vertex<T> getSink()
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
