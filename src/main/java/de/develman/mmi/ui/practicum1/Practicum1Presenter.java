package de.develman.mmi.ui.practicum1;

import de.develman.mmi.algorithm.BreadthFirstSearch;
import de.develman.mmi.algorithm.DepthFirstSearch;
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
public class Practicum1Presenter implements Initializable, GraphChangedListener
{
    @FXML
    ComboBox<Integer> startVertexCBX;
    @FXML
    ComboBox<Integer> endVertexCBX;

    @Inject
    LoggingService loggingService;
    @Inject
    BreadthFirstSearch breadthSearch;
    @Inject
    DepthFirstSearch depthSearch;

    private Graph graph;
    private ObservableList<Integer> vertexList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
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
        this.graph.getVertices().forEach(vertex -> vertexList.add(vertex.getKey()));

    }

    @FXML
    public void bfsTraverseAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertexCBX, defaultVertex);
        Vertex endVertex = UIHelper.loadVertex(graph, endVertexCBX, null);

        loggingService.log("BFS mit Startknoten " + startVertex + " und Endknoten " + endVertex);

        long startTime = System.currentTimeMillis();
        List<Vertex> foundVertices = breadthSearch.getVerticesOnPath(startVertex, endVertex);
        long endTime = System.currentTimeMillis();

        logFoundPath(foundVertices, startVertex, endVertex);
        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void bfsFindComponents(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex startVertex = graph.getFirstVertex();

        loggingService.log("BFS mit Startknoten " + startVertex);

        long startTime = System.currentTimeMillis();
        int count = depthSearch.countComponents(graph, startVertex);
        long endTime = System.currentTimeMillis();

        loggingService.log("Es wurden " + count + " Zusammenhangskomponenten gefunden.");
        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void dfsTraverseAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex defaultVertex = graph.getFirstVertex();
        Vertex startVertex = UIHelper.loadVertex(graph, startVertexCBX, defaultVertex);
        Vertex endVertex = UIHelper.loadVertex(graph, endVertexCBX, null);

        loggingService.log("DFS mit Startknoten " + startVertex + " und Endknoten " + endVertex);

        long startTime = System.currentTimeMillis();
        List<Vertex> foundVertices = depthSearch.getVerticesOnPath(startVertex, endVertex);
        long endTime = System.currentTimeMillis();

        logFoundPath(foundVertices, startVertex, endVertex);
        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void dfsFindComponents(ActionEvent event)
    {
        graph.unvisitAllVertices();

        Vertex startVertex = graph.getFirstVertex();

        loggingService.log("DFS mit Startknoten " + startVertex);

        long startTime = System.currentTimeMillis();
        int count = depthSearch.countComponents(graph, startVertex);
        long endTime = System.currentTimeMillis();

        loggingService.log("Es wurden " + count + " Zusammenhangskomponenten gefunden.");
        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    private String loadVertexList(List<Vertex> vertices)
    {
        StringBuilder builder = new StringBuilder();

        vertices.forEach(vertex ->
        {
            builder.append(vertex);
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    private void logFoundPath(List<Vertex> foundVertices, Vertex startVertex, Vertex endVertex)
    {
        if (endVertex != null && !foundVertices.contains(endVertex))
        {
            loggingService.log("Es konnte kein Weg zwischen " + startVertex + " und " + endVertex + " gefunden werden.");
        }
        else
        {
            StringBuilder builder = new StringBuilder();
            if (endVertex != null)
            {
                builder.append("Folgender Weg wurde gefunden {");
            }
            else
            {
                builder.append("Gefundene Knoten {");
            }

            builder.append(loadVertexList(foundVertices));
            builder.append("}");
            loggingService.log(builder.toString());
        }
    }
}
