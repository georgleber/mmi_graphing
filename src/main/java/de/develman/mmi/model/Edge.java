package de.develman.mmi.model;

/**
 * Die Klasse Edge repräsentiert eine Kante in einem Graphen
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Edge
{
    protected final Vertex source;
    protected final Vertex sink;

    /**
     * Erstellt eine neue Kante mit Start- und Endknoten
     *
     * @param source Startknoten
     * @param sink Endknoten
     */
    public Edge(Vertex source, Vertex sink)
    {
        this.source = source;
        this.sink = sink;
    }

    /**
     * @return Startknoten der Kante
     */
    public Vertex getSource()
    {
        return source;
    }

    /**
     * @return Endknoten der Kante
     */
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
