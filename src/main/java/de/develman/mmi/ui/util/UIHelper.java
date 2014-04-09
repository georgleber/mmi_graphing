package de.develman.mmi.ui.util;

import de.develman.mmi.model.Graph;
import de.develman.mmi.model.Vertex;
import javafx.scene.control.ComboBox;

/**
 * @author Georg Henkel <georg@develman.de>
 */
public class UIHelper
{
    public static Vertex loadVertex(Graph graph, ComboBox<Integer> vertexCbx, Vertex defaultValue)
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
