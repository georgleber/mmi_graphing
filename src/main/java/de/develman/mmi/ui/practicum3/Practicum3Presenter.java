package de.develman.mmi.ui.practicum3;

import de.develman.mmi.algorithm.DoubleTree;
import de.develman.mmi.algorithm.NearestNeighbour;
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
public class Practicum3Presenter implements Initializable, GraphChangedListener
{
    @FXML
    ComboBox<Integer> startVertexCBX;

    @Inject
    LoggingService loggingService;

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
    public void nearestNeighbourAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertexCBX, defaultVertex);

        loggingService.log("Nearest-Neighbour mit Startknoten: " + startVertex);

        long startTime = System.currentTimeMillis();
        List<Edge> hamilton = NearestNeighbour.getHamilton(graph, startVertex);
        long endTime = System.currentTimeMillis();

        double length = hamilton.stream().mapToDouble(Edge::getWeight).sum();
        loggingService.log("Länge der Tour: " + length);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void doubleTreeAction(ActionEvent event)
    {
        graph.unvisitAllVertices();
        loggingService.log("Doppelter Baum");

        long startTime = System.currentTimeMillis();
        List<Edge> hamilton = DoubleTree.getHamilton(graph);
        long endTime = System.currentTimeMillis();

        double length = hamilton.stream().mapToDouble(Edge::getWeight).sum();
        loggingService.log("Länge der Tour: " + length);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }
}
