package com.ghulam.setting;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class SettingController {
    private StackPane stackPane;
    private AnchorPane rootPane;
    public void setPane(StackPane stackPane,AnchorPane rootPane){
        this.stackPane=stackPane;
        this.rootPane=rootPane;
    }
}
