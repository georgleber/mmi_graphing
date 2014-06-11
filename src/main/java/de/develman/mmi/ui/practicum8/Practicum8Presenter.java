package de.develman.mmi.ui.practicum8;

import de.develman.mmi.algorithm.Matching;
import de.develman.mmi.model.Graph;
import de.develman.mmi.service.LoggingService;
import de.develman.mmi.ui.listener.GraphChangedListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.inject.Inject;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Practicum8Presenter implements Initializable, GraphChangedListener
{
    @Inject
    LoggingService loggingService;
    @Inject
    Matching matching;

    private Graph graph;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    @Override
    public void graphChanged(Graph graph)
    {
        this.graph = graph;
    }

    @FXML
    public void matchingAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        loggingService.log("Matching Algorithmus");

        long startTime = System.currentTimeMillis();
        int countEdges = matching.countMatching(graph);
        loggingService.log("Anzahl Kanten mit Fluss: " + countEdges);
        long endTime = System.currentTimeMillis();

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }
}
