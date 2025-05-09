package controller;

import java.io.IOException;
import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import model.Users.*;
import model.Application.AdoptionCentre;
import model.Exceptions.*;

public class LoginController extends Controller<AdoptionCentre> {

@FXML private ImageView bannerCat;
@FXML private Label loginLabel;
@FXML private Label loginUsername;
@FXML private TextField userName;
@FXML private Label loginEmail;
@FXML private TextField eMail;
@FXML private Label loginManager;
@FXML private TextField managerID;
@FXML private Button loginBtn;
@FXML private Button exitBtn;
@FXML private GridPane customerBox;
@FXML private GridPane managerBox;

public AdoptionCentre getAdoptionCentre() {
    return model;
}

@FXML
public void initialize() {
    Image bannerImage1= new Image(getClass().getResourceAsStream("/image/cat_banner.jpg"));
    bannerCat.setImage(bannerImage1);
    loginBtn.setDisable(true);
    userName.textProperty().addListener((obs, oldText, newText) -> enableLogin());
    eMail.textProperty().addListener((obs, oldText, newText) -> enableLogin());
    managerID.textProperty().addListener((obs, oldText, newText) -> enableLogin());
}

private void enableLogin() {
    boolean isCustomer = !userName.getText().isEmpty() && !eMail.getText().isEmpty();
    boolean isCustomerBox= !userName.getText().isEmpty() || !eMail.getText().isEmpty();
    boolean isManager = !managerID.getText().isEmpty();
    loginBtn.setDisable(!(isCustomer || isManager));
    customerBox.setDisable(isManager);
    managerBox.setDisable(isCustomerBox);
}

public void handleExit(ActionEvent event){
    stage.close();
}
public void handleLogin(ActionEvent event) throws IOException {
    String name = userName.getText();
    String email = eMail.getText();
    String managerId = managerID.getText();
    
    try{
        
        if(!name.isEmpty() && !email.isEmpty() && managerId.isEmpty())
        {Customer customer = getAdoptionCentre().getUsers().validateCustomer(name, email); 
            if(customer!=null) {
                AdoptionCentre.setLoggedInUser(customer);
                ViewLoader.showStage(getAdoptionCentre(), "/view/CustomerDashboard.fxml", "Customer Dashboard", new Stage());
            }
        
    
    }
        else if(!managerId.isEmpty() && name.isEmpty() && email.isEmpty()) {
        Manager manager = getAdoptionCentre().getUsers().validateManager(managerId);
        if (manager!=null) {
            AdoptionCentre.setLoggedInUser(manager);
            ViewLoader.showStage(getAdoptionCentre(), "/view/ManagerDashboard.fxml", "Manager Dashboard", new Stage());
        }
        }
    }
    catch (InvalidOperationException | UnauthorizedAccessException e) {
        ViewLoader.showStage(e, "/view/ErrorView.fxml", "Error", new Stage());
    }
}
}