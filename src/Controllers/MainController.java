package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainPane.setPadding(new Insets(0,10,5,10));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/HelloView.fxml"));
            StackPane temp = loader.load();
            mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void close(){
        ((Stage)mainPane.getScene().getWindow()).close();
    }

    @FXML
    private void ActionChargeProgramme(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProgrammeViews/MainView.fxml"));
            BorderPane temp = loader.load();
            mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionChargeProjet(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjetViews/MainView.fxml"));
            BorderPane temp = loader.load();
            mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionChargeOrganisme(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/OrganismeViews/MainView.fxml"));
            BorderPane temp = loader.load();
            mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionChargeConvention(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/MainView.fxml"));
            BorderPane temp = loader.load();
            mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionChargeFacture(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FactureViews/MainView.fxml"));
            BorderPane temp = loader.load();
            mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionChargeSuiviEtb(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SuiviEtbViews/MainView.fxml"));
            BorderPane temp = loader.load();
            mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
