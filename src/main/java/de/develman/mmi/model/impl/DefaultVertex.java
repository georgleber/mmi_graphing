package de.develman.mmi.model.impl;

import de.develman.mmi.model.Vertex;
import java.util.Objects;

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
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + this.key;
        hash = 23 * hash + Objects.hashCode(this.color);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (getClass() != obj.getClass())
        {
            return false;
        }

        final DefaultVertex other = (DefaultVertex) obj;
        if (this.key != other.key)
        {
            return false;
        }

        if (!Objects.equals(this.color, other.color))
        {
            return false;
        }

        return true;
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
