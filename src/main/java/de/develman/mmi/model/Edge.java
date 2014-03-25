package de.develman.mmi.model;

/**
 * Die Klasse Edge repräsentiert eine Kante in einem Graphen
 *
 * @param <T> Typ der Knoten die an einer Kante liegen
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Edge<T>
{
    protected final Vertex<T> source;
    protected final Vertex<T> sink;

    /**
     * Erstellt eine neue Kante mit Start- und Endknoten
     *
     * @param source Startknoten
     * @param sink Endknoten
     */
    public Edge(Vertex<T> source, Vertex<T> sink)
    {
        this.source = source;
        this.sink = sink;
    }

    /**
     * @return Startknoten der Kante
     */
    public Vertex<T> getSource()
    {
        return source;
    }

    /**
     * @return Endknoten der Kante
     */
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
