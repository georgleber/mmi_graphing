package de.develman.mmi.ui.practicum2;

import de.develman.mmi.algorithm.Kruskal;
import de.develman.mmi.algorithm.Prim;
import de.develman.mmi.model.Edge;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.service.LoggingService;
import de.develman.mmi.ui.listener.GraphChangedListener;
import de.develman.mmi.ui.util.UIHelper;
import java.net.URL;
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
public class Practicum2Presenter implements Initializable, GraphChangedListener
{
    @FXML
    ComboBox<Integer> startVertexCBX;

    @Inject
    LoggingService loggingService;
    @Inject
    Kruskal kruskal;
    @Inject
    Prim prim;

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
    public void kruskalAction(ActionEvent event)
    {
        graph.unvisitAllVertices();
        loggingService.log("Kruskal");

        long startTime = System.currentTimeMillis();
        Graph minSpanTree = kruskal.getMinimalSpanningTree(graph);
        long endTime = System.currentTimeMillis();

        double cost = minSpanTree.getEdges().stream().mapToDouble(Edge::getWeight).sum();
        loggingService.log("Kosten des Minimal spannenden Baumes: " + cost);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void primAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertexCBX, defaultVertex);

        loggingService.log("Prim mit Startknoten: " + startVertex);

        long startTime = System.currentTimeMillis();
        Graph minSpanTree = prim.getMinimalSpanningTree(graph, startVertex);
        long endTime = System.currentTimeMillis();

        double cost = minSpanTree.getEdges().stream().mapToDouble(Edge::getWeight).sum();
        loggingService.log("Kosten des Minimal spannenden Baumes: " + cost);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }
}
