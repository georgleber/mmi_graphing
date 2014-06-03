package de.develman.mmi.model;

/**
 * Die Klasse Edge repräsentiert eine Kante in einem Graphen
 *
 * @author Georg Henkel <georg@develman.de>
 */
public class Edge
{
    private Double capacity;
    private Double cost;
    private final Vertex source;
    private final Vertex sink;

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
        this.capacity = Double.NaN;
        this.cost = Double.NaN;
    }

    /**
     * Erstellt eine neue Kante mit Start-, Endknoten und Kapazität
     *
     * @param source Startknoten
     * @param sink Endknoten
     * @param capacity Kapazität
     */
    public Edge(Vertex source, Vertex sink, Double capacity)
    {
        this.source = source;
        this.sink = sink;
        this.capacity = capacity;
        this.cost = capacity;
    }

    /**
     * Erstellt eine neue Kante mit Start-, Endknoten, Kapazität und Kosten
     *
     * @param source Startknoten
     * @param sink Endknoten
     * @param capacity Kapazität
     * @param cost Kosten
     */
    public Edge(Vertex source, Vertex sink, Double capacity, Double cost)
    {
        this.source = source;
        this.sink = sink;
        this.capacity = capacity;
        this.cost = cost;
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

    /**
     * @return Kapazität der Kante
     */
    public double getCapacity()
    {
        return capacity;
    }

    /**
     * Setzt eine neue Kantenkapazitöt
     *
     * @param capacity Die neue Kapazität der Kante
     */
    public void setCapacity(Double capacity)
    {
        this.capacity = capacity;
    }

    /**
     * @return Kosten der Kante
     */
    public double getCost()
    {
        return cost;
    }

    /**
     * Setzt neue Kantenkosten
     *
     * @param cost Die neuen Kosten der Kante
     */
    public void setCost(Double cost)
    {
        this.cost = cost;
    }

    /**
     * @return Liefert die gegenläufige Kante
     */
    public Edge revert()
    {
        return new Edge(sink, source, capacity, cost);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(source);
        builder.append("->");
        builder.append(sink);

        if (!capacity.isNaN())
        {
            if (!cost.isNaN())
            {
                builder.append(" (");
                builder.append(capacity);
                builder.append("),");
                builder.append(cost);
            }
            else
            {
                builder.append(" (");
                builder.append(capacity);
                builder.append("),");
            }
        }

        return builder.toString();
    }
}
