package Controllers.ProgrammeControllers;

import BddPackage.ProgrammeOperation;
import Models.Programme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ArchiveController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<Programme> programmeTable;
    @FXML
    TableColumn<Programme,String> codeColumn,NomColumn,cdColumn,dateColumn;
    @FXML
    TableColumn<Programme,Integer> nbLogtsColumn;

    private ObservableList<Programme> dataTable = FXCollections.observableArrayList();
    private ArrayList<Programme> programmes = new ArrayList<>();
    private ProgrammeOperation operation = new ProgrammeOperation();

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
    private void ActionDeleteFromArchive(){
        Programme programmeDelete = programmeTable.getSelectionModel().getSelectedItem();
        if (programmeDelete != null){
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer désarchivage");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir désarchiver le programme numéro : "+programmeDelete.getCode() );
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        operation.DeleteFromArchive(programmeDelete);
                        ActionAnnuler();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir le programme pour désarchiver");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfRecherche.getScene().getWindow()).close();
    }

    @FXML
    private void ActionRefresh(){
        clearRecherche();
        refresh();
    }

    private void clearRecherche(){
        tfRecherche.clear();
    }

    private void refresh(){
        programmes = operation.getAllArchiver();
        dataTable.setAll(programmes);
        programmeTable.setItems(dataTable);
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
            } else return programme.getNumeroCD().contains(txtRecherche);
        });

        SortedList<Programme> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(programmeTable.comparatorProperty());
        programmeTable.setItems(sortedList);

    }
}
