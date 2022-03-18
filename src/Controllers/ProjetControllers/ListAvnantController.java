package Controllers.ProjetControllers;

import BddPackage.AvnentCoutOperation;
import Models.AvnentCout;
import Models.Cout;
import Models.ModelesTabels.ProjetTable;
import Models.Programme;
import Models.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ListAvnantController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<AvnentCout> tvAvnant;
    @FXML
    TableColumn<AvnentCout,Integer> idColumn;
    @FXML
    TableColumn<AvnentCout,String> dateColumn,MontantColumn,coutColumn,typeColumn;

    private Projet projetSelected;
    private ArrayList<AvnentCout> avnentCouts = new ArrayList<>();
    private AvnentCoutOperation operation = new AvnentCoutOperation();
    private final ObservableList<AvnentCout> dataTable = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        MontantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        coutColumn.setCellValueFactory(new PropertyValueFactory<>("coutApplique"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));


    }

    public void Init(Projet projet){
        this.projetSelected = projet;

        refresh();
    }

    @FXML
    private void ActionSave(){

    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tvAvnant.getScene().getWindow()).close();
    }

    @FXML
    private void ActionSearch(){
        // filtrer les données
        ObservableList<AvnentCout> dataAvnant = tvAvnant.getItems();
        FilteredList<AvnentCout> filteredData = new FilteredList<>(dataAvnant, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super AvnentCout>) avnentCout -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (avnentCout.getDate().contains(txtRecherche)) {
                return true;
            } else if (avnentCout.getCoutApplique().contains(txtRecherche)) {
                return true;
            } else return avnentCout.getType().contains(txtRecherche);
        });

        SortedList<AvnentCout> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvAvnant.comparatorProperty());
        tvAvnant.setItems(sortedList);
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        refresh();
    }

    @FXML
    private void ActionUpdateAvnant(){

        AvnentCout avnentCout = tvAvnant.getSelectionModel().getSelectedItem();

        if (avnentCout != null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjetViews/UpdateAvnantView.fxml"));
                DialogPane temp = loader.load();
                UpdateAvnantController controller = loader.getController();
                controller.Init(avnentCout);
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
            alertWarning.setContentText("svp sélectionner un Avnant");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionDeleteAvnant(){
        AvnentCout avnentCout = tvAvnant.getSelectionModel().getSelectedItem();

        if (avnentCout != null){
            try {
                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer la suppression");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'Avnant " );
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        operation.delete(avnentCout);
                        refresh();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un Avnant");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    private void refresh(){
        avnentCouts = operation.getAllByProjet(projetSelected.getId());
        dataTable.setAll(avnentCouts);
        tvAvnant.setItems(dataTable);
    }

}
