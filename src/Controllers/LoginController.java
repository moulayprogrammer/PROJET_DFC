package Controllers;

import BddPackage.UserOperation;
import Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField tfUser;
    @FXML
    PasswordField pfPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfUser.getScene().getWindow()).close();
    }

    @FXML
    private void ActionLogin(){
        String username = tfUser.getText().trim();
        String password = pfPass.getText().trim();

        if ( !username.isEmpty() && !password.isEmpty() ){
            User user = new User(username,password);
            login(user);
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez remplir les champs vides");
            alertWarning.initOwner(this.tfUser.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    private void login(User user){
        UserOperation userOperation = new UserOperation();
        boolean log = true; // userOperation.isExist(user);
        if (log){
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainView.fxml"));
                AnchorPane temp = loader.load();
                Scene scene = new Scene(temp);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.getIcons().add(new Image("Icons/logo.png"));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ActionAnnuler();
        }
        else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("le nom d'utilisateur ou le mot de passe est erroné");
            alertWarning.initOwner(this.tfUser.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.show();
        }
    }

}
