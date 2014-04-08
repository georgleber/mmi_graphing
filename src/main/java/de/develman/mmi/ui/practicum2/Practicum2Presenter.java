/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.develman.mmi.ui.practicum2;

import de.develman.mmi.algorithm.Kruskal;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.service.LoggingService;
import de.develman.mmi.ui.listener.GraphChangedListener;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.inject.Inject;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Practicum2Presenter implements Initializable, GraphChangedListener
{
    @Inject
    LoggingService loggingService;

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
    public void kruskalAction(ActionEvent event)
    {
        graph.unvisitAllVertices();
        loggingService.log("Running Kruskal");

        long startTime = System.currentTimeMillis();
        List<Edge> minSpanTree = Kruskal.getMinimalSpanningTree(graph);
        long endTime = System.currentTimeMillis();

        double cost = minSpanTree.stream().mapToDouble(Edge::getWeight).sum();
        loggingService.log("Kosten des Minimal-Spannenden Baumes: " + cost);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void primAction(ActionEvent event)
    {

    }
}
