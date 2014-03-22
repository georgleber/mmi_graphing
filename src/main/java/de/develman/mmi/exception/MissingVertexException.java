package de.develman.mmi.exception;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class MissingVertexException extends RuntimeException
{
    private Object missingKey;

    public MissingVertexException(Object key)
    {
        super("Failed to load vertex with key: " + key);
        this.missingKey = key;
    }

    public Object getMissingKey()
    {
        return missingKey;
    }
}
