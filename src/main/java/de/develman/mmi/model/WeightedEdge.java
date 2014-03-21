package de.develman.mmi.model;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class WeightedEdge<T> extends Edge<T>
{
    private final Number weight;

    public WeightedEdge(Vertex<T> source, Vertex<T> sink, Number weight)
    {
        super(source, sink);

        this.weight = weight;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(source);
        builder.append(" -[");
        builder.append(weight);
        builder.append("]-> ");
        builder.append(sink);

        return builder.toString();
    }
}
