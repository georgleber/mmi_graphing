package de.develman.mmi.ui.graphing;

import de.develman.mmi.App;
import de.develman.mmi.algorithm.BreadthFirstSearch;
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    Label statusLabel;
    @FXML
    ListView<String> loggingView;

    @Inject
    LoggingService loggingService;
    @Inject
    BreadthFirstSearch bfs;

    private Graph graph;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        LoggingBean loggingBean = loggingService.getLoggingBean();
        loggingView.setItems(loggingBean.getEntries());
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

            statusLabel.setText("Graph wurde erfolgreich geladen");
            loggingService.clearLogging();
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
        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        List<Vertex> foundVertices = bfs.doSearch(vertices.get(0));

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
}
