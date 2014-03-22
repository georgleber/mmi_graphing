package de.develman.mmi.exception;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class DuplicateVertexException extends RuntimeException
{
    private Object duplicateKey;

    public DuplicateVertexException(Object key)
    {
        super("Failed to insert duplicated vertex with key: " + key);
        this.duplicateKey = key;
    }

    public Object getDuplicateKey()
    {
        return duplicateKey;
    }
}
