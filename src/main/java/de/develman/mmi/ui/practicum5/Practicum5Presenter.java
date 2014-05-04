package de.develman.mmi.ui.practicum5;

import de.develman.mmi.algorithm.Dijkstra;
import de.develman.mmi.algorithm.MooreBellmanFord;
import de.develman.mmi.exception.NegativeCycleException;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.model.algorithm.ShortestPath;
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
public class Practicum5Presenter implements Initializable, GraphChangedListener
{
    @FXML
    ComboBox<Integer> startVertex1CBX;
    @FXML
    ComboBox<Integer> endVertex1CBX;
    @FXML
    ComboBox<Integer> startVertex2CBX;
    @FXML
    ComboBox<Integer> endVertex2CBX;

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
        endVertex2CBX.setItems(vertexList);
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
        ShortestPath path = dijkstra.findShortestPath(graph, startVertex, endVertex);
        long endTime = System.currentTimeMillis();
        logPath(path, startVertex, endVertex);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void mooreBellmanFordAction(ActionEvent event
    )
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertex2CBX, defaultVertex);
        Vertex endVertex = UIHelper.loadVertex(graph, endVertex2CBX, null);

        loggingService.log(
                "Moore-Bellman-Ford Algorithmus mit Startknoten: " + startVertex + " und Zielknoten: " + endVertex);

        long startTime = System.currentTimeMillis();
        ShortestPath path = null;
        try
        {
            path = mooreBellmanFord.findShortestPath(graph, startVertex, endVertex);
        }
        catch (NegativeCycleException ex)
        {
            loggingService.log("Es wurde ein negativer Zykel gefunden.");
        }
        long endTime = System.currentTimeMillis();
        logPath(path, startVertex, endVertex);

        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    private void logPath(ShortestPath path, Vertex startVertex, Vertex endVertex)
    {
        if (path == null)
        {
            loggingService.log("Es gibt kein Weg zwischen " + startVertex + " und " + endVertex);
        }
        else
        {
            loggingService.log("Weg zwischen " + startVertex + " und " + endVertex + ": " + path.getVertices());
            loggingService.log("Länge des Wegs: " + path.getLength());
        }
    }
}
