package de.develman.mmi.ui.graphing;

import de.develman.mmi.App;
import de.develman.mmi.algorithm.BreadthFirstSearch;
import de.develman.mmi.algorithm.DepthFirstSearch;
import de.develman.mmi.export.GraphMLExporter;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import de.develman.mmi.parser.FileParser;
import de.develman.mmi.service.LoggingService;
import de.develman.mmi.service.model.LoggingBean;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javax.inject.Inject;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class GraphingPresenter implements Initializable
{
    @FXML
    ListView<String> loggingView;
    @FXML
    ComboBox<Integer> bfsStartVertexCBX;
    @FXML
    ComboBox<Integer> bfsEndVertexCBX;
    @FXML
    ComboBox<Integer> dfsVertexCBX;

    @Inject
    LoggingService loggingService;
    @Inject
    BreadthFirstSearch bfs;
    @Inject
    DepthFirstSearch dfs;

    private ObservableList<Integer> vertexList;
    private Graph graph;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        LoggingBean loggingBean = loggingService.getLoggingBean();
        loggingView.setItems(loggingBean.getEntries());

        vertexList = FXCollections.observableArrayList();
        bfsStartVertexCBX.setItems(vertexList);
        bfsEndVertexCBX.setItems(vertexList);
        dfsVertexCBX.setItems(vertexList);
    }

    @FXML
    public void loadGraphAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Graph laden");

        File file = fileChooser.showOpenDialog(App.primaryStage);
        if (file != null)
        {
            FileParser parser = new FileParser(file);
            graph = parser.loadGraph(true);
            graph.getVertices().forEach(vertex -> vertexList.add(vertex.getKey()));

            loggingService.clearLogging();
            loggingService.
                log("Graph wurde erfolgreich geladen, Anzahl Knoten: " + graph.countVertices() + ", Kanten: " + graph.
                    countEdges());
        }
    }

    @FXML
    public void exportGraphAction(ActionEvent event)
    {
        GraphMLExporter exporter = new GraphMLExporter(graph);
        String xml = exporter.toGraphML();

        try
        {
            File file = new File("data/graph_out.graphml");
            Files.write(Paths.get(file.toURI()), xml.getBytes("utf-8"), StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @FXML
    public void bfsTraverseAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        Vertex defaultVertex = vertices.get(0);

        Vertex startVertex = loadVertex(bfsStartVertexCBX, defaultVertex);
        Vertex endVertex = loadVertex(bfsEndVertexCBX, null);

        List<Vertex> foundVertices = bfs.doSearch(startVertex, endVertex);
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

            foundVertices.forEach(vertex ->
            {
                builder.append(vertex);
                builder.append(",");
            });
            builder.append("}");

            loggingService.log(builder.toString());
        }
    }

    public void dfsTraverseAction(ActionEvent event)
    {
        graph.unvisitAllVertices();

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        Vertex defaultVertex = vertices.get(0);
        Vertex startVertex = loadVertex(dfsVertexCBX, defaultVertex);

        loggingService.log("Running DFS with Startknoten " + startVertex);

        List<Vertex> foundVertices = dfs.doSearch(startVertex);

        StringBuilder builder = new StringBuilder();
        builder.append("Gefundene Knoten {");
        foundVertices.forEach(vertex ->
        {
            builder.append(vertex);
            builder.append(",");
        });
        builder.append("}");

        loggingService.log(builder.toString());
    }

    public void findCompsites(ActionEvent event)
    {
        graph.unvisitAllVertices();

        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        Vertex defaultVertex = vertices.get(0);
        Vertex startVertex = loadVertex(dfsVertexCBX, defaultVertex);

        loggingService.log("Running DFS with Startknoten " + startVertex);

        List<List<Vertex>> components = dfs.loadComponents(graph, startVertex);
        loggingService.log("Es wurden " + components.size() + " Zusammenhangskomponenten gefunden.");
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
}
