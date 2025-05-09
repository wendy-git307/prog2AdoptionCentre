package controller;

import java.io.IOException;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import model.Animals.Animal;
import model.Animals.Cat;
import model.Animals.Dog;
import model.Animals.Rabbit;
import model.Exceptions.InvalidOperationException;
import model.Application.AdoptionCentre;



public class AddAnimalController extends Controller<AdoptionCentre> {
    @FXML private ImageView bannerCat;
    @FXML private Label addAnimal;
    @FXML private ChoiceBox<String> choiceBox;
    @FXML private Label typeLabel;
    @FXML private Label nameLabel;
    @FXML private Label ageLabel;
    @FXML private TextField nameAnimal;
    @FXML private TextField ageAnimal;
    @FXML private Button addBtn;
    @FXML private Button closeBtn;
    
    
    public AdoptionCentre getAdoptionCentre() {
        return model;
    }
    


    @FXML private void initialize() {
        choiceBox.getItems().addAll("Cat", "Dog", "Rabbit");
        choiceBox.setValue("Cat");
        addBtn.setDisable(true);
        nameAnimal.textProperty().addListener((obs, oldText, newText) -> validateAnimal());
        ageAnimal.textProperty().addListener((obs, oldText, newText) -> validateAnimal());
        Image bannerImage1= new Image(getClass().getResourceAsStream("/image/cat_banner.jpg"));
        bannerCat.setImage(bannerImage1);
    }
    private void validateAnimal() {
        addBtn.setDisable(nameAnimal.getText().isEmpty()||ageAnimal.getText().isEmpty());
    }

    @FXML
    private void handleAddAnimal() throws InvalidOperationException, IOException {
        String type = choiceBox.getValue();
        String name = nameAnimal.getText();
        String age = ageAnimal.getText();
        
        int ageNumber;
            try {
                ageNumber = Integer.parseInt(age);
            } catch( NumberFormatException e) {
                showError(new InvalidOperationException("Age must be an integer"));
                return;
            }
        
            if (isDuplicateAnimal(name)) {return;} //stop processing
            Animal animalAdded = null;
            switch (type) {
            case "Cat": animalAdded = new Cat(name, ageNumber);
            break;
            case "Dog": animalAdded = new Dog(name, ageNumber);
            break;
            case "Rabbit": animalAdded = new Rabbit(name, ageNumber);
            break;
            }
        getAdoptionCentre().getAnimals().getAnimals().add(animalAdded);
        stage.close();
        
    }

    private boolean isDuplicateAnimal(String name) throws InvalidOperationException, IOException {
        if (name==null || model == null || model.getAnimals() == null) {
            return false;
        }
          // search for an animal by name in the list
        for (Animal animal: model.getAnimals().getAnimals()) {
            if (animal.getName().equals(name)) {
                showError(new InvalidOperationException(name + " already exists in the adoption centre")); 
                return true;}
            } return false;
        }

    private void showError(Exception ex) throws IOException {
        ViewLoader.showStage(ex, "/view/ErrorView.fxml", "Error", new Stage());
    }

    @FXML
    private void handleClose(ActionEvent event) {
        stage.close();
    }
    
}
