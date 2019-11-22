package com.vd5.stock;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {
    @FXML protected void shutdownApplication(ActionEvent event) {
        Log.info("Controller", "button clicked!");
        Platform.exit();
    }
}
