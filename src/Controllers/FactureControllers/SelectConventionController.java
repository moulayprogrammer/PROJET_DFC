package Controllers.FactureControllers;

import BddPackage.MarConBcOperation;
import Models.MarConBc;
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

public class SelectConventionController implements Initializable {

    @FXML
    TextField tfRecherche;

    @FXML
    TableView<MarConBc> tvConvention;
    @FXML
    TableColumn<MarConBc,String> organismeColumnn,NomColumnn,NumerColumn,typeColumn,htColumn,tvaColumn,ttcColumn,dateColumn;
    @FXML
    TableColumn<MarConBc,Integer> idColumn,idOrgColumn,nbLogtsColumn;


    private final ObservableList<MarConBc> dataTable = FXCollections.observableArrayList();
    private final MarConBcOperation operation = new MarConBcOperation();
    private MarConBc marConBc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idOrgColumn.setCellValueFactory(new PropertyValueFactory<>("idOrganisme"));
        organismeColumnn.setCellValueFactory(new PropertyValueFactory<>("nomOrganisme"));
        NomColumnn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        NumerColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        htColumn.setCellValueFactory(new PropertyValueFactory<>("ht"));
        tvaColumn.setCellValueFactory(new PropertyValueFactory<>("tva"));
        ttcColumn.setCellValueFactory(new PropertyValueFactory<>("ttc"));
        nbLogtsColumn.setCellValueFactory(new PropertyValueFactory<>("numbreLogts"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        refresh();

    }

    public void Init(MarConBc marConBc){
        this.marConBc = marConBc;
    }


    @FXML
    private void ActionSelectionMar(){

        MarConBc mar = tvConvention.getSelectionModel().getSelectedItem();

        if (mar != null){
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer la séletion");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir sélectioner la convention ");
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        this.marConBc.setId(mar.getId());
                        this.marConBc.setIdOrganisme(mar.getIdOrganisme());
                        this.marConBc.setNomOrganisme(mar.getNomOrganisme());
                        this.marConBc.setNom(mar.getNom());
                        this.marConBc.setNumero(mar.getNumero());
                        this.marConBc.setType(mar.getType());
                        this.marConBc.setHt(mar.getHt());
                        this.marConBc.setTva(mar.getTva());
                        this.marConBc.setTtc(mar.getTtc());
                        this.marConBc.setNumbreLogts(mar.getNumbreLogts());
                        this.marConBc.setDate(mar.getDate());

                        ActionAnnuler();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir la convention pour sélectioner");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionSearch(){
        // filtrer les données
        ObservableList<MarConBc> dataMar = tvConvention.getItems();
        FilteredList<MarConBc> filteredData = new FilteredList<>(dataMar, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super MarConBc>) mar -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (mar.getNomOrganisme().contains(txtRecherche)) {
                return true;
            } else if (mar.getNom().contains(txtRecherche)) {
                return true;
            } else if (mar.getNumero().contains(txtRecherche)) {
                return true;
            } else if (mar.getType().contains(txtRecherche)) {
                return true;
            } else return mar.getDate().contains(txtRecherche);
        });

        SortedList<MarConBc> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvConvention.comparatorProperty());
        tvConvention.setItems(sortedList);
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        refresh();
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfRecherche.getScene().getWindow()).close();
    }

    private void refresh(){
        ArrayList<MarConBc> marConBcs = operation.getAll();
        dataTable.setAll(marConBcs);
        tvConvention.setItems(dataTable);
    }
}
