package Controllers.OrganismeControllers;

import BddPackage.OrganismOperation;
import Models.Organism;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ArchiveController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<Organism> tvOrganisme;
    @FXML
    TableColumn<Organism,String> resonSocialColumn,adressColumn,telColumn,rcColumn,nifColumn;
    @FXML
    TableColumn<Organism, Integer> idColumn;

    private final ObservableList<Organism> dataTable = FXCollections.observableArrayList();
    private final OrganismOperation operation = new OrganismOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        resonSocialColumn.setCellValueFactory(new PropertyValueFactory<>("raisonSocial"));
        adressColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        rcColumn.setCellValueFactory(new PropertyValueFactory<>("rc"));
        nifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));

        refresh();
    }

    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ){

            ActionDeleteFromArchive();
        }
    }

    @FXML
    private void ActionDeleteFromArchive(){
        Organism organismeArchive =  tvOrganisme.getSelectionModel().getSelectedItem();

        if (organismeArchive != null){
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer désarchivage");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir désarchiver l'organisme : "+organismeArchive.getRaisonSocial() );
                alertConfirmation.initOwner(this.tfRecherche.getScene().getWindow());
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        operation.DeleteFromArchive(organismeArchive);

                        ActionAnnuler();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir l'organisme pour désarchiver");
            alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
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
        tfRecherche.clear();
        refresh();
    }

    private void refresh(){
        ArrayList<Organism> organismes = operation.getAllArchive();
        dataTable.setAll(organismes);
        tvOrganisme.setItems(dataTable);
    }

    @FXML
    void ActionSearch() {
        // filtrer les données
        ObservableList<Organism> dataOrganisme = tvOrganisme.getItems();
        FilteredList<Organism> filteredData = new FilteredList<>(dataOrganisme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super Organism>) organisme -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (organisme.getRaisonSocial().contains(txtRecherche)) {
                return true;
            } else if (organisme.getAdresse().contains(txtRecherche)) {
                return true;
            } else if (organisme.getTel().contains(txtRecherche)) {
                return true;
            } else if (organisme.getRc().contains(txtRecherche)) {
                return true;
            } else return organisme.getNif().contains(txtRecherche);
        });

        SortedList<Organism> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvOrganisme.comparatorProperty());
        tvOrganisme.setItems(sortedList);
    }
}
