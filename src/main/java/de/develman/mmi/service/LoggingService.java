package de.develman.mmi.service;

import de.develman.mmi.model.logging.LoggingBean;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class LoggingService
{
    private final LoggingBean loggingBean = new LoggingBean();

    public void log(String message)
    {
        loggingBean.addEntry(message);
    }

    public LoggingBean getLoggingBean()
    {
        return loggingBean;
    }

    public void clearLogging()
    {
        loggingBean.clearEntries();
    }
}
