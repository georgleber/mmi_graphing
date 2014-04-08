/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.develman.mmi.ui.practicum1;

import de.develman.mmi.algorithm.BreadthFirstSearch;
import de.develman.mmi.algorithm.DepthFirstSearch;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.service.LoggingService;
import de.develman.mmi.ui.listener.GraphChangedListener;
import java.net.URL;
import java.util.ArrayList;
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

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        Vertex defaultVertex = vertices.get(0);

        Vertex startVertex = loadVertex(startVertexCBX, defaultVertex);
        Vertex endVertex = loadVertex(endVertexCBX, null);

        loggingService.log("Running BFS with Startknoten " + startVertex + " und Endknoten " + endVertex);

        long startTime = System.currentTimeMillis();
        List<Vertex> foundVertices = BreadthFirstSearch.getVerticesOnPath(startVertex, endVertex);
        long endTime = System.currentTimeMillis();

        logFoundPath(foundVertices, startVertex, endVertex);
        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void bfsFindComponents(ActionEvent event)
    {
        graph.unvisitAllVertices();

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        Vertex startVertex = vertices.get(0);

        loggingService.log("Running BFS with Startknoten " + startVertex);

        long startTime = System.currentTimeMillis();
        int count = DepthFirstSearch.countComponents(graph, startVertex);
        long endTime = System.currentTimeMillis();

        loggingService.log("Es wurden " + count + " Zusammenhangskomponenten gefunden.");
        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void dfsTraverseAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        Vertex defaultVertex = vertices.get(0);

        Vertex startVertex = loadVertex(startVertexCBX, defaultVertex);
        Vertex endVertex = loadVertex(endVertexCBX, null);

        loggingService.log("Running DFS with Startknoten " + startVertex + " und Endknoten " + endVertex);

        long startTime = System.currentTimeMillis();
        List<Vertex> foundVertices = DepthFirstSearch.getVerticesOnPath(startVertex, endVertex);
        long endTime = System.currentTimeMillis();

        logFoundPath(foundVertices, startVertex, endVertex);
        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    @FXML
    public void dfsFindComponents(ActionEvent event)
    {
        graph.unvisitAllVertices();

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        Vertex startVertex = vertices.get(0);

        loggingService.log("Running DFS with Startknoten " + startVertex);

        long startTime = System.currentTimeMillis();
        int count = DepthFirstSearch.countComponents(graph, startVertex);
        long endTime = System.currentTimeMillis();

        loggingService.log("Es wurden " + count + " Zusammenhangskomponenten gefunden.");
        loggingService.log("Laufzeit: " + (endTime - startTime) + "ms");
    }

    private Vertex loadVertex(ComboBox<Integer> vertexCbx, Vertex defaultValue)
    {
        Vertex vertex = defaultValue;

        Integer selectedKey = vertexCbx.getSelectionModel().selectedItemProperty().get();
        if (selectedKey != null)
        {
            vertex = graph.getVertex(selectedKey);
        }

        return vertex;
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
