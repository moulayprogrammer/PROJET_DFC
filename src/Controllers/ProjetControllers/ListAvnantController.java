package Controllers.ProjetControllers;

import BddPackage.AvnentCoutOperation;
import Models.AvnentCout;
import Models.Project;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ListAvnantController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<List<StringProperty>> tvAvnant;
    @FXML
    TableColumn<List<StringProperty>,String> idColumn,dateColumn,MontantColumn,coutColumn,typeColumn;

    private Project projetSelected;
    private final AvnentCoutOperation operation = new AvnentCoutOperation();
    private final ObservableList<List<StringProperty>> dataTable = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(data -> data.getValue().get(0));
        dateColumn.setCellValueFactory(data -> data.getValue().get(1));
        MontantColumn.setCellValueFactory(data -> data.getValue().get(2));
        coutColumn.setCellValueFactory(data -> data.getValue().get(3));
        typeColumn.setCellValueFactory(data -> data.getValue().get(4));

    }

    public void Init(Project projet){
        this.projetSelected = projet;

        refresh();
    }

    @FXML
    private void ActionSave(){

    }
    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ){

            ActionUpdateAvnant();
        }
    }
    @FXML
    private void ActionUpdateAvnant(){
        List<StringProperty> selected = tvAvnant.getSelectionModel().getSelectedItem();

        if (selected != null){
            try {
                AvnentCout avnentCout = operation.get(Integer.parseInt(selected.get(0).get()));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjectViews/UpdateAvnantView.fxml"));
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
    }

    @FXML
    private void ActionDeleteAvnant(){
        List<StringProperty> selected = tvAvnant.getSelectionModel().getSelectedItem();

        if (selected != null){
            try {
                AvnentCout avnentCout = operation.get(Integer.parseInt(selected.get(0).get()));

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
    }

    private void refresh(){
        dataTable.clear();
        ArrayList<AvnentCout> avnentCouts = operation.getAllByProjet(projetSelected.getId());
        for (AvnentCout av : avnentCouts) {
            List<StringProperty> data = new ArrayList<>();
            data.add(0, new SimpleStringProperty(String.valueOf(av.getId())));
            data.add(1, new SimpleStringProperty(av.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            data.add(2, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", av.getMontant())));
            data.add(3, new SimpleStringProperty(av.getCoutApplique()));
            data.add(4, new SimpleStringProperty(av.getType()));

            dataTable.add(data);
        }
        tvAvnant.setItems(dataTable);
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
        ObservableList<List<StringProperty>> dataProgramme = tvAvnant.getItems();
        FilteredList<List<StringProperty>> filteredData = new FilteredList<>(dataProgramme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super List<StringProperty>>) stringProperties -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (stringProperties.get(1).toString().contains(txtRecherche)) {
                return true;
            } else if (stringProperties.get(2).toString().contains(txtRecherche)) {
                return true;
            } else if (stringProperties.get(3).toString().contains(txtRecherche)) {
                return true;
            }  else return String.valueOf(stringProperties.get(4)).contains(txtRecherche);
        });

        SortedList<List<StringProperty>> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvAvnant.comparatorProperty());
        tvAvnant.setItems(sortedList);
    }
}
