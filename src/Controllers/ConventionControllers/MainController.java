package Controllers.ConventionControllers;

import BddPackage.AvnentOperation;
import BddPackage.MarConBcOperation;
import Models.MarConBc;
import Models.ModelesTabels.ProjetTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {

    @FXML
    TextField tfProjet,tfRecherche;

    @FXML
    TableView<List<StringProperty>> tvConvention;
    @FXML
    TableColumn<List<StringProperty>,String> organismeColumnn,NomColumnn,NumerColumn,typeColumn,MontInit,AvnSup,AvnDem,MontEnga,dateColumn;
    @FXML
    TableColumn<List<StringProperty>,String> idColumn,idOrgColumn,nbLogtsColumn;



    private ProjetTable projet;
    private final ObservableList<List<StringProperty>> dataTable = FXCollections.observableArrayList();
    private final MarConBcOperation operation = new MarConBcOperation();
    private final AvnentOperation avnentOperation = new AvnentOperation();
    private ArrayList<MarConBc> marConBcs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(data -> data.getValue().get(0));
        idOrgColumn.setCellValueFactory(data -> data.getValue().get(1));
        organismeColumnn.setCellValueFactory(data -> data.getValue().get(2));
        NomColumnn.setCellValueFactory(data -> data.getValue().get(3));
        NumerColumn.setCellValueFactory(data -> data.getValue().get(4));
        typeColumn.setCellValueFactory(data -> data.getValue().get(5));
        MontInit.setCellValueFactory(data -> data.getValue().get(6));
        AvnSup.setCellValueFactory(data -> data.getValue().get(7));
        AvnDem.setCellValueFactory(data -> data.getValue().get(8));
        MontEnga.setCellValueFactory(data -> data.getValue().get(9));
        nbLogtsColumn.setCellValueFactory(data -> data.getValue().get(10));
        dateColumn.setCellValueFactory(data -> data.getValue().get(11));


        projet = new ProjetTable();
        refresh();
    }

    @FXML
    private void ActionSelectProjet(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/SelectProjetView.fxml"));
            DialogPane temp = loader.load();
            SelectProjetController controller = loader.getController();
            controller.Init(this.projet);
            javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.showAndWait();

            tfProjet.setText(this.projet.getNom());
            refreshByProjet();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionClearSelect(){
        tfProjet.clear();
        projet = new ProjetTable();
        refresh();
    }

    @FXML
    private void ActionAdd(){
        String projet = tfProjet.getText().trim();
        if (!projet.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/AddView.fxml"));
                DialogPane temp = loader.load();
                AddController controller = loader.getController();
                controller.Init(this.projet);
                javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.showAndWait();

                refreshByProjet();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp s??lectionner un projet");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionUpdate(){
        List<StringProperty> selected = tvConvention.getSelectionModel().getSelectedItem();
        if (selected != null) {
            MarConBc convention = operation.get(Integer.valueOf(selected.get(0).getValue()));
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/UpdateView.fxml"));
                DialogPane temp = loader.load();
                UpdateController controller = loader.getController();
                controller.Init(convention);
                javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.showAndWait();

                if (tfProjet.getText().trim().isEmpty()) refresh();
                else refreshByProjet();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp s??lectionner un convention");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionAddToArchive(){
        List<StringProperty> selected = tvConvention.getSelectionModel().getSelectedItem();
        if (selected != null) {
            MarConBc convention = operation.get(Integer.valueOf(selected.get(0).getValue()));

            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("Confirmer l'archivation");
            alertConfirmation.setContentText("??tes-vous s??r de vouloir archiver la convention ");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("D'accord");

            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("Annuler");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    if (operation.addToArchive(convention)){
                        if (tfProjet.getText().trim().isEmpty()) refresh();
                        else refreshByProjet();
                    }
                }
            });

        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp s??lectionner une convention pour archiver");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionListArchive(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/ArchiveView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.showAndWait();

            if (tfProjet.getText().isEmpty()) refresh();
            else refreshByProjet();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionAddAvnant(){
        List<StringProperty> selected = tvConvention.getSelectionModel().getSelectedItem();
        if (selected != null) {
            MarConBc convention = operation.get(Integer.valueOf(selected.get(0).getValue()));

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/AddAvnantView.fxml"));
                DialogPane temp = loader.load();
                AddAvnantController controller = loader.getController();
                controller.Init(convention);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.showAndWait();

                if (tfProjet.getText().isEmpty()) refresh();
                else refreshByProjet();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp s??lectionner un Convention");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionListAvnant(){
        List<StringProperty> selected = tvConvention.getSelectionModel().getSelectedItem();
        if (selected != null) {
            MarConBc convention = operation.get(Integer.valueOf(selected.get(0).getValue()));

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/ListAvnantView.fxml"));
                DialogPane temp = loader.load();
                ListAvnantController controller = loader.getController();
                controller.Init(convention);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.showAndWait();

                if (tfProjet.getText().isEmpty()) refresh();
                else refreshByProjet();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp s??lectionner un Convention");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void ActionSearch() {
        // filtrer les donn??es
        ObservableList<List<StringProperty>> dataProgramme = tvConvention.getItems();
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
            } else if (stringProperties.get(4).toString().contains(txtRecherche)) {
                return true;
            }else if (stringProperties.get(5).toString().contains(txtRecherche)) {
                return true;
            } else return String.valueOf(stringProperties.get(6)).contains(txtRecherche);
        });

        SortedList<List<StringProperty>> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvConvention.comparatorProperty());
        tvConvention.setItems(sortedList);

    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        if (tfProjet.getText().trim().isEmpty()) refresh();
        else refreshByProjet();
    }

    private void refresh(){
        marConBcs = operation.getAll();
        chargeTable();
    }

    private void refreshByProjet(){
        marConBcs = operation.getAllByProjet(projet.getId());
        chargeTable();
    }

    private void chargeTable(){
        dataTable.clear();
        marConBcs.forEach(marConBc -> {
            List<StringProperty> data = new ArrayList<>();

            double AvnSup = avnentOperation.getSum(marConBc.getId(), "SUPLEMENTAIRE") ;
            double AvnDem = avnentOperation.getSum(marConBc.getId(), "DEMENITIVE") ;

            data.add(0, new SimpleStringProperty(String.valueOf(marConBc.getId())));
            data.add(1, new SimpleStringProperty(String.valueOf(marConBc.getIdOrganisme())));
            data.add(2, new SimpleStringProperty(marConBc.getNomOrganisme()));
            data.add(3, new SimpleStringProperty(marConBc.getNom()));
            data.add(4, new SimpleStringProperty(marConBc.getNumero()));
            data.add(5, new SimpleStringProperty(marConBc.getType()));
            data.add(6, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",marConBc.getTtc())));
            data.add(7, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",AvnSup)));
            data.add(8, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",AvnDem)));
            data.add(9, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",(marConBc.getTtc() + AvnSup + AvnDem) )));
            data.add(10, new SimpleStringProperty(String.valueOf(marConBc.getNumbreLogts())));
            data.add(11, new SimpleStringProperty(marConBc.getDate()));

            dataTable.add(data);
        });
        tvConvention.setItems(dataTable);
    }
}
