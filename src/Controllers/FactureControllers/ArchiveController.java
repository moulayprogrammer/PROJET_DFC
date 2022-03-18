package Controllers.FactureControllers;

import BddPackage.FactureOperation;
import Models.Facture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    TableView<Facture> tvFacture;
    @FXML
    TableColumn<Facture,String> NumeroColumnn,DateColumnn;
    @FXML
    TableColumn<Facture,Integer> idColumn,idMarColumn;
    @FXML
    TableColumn<Facture, Double> MontantColumn;

    private final ObservableList<Facture> dataTable = FXCollections.observableArrayList();
    private final FactureOperation operation = new FactureOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idMarColumn.setCellValueFactory(new PropertyValueFactory<>("idMarConBc"));
        NumeroColumnn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        DateColumnn.setCellValueFactory(new PropertyValueFactory<>("date"));
        MontantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));

        refresh();
    }

    @FXML
    private void ActionDeleteFromArchive(){
        Facture facture = tvFacture.getSelectionModel().getSelectedItem();

        if (facture != null){
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer désarchivage");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir désarchiver la facture" );
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        if (operation.DeleteFromArchive(facture)) ActionAnnuler();

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir la facture pour désarchiver");
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
    void ActionSearch() {
        // filtrer les données
        ObservableList<Facture> dataFacture = tvFacture.getItems();
        FilteredList<Facture> filteredData = new FilteredList<>(dataFacture, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super Facture>) facture -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (facture.getNumero().contains(txtRecherche)) {
                return true;
            } else if (facture.getDate().toString().contains(txtRecherche)) {
                return true;
            }  else return String.valueOf(facture.getMontant()).contains(txtRecherche);
        });

        SortedList<Facture> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvFacture.comparatorProperty());
        tvFacture.setItems(sortedList);
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        refresh();
    }

    private void refresh(){
        ArrayList<Facture> factures = operation.getAllArchive();
        dataTable.setAll(factures);
        tvFacture.setItems(dataTable);
    }
}
