package de.develman.mmi.ui.graphing;

import de.develman.mmi.App;
import de.develman.mmi.export.GraphExporter;
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
import de.develman.mmi.ui.practicum5.Practicum5Presenter;
import de.develman.mmi.ui.practicum5.Practicum5View;
import de.develman.mmi.ui.practicum6.Practicum6Presenter;
import de.develman.mmi.ui.practicum6.Practicum6View;
import de.develman.mmi.ui.practicum7.Practicum7Presenter;
import de.develman.mmi.ui.practicum7.Practicum7View;
import de.develman.mmi.ui.practicum8.Practicum8Presenter;
import de.develman.mmi.ui.practicum8.Practicum8View;
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
    AnchorPane practicum5Tab;
    @FXML
    AnchorPane practicum6Tab;
    @FXML
    AnchorPane practicum7Tab;
    @FXML
    AnchorPane practicum8Tab;
    @FXML
    CheckBox directedCbx;
    @FXML
    CheckBox balancedCbx;
    @FXML
    CheckBox groupedCbx;

    @Inject
    LoggingService loggingService;

    private Graph graph;
    private BooleanProperty directed;
    private BooleanProperty balanced;
    private BooleanProperty grouped;
    private final List<GraphChangedListener> changeListener = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        LoggingBean loggingBean = loggingService.getLoggingBean();
        loggingView.setItems(loggingBean.getEntries());

        directed = new SimpleBooleanProperty(false);
        directedCbx.selectedProperty().bindBidirectional(directed);
        balanced = new SimpleBooleanProperty(false);
        balancedCbx.selectedProperty().bindBidirectional(balanced);
        grouped = new SimpleBooleanProperty(false);
        groupedCbx.selectedProperty().bindBidirectional(grouped);

        Practicum1View practicum1View = new Practicum1View();
        practicum1Tab.getChildren().add(practicum1View.getView());
        Practicum2View practicum2View = new Practicum2View();
        practicum2Tab.getChildren().add(practicum2View.getView());
        Practicum3View practicum3View = new Practicum3View();
        practicum3Tab.getChildren().add(practicum3View.getView());
        Practicum4View practicum4View = new Practicum4View();
        practicum4Tab.getChildren().add(practicum4View.getView());
        Practicum5View practicum5View = new Practicum5View();
        practicum5Tab.getChildren().add(practicum5View.getView());
        Practicum6View practicum6View = new Practicum6View();
        practicum6Tab.getChildren().add(practicum6View.getView());
        Practicum7View practicum7View = new Practicum7View();
        practicum7Tab.getChildren().add(practicum7View.getView());
        Practicum8View practicum8View = new Practicum8View();
        practicum8Tab.getChildren().add(practicum8View.getView());

        Practicum1Presenter practicum1Presenter = (Practicum1Presenter) practicum1View.getPresenter();
        changeListener.add(practicum1Presenter);
        Practicum2Presenter practicum2Presenter = (Practicum2Presenter) practicum2View.getPresenter();
        changeListener.add(practicum2Presenter);
        Practicum3Presenter practicum3Presenter = (Practicum3Presenter) practicum3View.getPresenter();
        changeListener.add(practicum3Presenter);
        Practicum4Presenter practicum4Presenter = (Practicum4Presenter) practicum4View.getPresenter();
        changeListener.add(practicum4Presenter);
        Practicum5Presenter practicum5Presenter = (Practicum5Presenter) practicum5View.getPresenter();
        changeListener.add(practicum5Presenter);
        Practicum6Presenter practicum6Presenter = (Practicum6Presenter) practicum6View.getPresenter();
        changeListener.add(practicum6Presenter);
        Practicum7Presenter practicum7Presenter = (Practicum7Presenter) practicum7View.getPresenter();
        changeListener.add(practicum7Presenter);
        Practicum8Presenter practicum8Presenter = (Practicum8Presenter) practicum8View.getPresenter();
        changeListener.add(practicum8Presenter);
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
            graph = parser.loadGraph(directed.get(), balanced.get(), grouped.get());

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
        GraphExporter exporter = new GraphExporter(graph);
        String json = exporter.export();

        try
        {
            File file = new File("data/graph.json");
            Files.write(Paths.get(file.toURI()), json.getBytes("utf-8"), StandardOpenOption.CREATE,
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
