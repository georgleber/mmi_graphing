package de.develman.mmi.model.impl;

import de.develman.mmi.model.Vertex;

/**
 * @author: Georg Henkel
 */
public class DefaultVertex implements Vertex
{
    public int key;
    public String color;

    public DefaultVertex(int key)
    {
        this.key = key;
    }

    @Override
    public int key()
    {
        return key;
    }

    @Override
    public String color()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    @Override
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(key);

        if (color != null)
        {
            buffer.append("[");
            buffer.append(color);
            buffer.append("]");
        }

        return buffer.toString();
    }
}
