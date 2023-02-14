package Controllers.ConventionControllers;

import BddPackage.OdsArretOperation;
import BddPackage.OdsRepriseOperation;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ListOdsController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<OdsArret> tvOdsArret;
    @FXML
    TableView<OdsReprise> odsRepriseTableView;
    @FXML
    TableColumn<OdsArret,Integer> idColumn;
    @FXML
    TableColumn<OdsReprise,Integer> idColumnC;
    @FXML
    TableColumn<OdsArret,String> dateColumn, numberColumn, raisonColumn;
    @FXML
    TableColumn<OdsReprise,String> NumeroColumn,dateColumnC;
    @FXML
    TabPane tabPane;


    private MarConBc marConBc;
    private final OdsArretOperation operation = new OdsArretOperation();
    private final OdsRepriseOperation odsRepriseOperation = new OdsRepriseOperation();
    private final ObservableList<OdsArret> dataTable = FXCollections.observableArrayList();
    private final ObservableList<OdsReprise> dataTableCompte = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        raisonColumn.setCellValueFactory(new PropertyValueFactory<>("raison"));


        idColumnC.setCellValueFactory(new PropertyValueFactory<>("id"));
        NumeroColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        dateColumnC.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public void Init(MarConBc marConBc){
        this.marConBc = marConBc;

        refreshArret();

        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            switch (newTab.getId()){
                case "tabArret":
                    refreshArret();
                    break;
                case "tabReprise":
                    refreshReprise();
                    break;
            }
        });
    }
    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ){

            ActionUpdateAvnant();
        }
    }
    @FXML
    private void ActionUpdateAvnant(){
        String tabId = tabPane.getSelectionModel().getSelectedItem().getId();
        switch (tabId){
            case "tabArret":
                try {
                    OdsArret odsArret = tvOdsArret.getSelectionModel().getSelectedItem();

                    if (odsArret != null){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/UpdateAretteView.fxml"));
                            DialogPane temp = loader.load();
                            UpdateOdsArretController controller = loader.getController();
                            controller.Init(odsArret);
                            Dialog<ButtonType> dialog = new Dialog<>();
                            dialog.setDialogPane(temp);
                            dialog.resizableProperty().setValue(false);
                            dialog.initOwner(this.tfRecherche.getScene().getWindow());
                            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                            closeButton.setVisible(false);
                            dialog.showAndWait();

                            refreshArret();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("Attention ");
                        alertWarning.setContentText("svp sélectionner un Ods");
                        alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setText("d'accord");
                        alertWarning.showAndWait();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "tabReprise":
                try {

                    OdsReprise odsReprise = odsRepriseTableView.getSelectionModel().getSelectedItem();

                    if (odsReprise != null){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/UpdateRepriseView.fxml"));
                            DialogPane temp = loader.load();
                            UpdateOdsRepriseController controller = loader.getController();
                            controller.Init(odsReprise);
                            Dialog<ButtonType> dialog = new Dialog<>();
                            dialog.setDialogPane(temp);
                            dialog.resizableProperty().setValue(false);
                            dialog.initOwner(this.tfRecherche.getScene().getWindow());
                            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                            closeButton.setVisible(false);
                            dialog.showAndWait();

                            refreshReprise();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("Attention ");
                        alertWarning.setContentText("svp sélectionner un Ods");
                        alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setText("d'accord");
                        alertWarning.showAndWait();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @FXML
    private void ActionDeleteAvnant(){
        String tabId = tabPane.getSelectionModel().getSelectedItem().getId();
        switch (tabId){
            case "tabArret":
                try {
                    OdsArret odsArret = tvOdsArret.getSelectionModel().getSelectedItem();

                    if (odsArret != null){
                        try {
                            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            alertConfirmation.setHeaderText("Confirmer la suppression");
                            alertConfirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'Ods " );
                            alertConfirmation.initOwner(this.tfRecherche.getScene().getWindow());
                            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                            okButton.setText("D'accord");

                            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                            cancel.setText("Annuler");

                            alertConfirmation.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.CANCEL) {
                                    alertConfirmation.close();
                                } else if (response == ButtonType.OK) {
                                    operation.delete(odsArret);
                                    refreshArret();
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("Attention ");
                        alertWarning.setContentText("svp sélectionner un Avnant");
                        alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setText("d'accord");
                        alertWarning.showAndWait();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "tabReprise":
                try {
                    OdsReprise odsReprise = odsRepriseTableView.getSelectionModel().getSelectedItem();

                    if (odsReprise != null){
                        try {
                            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            alertConfirmation.setHeaderText("Confirmer la suppression");
                            alertConfirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'Ods " );
                            alertConfirmation.initOwner(this.tfRecherche.getScene().getWindow());
                            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                            okButton.setText("D'accord");

                            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                            cancel.setText("Annuler");

                            alertConfirmation.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.CANCEL) {
                                    alertConfirmation.close();
                                } else if (response == ButtonType.OK) {
                                    odsRepriseOperation.delete(odsReprise);
                                    refreshReprise();
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("Attention ");
                        alertWarning.setContentText("svp sélectionner un Ods");
                        alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setText("d'accord");
                        alertWarning.showAndWait();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void refreshArret(){
        ArrayList<OdsArret> odsArrets = operation.getAllByConvention(marConBc.getId());
        dataTable.setAll(odsArrets);
        tvOdsArret.setItems(dataTable);
    }

    private void refreshReprise(){
        ArrayList<OdsReprise> odsReprises = odsRepriseOperation.getAllByConvention(marConBc.getId());
        dataTableCompte.setAll(odsReprises);
        odsRepriseTableView.setItems(dataTableCompte);
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage) tvOdsArret.getScene().getWindow()).close();
    }

    @FXML
    private void ActionRefresh(){
        String tabId = tabPane.getSelectionModel().getSelectedItem().getId();
        tfRecherche.clear();
        switch (tabId){
            case "tabArret":
                refreshArret();
                break;
            case "tabReprise":
                refreshArret();
                break;
        }

    }
    @FXML
    private void ActionSearch(){

        String tabId = tabPane.getSelectionModel().getSelectedItem().getId();
        switch (tabId){
            case "tabArret":
                try {
                    // filtrer les données
                    ObservableList<OdsArret> dataAvnant = tvOdsArret.getItems();
                    FilteredList<OdsArret> filteredData = new FilteredList<>(dataAvnant, e -> true);
                    String txtRecherche = tfRecherche.getText().trim();

                    filteredData.setPredicate((Predicate<? super OdsArret>) odsArret -> {
                        if (txtRecherche.isEmpty()) {
                            //loadDataInTable();
                            return true;
                        } else if (odsArret.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).contains(txtRecherche)) {
                            return true;
                        } else if (odsArret.getNumber().contains(txtRecherche)) {
                            return true;
                        } else return odsArret.getRaison().contains(txtRecherche);
                    });

                    SortedList<OdsArret> sortedList = new SortedList<>(filteredData);
                    sortedList.comparatorProperty().bind(tvOdsArret.comparatorProperty());
                    tvOdsArret.setItems(sortedList);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "tabReprise":
                try {
                    // filtrer les données
                    ObservableList<OdsReprise> dataAvnant = odsRepriseTableView.getItems();
                    FilteredList<OdsReprise> filteredData = new FilteredList<>(dataAvnant, e -> true);
                    String txtRecherche = tfRecherche.getText().trim();

                    filteredData.setPredicate((Predicate<? super OdsReprise>) odsReprise -> {
                        if (txtRecherche.isEmpty()) {
                            //loadDataInTable();
                            return true;
                        } else if (odsReprise.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).contains(txtRecherche)) {
                            return true;
                        } else return odsReprise.getNumber().contains(txtRecherche);
                    });

                    SortedList<OdsReprise> sortedList = new SortedList<>(filteredData);
                    sortedList.comparatorProperty().bind(odsRepriseTableView.comparatorProperty());
                    odsRepriseTableView.setItems(sortedList);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }

    }

}
