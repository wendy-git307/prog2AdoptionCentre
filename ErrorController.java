package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ErrorController extends Controller<Exception> {
    @FXML private Label errorLabel;
    @FXML private ImageView bannerError;
    @FXML private Button closeBtn;
    @FXML private Label errorMessage;

    @FXML
    public void initialize() {
        Image bannerImage2= new Image(getClass().getResourceAsStream("/image/error_banner.jpg"));
        bannerError.setImage(bannerImage2);
        if (errorLabel!=null) {errorLabel.setText(model.getClass().getSimpleName());}
        if (errorMessage!=null) {errorMessage.setText(model.getMessage());}
    }

    @FXML
    public void handleClose(ActionEvent event) {
        stage.close();
    }
}


