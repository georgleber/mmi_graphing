package de.develman.mmi.ui.practicum6;

import de.develman.mmi.algorithm.FordFulkerson;
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
public class Practicum6Presenter implements Initializable, GraphChangedListener
{
    @FXML
    ComboBox<Integer> startVertexCBX;
    @FXML
    ComboBox<Integer> endVertexCBX;

    @Inject
    LoggingService loggingService;
    @Inject
    FordFulkerson fordFulkerson;

    private Graph graph;
    private ObservableList<Integer> vertexList;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        vertexList = FXCollections.observableArrayList();
        startVertexCBX.setItems(vertexList);
        endVertexCBX.setItems(vertexList);
    }

    @Override
    public void graphChanged(Graph graph)
    {
        vertexList.clear();

        this.graph = graph;
        if (graph != null)
        {
            this.graph.getVertices().forEach(vertex -> vertexList.add(vertex.getKey()));
        }
    }

    @FXML
    public void fordFulkersonAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertexCBX, defaultVertex);
        Vertex endVertex = UIHelper.loadVertex(graph, endVertexCBX, null);

        loggingService.log(
                "Ford-Fulkerson Algorithmus mit Startknoten: " + startVertex + " und Zielknoten: " + endVertex);

        long startTime = System.currentTimeMillis();
        double maxFlow = fordFulkerson.findMaxFlow(graph, startVertex, endVertex);
        long endTime = System.currentTimeMillis();
        loggingService.log("Maximaler Fluss: " + maxFlow);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }
}
