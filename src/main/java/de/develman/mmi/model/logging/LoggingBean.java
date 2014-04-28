package de.develman.mmi.model.logging;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class LoggingBean
{
    private final ObservableList<String> entries = FXCollections.observableArrayList();

    public void addEntry(String entry)
    {
        entries.add(entry);
    }

    public ObservableList<String> getEntries()
    {
        return entries;
    }

    public void clearEntries()
    {
        entries.clear();
    }
}
