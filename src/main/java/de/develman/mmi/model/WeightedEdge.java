package de.develman.mmi.model;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class WeightedEdge extends Edge
{
    private final Number weight;

    public WeightedEdge(Vertex source, Vertex sink, Number weight)
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
