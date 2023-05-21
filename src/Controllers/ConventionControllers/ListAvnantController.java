package Controllers.ConventionControllers;

import BddPackage.AvnentCompteOperation;
import BddPackage.AvnentOperation;
import Models.AvnentCompteMarConBc;
import Models.AvnentMarConBc;
import Models.MarConBc;
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

public class ListAvnantController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<AvnentMarConBc> tvAvnant;
    @FXML
    TableView<AvnentCompteMarConBc> tvAvnantCompte;
    @FXML
    TableColumn<AvnentMarConBc,Integer> idColumn;
    @FXML
    TableColumn<AvnentMarConBc,Integer> idColumnC;
    @FXML
    TableColumn<AvnentMarConBc,String> dateColumn,MontantColumn,coutColumn,typeColumn;
    @FXML
    TableColumn<AvnentMarConBc,String> NumeroColumn,dateColumnC,NumeroCompteColumn,BankColumn,AgenceColumn;
    @FXML
    TabPane tabPane;


    private MarConBc marConBc;
    private ArrayList<AvnentMarConBc> avnentCouts = new ArrayList<>();
    private ArrayList<AvnentCompteMarConBc> avnentComptes = new ArrayList<>();
    private AvnentOperation operation = new AvnentOperation();
    private AvnentCompteOperation avnentCompteOperation = new AvnentCompteOperation();
    private final ObservableList<AvnentMarConBc> dataTable = FXCollections.observableArrayList();
    private final ObservableList<AvnentCompteMarConBc> dataTableCompte = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        MontantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));


        idColumnC.setCellValueFactory(new PropertyValueFactory<>("id"));
        NumeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        dateColumnC.setCellValueFactory(new PropertyValueFactory<>("date"));
        NumeroCompteColumn.setCellValueFactory(new PropertyValueFactory<>("compteNumero"));
        BankColumn.setCellValueFactory(new PropertyValueFactory<>("compteBank"));
        AgenceColumn.setCellValueFactory(new PropertyValueFactory<>("compteAgence"));

    }

    public void Init(MarConBc marConBc){
        this.marConBc = marConBc;

        refresh();

        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            switch (newTab.getId()){
                case "tabMontant":
                    refresh();
                    break;
                case "tabCompte":
                    refreshCompte();
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
            case "tabMontant":
                try {
                    AvnentMarConBc avnentCout = tvAvnant.getSelectionModel().getSelectedItem();

                    if (avnentCout != null){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/Avenant/UpdateAvnantView.fxml"));
                            DialogPane temp = loader.load();
                            UpdateAvnantController controller = loader.getController();
                            controller.Init(avnentCout);
                            Dialog<ButtonType> dialog = new Dialog<>();
                            dialog.setDialogPane(temp);
                            dialog.resizableProperty().setValue(false);
                            dialog.initOwner(this.tfRecherche.getScene().getWindow());
                            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                            closeButton.setVisible(false);
                            dialog.showAndWait();

                            refresh();

                        } catch (IOException e) {
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
            case "tabCompte":
                try {
                    AvnentCompteMarConBc avnentCout = tvAvnantCompte.getSelectionModel().getSelectedItem();

                    if (avnentCout != null){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/UpdateAvnantCompteView.fxml"));
                            DialogPane temp = loader.load();
                            UpdateAvnantCompteController controller = loader.getController();
                            controller.Init(avnentCout);
                            Dialog<ButtonType> dialog = new Dialog<>();
                            dialog.setDialogPane(temp);
                            dialog.resizableProperty().setValue(false);
                            dialog.initOwner(this.tfRecherche.getScene().getWindow());
                            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                            closeButton.setVisible(false);
                            dialog.showAndWait();

                            refreshCompte();

                        } catch (IOException e) {
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
        }
    }

    @FXML
    private void ActionDeleteAvnant(){
        String tabId = tabPane.getSelectionModel().getSelectedItem().getId();
        switch (tabId){
            case "tabMontant":
                try {
                    AvnentMarConBc avnentCout = tvAvnant.getSelectionModel().getSelectedItem();

                    if (avnentCout != null){
                        try {
                            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            alertConfirmation.setHeaderText("Confirmer la suppression");
                            alertConfirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'Avnant " );
                            alertConfirmation.initOwner(this.tfRecherche.getScene().getWindow());
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
                        alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setText("d'accord");
                        alertWarning.showAndWait();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "tabCompte":
                try {
                    AvnentCompteMarConBc avnentCout = tvAvnantCompte.getSelectionModel().getSelectedItem();

                    if (avnentCout != null){
                        try {
                            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            alertConfirmation.setHeaderText("Confirmer la suppression");
                            alertConfirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'Avnant " );
                            alertConfirmation.initOwner(this.tfRecherche.getScene().getWindow());
                            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                            okButton.setText("D'accord");

                            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                            cancel.setText("Annuler");

                            alertConfirmation.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.CANCEL) {
                                    alertConfirmation.close();
                                } else if (response == ButtonType.OK) {
                                    avnentCompteOperation.delete(avnentCout);
                                    refreshCompte();
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
        }
    }

    private void refresh(){
        avnentCouts = operation.getAllByConvention(marConBc.getId());
        dataTable.setAll(avnentCouts);
        tvAvnant.setItems(dataTable);
    }

    private void refreshCompte(){
        avnentComptes = avnentCompteOperation.getAllByConvention(marConBc.getId());
        dataTableCompte.setAll(avnentComptes);
        tvAvnantCompte.setItems(dataTableCompte);
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tvAvnant.getScene().getWindow()).close();
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        refresh();
    }
    @FXML
    private void ActionSearch(){
        // filtrer les données
        ObservableList<AvnentMarConBc> dataAvnant = tvAvnant.getItems();
        FilteredList<AvnentMarConBc> filteredData = new FilteredList<>(dataAvnant, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super AvnentMarConBc>) avnentCout -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (avnentCout.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).contains(txtRecherche)) {
                return true;
            } else if (avnentCout.getType().contains(txtRecherche)) {
                return true;
            } else return avnentCout.getType().contains(txtRecherche);
        });

        SortedList<AvnentMarConBc> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvAvnant.comparatorProperty());
        tvAvnant.setItems(sortedList);
    }

}
