package de.develman.mmi.ui.graphing;

import de.develman.mmi.App;
import de.develman.mmi.export.GraphMLExporter;
import de.develman.mmi.model.Graph;
import de.develman.mmi.model.logging.LoggingBean;
import de.develman.mmi.parser.FileParser;
import de.develman.mmi.service.LoggingService;
import de.develman.mmi.ui.listener.GraphChangedListener;
import de.develman.mmi.ui.practicum1.Practicum1Presenter;
import de.develman.mmi.ui.practicum1.Practicum1View;
import de.develman.mmi.ui.practicum2.Practicum2Presenter;
import de.develman.mmi.ui.practicum2.Practicum2View;
import de.develman.mmi.ui.practicum3.Practicum3Presenter;
import de.develman.mmi.ui.practicum3.Practicum3View;
import de.develman.mmi.ui.practicum4.Practicum4Presenter;
import de.develman.mmi.ui.practicum4.Practicum4View;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
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
    AnchorPane practicum1Tab;
    @FXML
    AnchorPane practicum2Tab;
    @FXML
    AnchorPane practicum3Tab;
    @FXML
    AnchorPane practicum4Tab;
    @FXML
    CheckBox directedCbx;

    @Inject
    LoggingService loggingService;

    private Graph graph;
    private BooleanProperty directed;
    private final List<GraphChangedListener> changeListener = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        LoggingBean loggingBean = loggingService.getLoggingBean();
        loggingView.setItems(loggingBean.getEntries());

        directed = new SimpleBooleanProperty(false);
        directedCbx.selectedProperty().bindBidirectional(directed);

        Practicum1View practicum1View = new Practicum1View();
        practicum1Tab.getChildren().add(practicum1View.getView());
        Practicum2View practicum2View = new Practicum2View();
        practicum2Tab.getChildren().add(practicum2View.getView());
        Practicum3View practicum3View = new Practicum3View();
        practicum3Tab.getChildren().add(practicum3View.getView());
        Practicum4View practicum4View = new Practicum4View();
        practicum4Tab.getChildren().add(practicum4View.getView());

        Practicum1Presenter practicum1Presenter = (Practicum1Presenter) practicum1View.getPresenter();
        changeListener.add(practicum1Presenter);
        Practicum2Presenter practicum2Presenter = (Practicum2Presenter) practicum2View.getPresenter();
        changeListener.add(practicum2Presenter);
        Practicum3Presenter practicum3Presenter = (Practicum3Presenter) practicum3View.getPresenter();
        changeListener.add(practicum3Presenter);
        Practicum4Presenter practicum4Presenter = (Practicum4Presenter) practicum4View.getPresenter();
        changeListener.add(practicum4Presenter);
    }

    @FXML
    public void loadGraphAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Graph laden");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph", "*.txt"));

        File file = fileChooser.showOpenDialog(App.primaryStage);
        if (file != null)
        {
            FileParser parser = new FileParser(file);
            graph = parser.loadGraph(directed.get());

            loggingService.clearLogging();
            loggingService.
                    log("Graph wurde erfolgreich geladen, Anzahl Knoten: " + graph.countVertices() + ", Kanten: " + graph.
                            countEdges());
        }

        fireGraphChanged();
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

    private void fireGraphChanged()
    {
        changeListener.forEach(l -> l.graphChanged(graph));
    }
}
