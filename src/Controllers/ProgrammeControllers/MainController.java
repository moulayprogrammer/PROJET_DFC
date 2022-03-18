package Controllers.ProgrammeControllers;

import BddPackage.ProgrammeOperation;
import Models.ModelesTabels.ProjetProgrammeList;
import Models.ModelesTabels.ProjetTable;
import Models.Programme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {

    @FXML
    AnchorPane MainPanel;
    @FXML
    TextField tfRecherche;
    @FXML
    TableView<Programme> programmeTable;
    @FXML
    TableColumn<Programme,String> codeColumn,NomColumn,cdColumn,dateColumn;
    @FXML
    TableColumn<Programme,Integer> nbLogtsColumn;


    private final ObservableList<Programme> dataTable = FXCollections.observableArrayList();
    private final ProgrammeOperation operation = new ProgrammeOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        NomColumn.setCellValueFactory(new PropertyValueFactory<>("nomProgramme"));
        nbLogtsColumn.setCellValueFactory(new PropertyValueFactory<>("nombreLogts"));
        cdColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCD"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));

        refresh();
    }

    @FXML
    private void ActionAdd(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProgrammeViews/AddView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.showAndWait();

            refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionUpdate(){
        Programme programmeUpdate = programmeTable.getSelectionModel().getSelectedItem();
        if (programmeUpdate != null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProgrammeViews/UpdateView.fxml"));
                DialogPane temp = loader.load();
                UpdateController controller = loader.getController();
                controller.InitUpdate(programmeUpdate);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.showAndWait();
                refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir le programme pour modifier");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionAddToArchive(){
        Programme programmeDelete = programmeTable.getSelectionModel().getSelectedItem();
        if (programmeDelete != null){
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer l'archivation");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir archiver le programme numéro : "+programmeDelete.getCode() );
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        operation.AddToArchive(programmeDelete);
                        refresh();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir le programme pour archiver");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }


    @FXML
    private void ActionDeleteFromArchive(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProgrammeViews/ArchiveView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.showAndWait();

            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refresh(){
        ArrayList<Programme> programmes = operation.getAll();
        dataTable.setAll(programmes);
        programmeTable.setItems(dataTable);
    }

    @FXML
    private void ActionRefresh(){
        clearRecherche();
        refresh();
    }

    private void clearRecherche(){
        tfRecherche.clear();
    }

    @FXML
    void ActionSearch() {
        // filtrer les données
        ObservableList<Programme> dataProgramme = programmeTable.getItems();
        FilteredList<Programme> filteredData = new FilteredList<>(dataProgramme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super Programme>) programme -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (programme.getCode().contains(txtRecherche)) {
                return true;
            } else if (programme.getNomProgramme().contains(txtRecherche)) {
                return true;
            } else if (programme.getNumeroCD().contains(txtRecherche)) {
                return true;
            } else if (String.valueOf(programme.getNombreLogts()).contains(txtRecherche)) {
                return true;
            } else return programme.getDateInscription().contains(txtRecherche);
        });

        SortedList<Programme> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(programmeTable.comparatorProperty());
        programmeTable.setItems(sortedList);

    }
}
