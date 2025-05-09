package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import model.Animals.Animal;
import model.Application.AdoptionCentre;
import model.Exceptions.InvalidOperationException;
import model.Users.Customer;
import java.io.IOException;

public class CustomerDashboardController extends Controller<AdoptionCentre>{
    @FXML private ImageView bannerCat;
    @FXML private Label welcomeLabel;
    @FXML private ListView<Animal> animalListToAdopt;
    @FXML private Button detailsBtn;
    @FXML private Button adoptBtn;
    @FXML private Button closeBtn;


public AdoptionCentre getAdoptionCentre() {
return model;
}

@FXML
public void initialize() {
    Image bannerImage1= new Image(getClass().getResourceAsStream("/image/cat_banner.jpg"));
    bannerCat.setImage(bannerImage1);
    Customer customer = (Customer) getAdoptionCentre().getLoggedInUser();
    welcomeLabel.setText("Welcome " + customer.getFirstName());
    adoptBtn.setDisable(true);
    // set the Adopt button to be disabled until the listener changes to the new selection
    animalListToAdopt.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
    -> { adoptBtn.setDisable(newSelection == null);});
    // wonder whether this will create a distinct adoption centre // do we need share the data between the customer and manager dashboard// do we need to have a shared application state?
    if (model!=null)
    {ObservableList<Animal> allAvailableAnimals = model.getAvailableAnimals();
    animalListToAdopt.setItems(allAvailableAnimals);}
    
}

@FXML
public void handleAdopt(ActionEvent event) throws IOException {
    Animal selectedAnimal = animalListToAdopt.getSelectionModel().getSelectedItem();
    if (selectedAnimal==null) { return;}
    Customer customer = (Customer) getAdoptionCentre().getLoggedInUser();
    if (selectedAnimal != null && !selectedAnimal.isAdopted()) {
        try {
        selectedAnimal.adopt();
        customer.adoptAnimal(selectedAnimal);
        animalListToAdopt.setItems(model.getAvailableAnimals());
        adoptBtn.setDisable(true);
        }
        catch (InvalidOperationException e) {
        ViewLoader.showStage(e, "/view/ErrorView.fxml", "Error", new Stage());
        }
    }
}
@FXML
public void handleDetails(ActionEvent event) throws IOException {
    ViewLoader.showStage(getAdoptionCentre(), "/view/DetailsView.fxml", "My Details", new Stage());
}

@FXML
public void handleClose(ActionEvent event){
    stage.close();
}


}
