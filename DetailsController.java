package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Animals.Animal;
import model.Application.AdoptionCentre;
import model.Users.Customer;

public class DetailsController extends Controller<AdoptionCentre> {
    @FXML private ImageView bannerCat;
    @FXML private ListView<Animal> adoptedAnimalList;
    @FXML private Button closeBtn;
    @FXML private Label nameCustomer;
    @FXML private Label animalAdopted;
    
    public AdoptionCentre getAdoptionCentre() {
        return model;
    }

    @FXML
    public void initialize() {

    Image bannerImage1= new Image(getClass().getResourceAsStream("/image/cat_banner.jpg"));
    bannerCat.setImage(bannerImage1);
    Customer customer = (Customer) getAdoptionCentre().getLoggedInUser();
    nameCustomer.setText(customer.getName());
    adoptedAnimalList.setItems(customer.adoptedAnimals().getAnimals());
    }
    
    @FXML
    private void handleClose(ActionEvent event) {
        stage.close();
    }

}
