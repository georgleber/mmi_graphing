package de.develman.mmi.ui.practicum7;

import de.develman.mmi.algorithm.CycleCanceling;
import de.develman.mmi.algorithm.SuccessiveShortestPath;
import de.develman.mmi.exception.MinimalCostFlowException;
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
public class Practicum7Presenter implements Initializable, GraphChangedListener
{
    @Inject
    LoggingService loggingService;
    @Inject
    CycleCanceling cycleCanceling;
    @Inject
    SuccessiveShortestPath successiveShortestPath;

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
    public void cycleCancelingAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        loggingService.log("Cycle-Canceling Algorithmus");

        long endTime;
        long startTime = System.currentTimeMillis();
        try
        {
            double minimalCostFlow = cycleCanceling.findMinimumCostFlow(graph);
            loggingService.log("Gesamtkosten des kostenminimalen Flusses: " + minimalCostFlow);
        }
        catch (MinimalCostFlowException ex)
        {
            loggingService.log("Kostenminimaler Fluss kann nicht ermittelt werden: " + ex.getMessage());
        }
        finally
        {
            endTime = System.currentTimeMillis();
        }

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void successiveShortestPathAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        loggingService.log("Successive-Shortes-Path Algorithmus");

        long endTime;
        long startTime = System.currentTimeMillis();
        try
        {
            double minimalCostFlow = successiveShortestPath.findMinimumCostFlow(graph);
            loggingService.log("Gesamtkosten des kostenminimalen Flusses: " + minimalCostFlow);
        }
        catch (MinimalCostFlowException ex)
        {
            loggingService.log("Kostenminimaler Fluss kann nicht ermittelt werden: " + ex.getMessage());
        }
        finally
        {
            endTime = System.currentTimeMillis();
        }

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }
}
