package Controllers.OrganismeControllers;

import BddPackage.OrganismeOperation;
import Models.Organisme;
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
    TableView<Organisme> tvOrganisme;
    @FXML
    TableColumn<Organisme,String> resonSocialColumn,adressColumn,telColumn,rcColumn,nifColumn;
    @FXML
    TableColumn<Organisme, Integer> idColumn;

    private final ObservableList<Organisme> dataTable = FXCollections.observableArrayList();
    private final OrganismeOperation operation = new OrganismeOperation();

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
    private void ActionDeleteFromArchive(){
        Organisme organismeArchive =  tvOrganisme.getSelectionModel().getSelectedItem();

        if (organismeArchive != null){
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer désarchivage");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir désarchiver l'organisme : "+organismeArchive.getRaisonSocial() );
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
        ObservableList<Organisme> dataOrganisme = tvOrganisme.getItems();
        FilteredList<Organisme> filteredData = new FilteredList<>(dataOrganisme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super Organisme>) organisme -> {
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

        SortedList<Organisme> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvOrganisme.comparatorProperty());
        tvOrganisme.setItems(sortedList);

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
        ArrayList<Organisme> organismes = operation.getAllArchive();
        dataTable.setAll(organismes);
        tvOrganisme.setItems(dataTable);
    }
}
