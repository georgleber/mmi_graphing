package de.develman.mmi.ui.practicum5;

import de.develman.mmi.algorithm.Dijkstra;
import de.develman.mmi.algorithm.MooreBellmanFord;
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
public class Practicum5Presenter implements Initializable, GraphChangedListener
{
    @FXML
    ComboBox<Integer> startVertex1CBX;
    @FXML
    ComboBox<Integer> endVertex1CBX;
    @FXML
    ComboBox<Integer> startVertex2CBX;

    @Inject
    LoggingService loggingService;
    @Inject
    Dijkstra dijkstra;
    @Inject
    MooreBellmanFord mooreBellmanFord;

    private Graph graph;
    private ObservableList<Integer> vertexList;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        vertexList = FXCollections.observableArrayList();
        startVertex1CBX.setItems(vertexList);
        endVertex1CBX.setItems(vertexList);
        startVertex2CBX.setItems(vertexList);
    }

    @Override
    public void graphChanged(Graph graph)
    {
        vertexList.clear();

        this.graph = graph;
        this.graph.getVertices().forEach(vertex -> vertexList.add(vertex.getKey()));
    }

    @FXML
    public void dijkstraAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertex1CBX, defaultVertex);
        Vertex endVertex = UIHelper.loadVertex(graph, endVertex1CBX, null);

        loggingService.log("Dijkstra Algorithmus mit Startknoten: " + startVertex + " und Zielknoten: " + endVertex);

        long startTime = System.currentTimeMillis();
        double length = dijkstra.getLengthOfShortestPath(graph, startVertex, endVertex);
        long endTime = System.currentTimeMillis();

        loggingService.log("Länge der Tour: " + length);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void mooreBellmanFordAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertex2CBX, defaultVertex);

        loggingService.log("Moore-Bellman-Ford Algorithmus mit Startknoten: " + startVertex);

        long startTime = System.currentTimeMillis();
        List<Edge> tour = mooreBellmanFord.findTour(graph, startVertex);
        long endTime = System.currentTimeMillis();

        double length = tour.stream().mapToDouble(Edge::getWeight).sum();
        loggingService.log("Länge der Tour: " + length);
        loggingService.log("Tour: " + tour);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }
}
