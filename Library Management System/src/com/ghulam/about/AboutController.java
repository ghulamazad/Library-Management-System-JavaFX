package com.ghulam.about;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class AboutController {
    private StackPane stackPane;
    private AnchorPane rootPane;
    public void setPane(StackPane stackPane,AnchorPane rootPane){
        this.stackPane=stackPane;
        this.rootPane=rootPane;
    }
}
