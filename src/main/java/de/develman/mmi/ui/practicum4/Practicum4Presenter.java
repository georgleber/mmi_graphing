package de.develman.mmi.ui.practicum4;

import de.develman.mmi.algorithm.BranchAndBound;
import de.develman.mmi.algorithm.TryAllTours;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.service.LoggingService;
import de.develman.mmi.ui.listener.GraphChangedListener;
import de.develman.mmi.ui.util.UIHelper;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javax.inject.Inject;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class Practicum4Presenter implements Initializable, GraphChangedListener
{
    @FXML
    ComboBox<Integer> startVertexCBX;

    @Inject
    LoggingService loggingService;
    @Inject
    TryAllTours tryAllTour;
    @Inject
    BranchAndBound branchAndBound;

    private Graph graph;
    private ObservableList<Integer> vertexList;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        vertexList = FXCollections.observableArrayList();
        startVertexCBX.setItems(vertexList);
    }

    @Override
    public void graphChanged(Graph graph)
    {
        vertexList.clear();

        this.graph = graph;
        this.graph.getVertices().forEach(vertex -> vertexList.add(vertex.getKey()));
    }

    @FXML
    public void tryAllToursAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertexCBX, defaultVertex);

        loggingService.log("Alle Touren durchprobieren");

        long startTime = System.currentTimeMillis();
        List<Edge> tour = tryAllTour.findOptimalTour(graph, startVertex);
        long endTime = System.currentTimeMillis();

        double length = tour.stream().mapToDouble(Edge::getWeight).sum();
        loggingService.log("Länge der Tour: " + length);
        loggingService.log("Tour: " + tour);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void branchAndBoundAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertexCBX, defaultVertex);

        loggingService.log("Branch-&-Bound mit Startknoten: " + startVertex);

        long startTime = System.currentTimeMillis();
        List<Edge> tour = branchAndBound.findOptimalTour(graph, startVertex);
        long endTime = System.currentTimeMillis();

        double length = tour.stream().mapToDouble(Edge::getWeight).sum();
        loggingService.log("Länge der Tour: " + length);
        loggingService.log("Tour: " + tour);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }
}
