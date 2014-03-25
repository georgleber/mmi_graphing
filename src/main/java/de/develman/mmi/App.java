package de.develman.mmi;

import com.airhacks.afterburner.injection.InjectionProvider;
import de.develman.mmi.ui.graphing.GraphingView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class App extends Application
{
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception
    {
        App.primaryStage = stage;

        GraphingView view = new GraphingView();
        Scene scene = new Scene(view.getView());

        stage.setTitle("MMI Graphing Library");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception
    {
        InjectionProvider.forgetAll();
    }

    public static void main(String[] args)
    {
        App.launch(args);
    }
}
