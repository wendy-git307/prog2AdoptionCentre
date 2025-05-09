package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.*;
import model.Animals.Animal;
import model.Animals.Cat;
import model.Animals.Dog;
import model.Animals.Rabbit;
import model.Application.AdoptionCentre;
import model.Exceptions.InvalidOperationException;
import java.io.IOException;

public class ManagerDashboardController extends Controller<AdoptionCentre>{
@FXML private ImageView bannerCat;
@FXML private Label managerDashboard;
@FXML private Button userListBtn;
@FXML private Button addBtn;
@FXML private Button removeBtn;
@FXML private Button closeBtn;
@FXML private TableView<Animal> allAnimalsTable;
@FXML private TableView<Animal> catTypes;
@FXML private TableView<Animal> dogTypes;
@FXML private TableView<Animal> rabbitTypes;
@FXML private StackPane tableAnimals;
@FXML private Button allBtn;
@FXML private Button catBtn;
@FXML private Button dogBtn;
@FXML private Button rabbitBtn;

private AdoptionCentre getAdoptionCentre() {
    return model;
}

private final ObservableList<Animal> all = FXCollections.observableArrayList();
private final ObservableList<Animal> cats = FXCollections.observableArrayList();
private final ObservableList<Animal> rabbits = FXCollections.observableArrayList();
private final ObservableList<Animal> dogs = FXCollections.observableArrayList();

@FXML
public void initialize () {
    Image bannerImage1= new Image(getClass().getResourceAsStream("/image/cat_banner.jpg"));
    bannerCat.setImage(bannerImage1);
    setUpColumns(allAnimalsTable);
    setUpColumns(catTypes);
    setUpColumns(dogTypes);
    setUpColumns(rabbitTypes);
    processData();
    buttonActions();
    removeBtn.setDisable(true);
    allAnimalsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        removeBtn.setDisable(newSelection == null);
    });
    catTypes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        removeBtn.setDisable(newSelection == null);
    });
    dogTypes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        removeBtn.setDisable(newSelection == null);
    });
    rabbitTypes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        removeBtn.setDisable(newSelection == null);
    });
}


private void setUpColumns(TableView<Animal> tableView) throws NullPointerException {
    tableView.getColumns().clear();
    TableColumn<Animal, String> name= new TableColumn<>("Name");
    name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    TableColumn<Animal, String> type= new TableColumn<>("Type");
    type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
    TableColumn<Animal, Number> age= new TableColumn<>("Age");
    age.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
    TableColumn<Animal, String> status= new TableColumn<>("Adoption Status");
    status.setCellValueFactory(cellData -> cellData.getValue().isAdoptedProperty());

    tableView.getColumns().setAll(name, type, age, status);
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);// get the table view cleaner 
}

public void processData() {
    ObservableList<Animal> newAnimalList = getAdoptionCentre().getAnimals().getAnimals();
    
    all.setAll(newAnimalList);//Update the main list (allAnimalsTable first) after adding an animal

    // clear previous data in each filtered table before repopulating new data after an animal is added
    cats.clear();
    dogs.clear();
    rabbits.clear();

    // update each filtered table
    for(Animal a: newAnimalList) {
        if ( a instanceof Cat) {cats.add(a);}
        else if ( a instanceof Dog) {dogs.add(a);}
        else if ( a instanceof Rabbit) {rabbits.add(a);}
    }

    allAnimalsTable.setItems(newAnimalList);
    catTypes.setItems(cats);
    dogTypes.setItems(dogs);
    rabbitTypes.setItems(rabbits);
}

private void buttonActions() {
    allBtn.setOnAction(e -> showTable(allAnimalsTable));
    catBtn.setOnAction(e -> showTable(catTypes));
    dogBtn.setOnAction( e -> showTable(dogTypes));
    rabbitBtn.setOnAction( e -> showTable(rabbitTypes));
}
private void showTable(TableView<Animal> tableToDisplay) {
    allAnimalsTable.setVisible(false);
    catTypes.setVisible(false);
    dogTypes.setVisible(false);
    rabbitTypes.setVisible(false);

    tableToDisplay.setVisible(true);
}
@FXML
public void handleUserList(ActionEvent event) throws IOException {
    ViewLoader.showStage(getAdoptionCentre().getUsers(), "/view/UserListView.fxml", "User List", new Stage());
}
@FXML
public void handleAdd(ActionEvent event) throws IOException {
    Stage addAnimalStage = new Stage();
    ViewLoader.showStage(getAdoptionCentre(), "/view/AddAnimalView.fxml", "Add Animal", addAnimalStage);
    addAnimalStage.setOnHiding(e -> processData());
    // Trigger the processData to update the list of animals (including the allAnimalsTable and the filtered table that it belongs to)) when the AddAnimal is closed (~hidden)
}
@FXML
public void handleClose(ActionEvent event){
    stage.close();
}
@FXML
public void handleRemove(ActionEvent event) throws IOException  {
    TableView<Animal> currentSelectedTable = getVisibleTable();
    if (currentSelectedTable!= null) {
        Animal selectedAnimal = currentSelectedTable.getSelectionModel().getSelectedItem();
        if (selectedAnimal!=null) {
            if (selectedAnimal.isAdopted()) {
                showError(new InvalidOperationException(selectedAnimal.getName() + " is already adopted"));
                return;
            }
            currentSelectedTable.getItems().remove(selectedAnimal);
        }
    getAdoptionCentre().getAnimals().remove(selectedAnimal);
    }
}
private TableView<Animal> getVisibleTable() {
    if (allAnimalsTable.isVisible()) return allAnimalsTable;
    if (catTypes.isVisible()) return catTypes;
    if (dogTypes.isVisible()) return dogTypes;
    if (rabbitTypes.isVisible()) return rabbitTypes;
    return null;
}
private void showError(Exception ex) throws IOException {
    ViewLoader.showStage(ex, "/view/ErrorView.fxml", "Error", new Stage());
}
}
