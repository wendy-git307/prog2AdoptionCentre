package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Users.User;
import model.Users.Users;


public class UserListController extends Controller<Users>{
    @FXML private ImageView bannerCat;
    @FXML private ListView<String> userListToDisplay;
    @FXML private Button closeBtn;
    @FXML private Label userList;


    @FXML
    public void initialize() {
    Image bannerImage1= new Image(getClass().getResourceAsStream("/image/cat_banner.jpg"));
    bannerCat.setImage(bannerImage1);
    ObservableList<String> allUsers = FXCollections.observableArrayList();

    for (User user : model.getUsers()) {
        if (user == null) {
            return;
        }
        else if (user!= null) {
        allUsers.add(user.toString());
        }
        }
        
    userListToDisplay.setItems(allUsers);
    }
    @FXML
    private void handleClose(ActionEvent event) {
        stage.close();
    }

    
}
