package Controllers.ProjetControllers;

import BddPackage.AvnentCoutOperation;
import BddPackage.CoutOperation;
import BddPackage.ProjetOperation;
import Models.AvnentCout;
import Models.Cout;
import Models.Programme;
import Models.Projet;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {


    @FXML
    TextField tfProgramme,tfRecherche;

    @FXML
    TableView<List<StringProperty>> tvProjet;
    @FXML
    TableColumn<List<StringProperty>, String> idColumn,nbLogtsCuColumn;
    @FXML
    TableColumn<List<StringProperty>, String> NomColumn,siteColumn,cfColumn,dateColumn;
    @FXML
    TableColumn<List<StringProperty>, String> RColumn,AvnRColumn,ApRColumn,VrColumn,AvnVrColumn,ApVrColumn,EColumn,AvnEColumn,ApEColumn;


    private Programme programmeSelected;
    private final ProjetOperation operation = new ProjetOperation();
    private final CoutOperation coutOperation = new CoutOperation();
    private final AvnentCoutOperation avnentCoutOperation = new AvnentCoutOperation();
    private final ObservableList<List<StringProperty>> pTableData = FXCollections.observableArrayList();
    private ArrayList<Projet> projets = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        programmeSelected = new Programme();

        idColumn.setCellValueFactory(data -> data.getValue().get(0));
        NomColumn.setCellValueFactory(data -> data.getValue().get(1));
        siteColumn.setCellValueFactory(data -> data.getValue().get(2));
        cfColumn.setCellValueFactory(data -> data.getValue().get(3));
        dateColumn.setCellValueFactory(data -> data.getValue().get(4));
        nbLogtsCuColumn.setCellValueFactory(data -> data.getValue().get(5));
        RColumn.setCellValueFactory(data -> data.getValue().get(6));
        AvnRColumn.setCellValueFactory(data -> data.getValue().get(7));
        ApRColumn.setCellValueFactory(data -> data.getValue().get(8));
        VrColumn.setCellValueFactory(data -> data.getValue().get(9));
        AvnVrColumn.setCellValueFactory(data -> data.getValue().get(10));
        ApVrColumn.setCellValueFactory(data -> data.getValue().get(11));
        EColumn.setCellValueFactory(data -> data.getValue().get(12));
        AvnEColumn.setCellValueFactory(data -> data.getValue().get(13));
        ApEColumn.setCellValueFactory(data -> data.getValue().get(14));

        refresh();

    }

    @FXML
    private void ActionSelectProgramme() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjetViews/SelectProgrammeView.fxml"));
            DialogPane temp = loader.load();
            SelectProgrammeController controller = loader.getController();
            controller.InitListProgramme(programmeSelected);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tfRecherche.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            tfProgramme.setText(programmeSelected.getNomProgramme());
            refreshByProgramme(programmeSelected.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionRefreshProgrammeSelected() {
        tfProgramme.clear();
        programmeSelected = new Programme();
        refresh();
    }

    @FXML
    private void ActionAdd() {
        String ProgrammeSelected = tfProgramme.getText().trim();
        if (!ProgrammeSelected.isEmpty()) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjetViews/AddView.fxml"));
                DialogPane temp = loader.load();
                AddController controller = loader.getController();
                controller.InitAdd(programmeSelected);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.initOwner(this.tfRecherche.getScene().getWindow());
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                closeButton.setVisible(false);
                dialog.showAndWait();

                refreshByProgramme(programmeSelected.getId());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un programme");
            alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ){

            ActionUpdate();
        }
    }

    @FXML
    private void ActionUpdate(){
        List<StringProperty> selected = tvProjet.getSelectionModel().getSelectedItem();

        if (selected != null){
            String stIDdPrj = selected.get(0).getValue();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjetViews/UpdateView.fxml"));
                DialogPane temp = loader.load();
                UpdateController controller = loader.getController();
                controller.InitUpdate(stIDdPrj);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.initOwner(this.tfRecherche.getScene().getWindow());
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                closeButton.setVisible(false);
                dialog.showAndWait();

                if (!tfProgramme.getText().isEmpty()) refreshByProgramme(programmeSelected.getId());
                else refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un Projet");
            alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionAddToArchive(){
        List<StringProperty> selected = tvProjet.getSelectionModel().getSelectedItem();

        if (selected != null){
            String stIDdPrj = selected.get(0).getValue();
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer l'archivation");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir archiver le projet intitulé : " + selected.get(1).getValue() );
                alertConfirmation.initOwner(this.tfRecherche.getScene().getWindow());
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        operation.addToArchive(Integer.parseInt(stIDdPrj));
                        if (tfProgramme.getText().isEmpty()) refresh();
                        else refreshByProgramme(programmeSelected.getId());
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir le projet pour archiver");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionListArchive(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjetViews/ArchiveView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tfRecherche.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            if (tfProgramme.getText().isEmpty()) refresh();
            else refreshByProgramme(programmeSelected.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSearch(){
        // filtrer les données
        ObservableList<List<StringProperty>> dataProgramme = tvProjet.getItems();
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
            } else return String.valueOf(stringProperties.get(5)).contains(txtRecherche);
        });

        SortedList<List<StringProperty>> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvProjet.comparatorProperty());
        tvProjet.setItems(sortedList);
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        String ProgrammeSelect = tfProgramme.getText().trim();
        if (ProgrammeSelect.isEmpty()) refresh();
        else refreshByProgramme(programmeSelected.getId());
    }

    private void refresh(){
        projets = operation.getAll();
        chargeTable();
    }

    private void refreshByProgramme(int idProg){
        projets = operation.getAllByProgramme(idProg);
        chargeTable();
    }

    private void chargeTable(){
        try {
            pTableData.clear();
            tvProjet.getItems().clear();
            projets.forEach(projet -> {

                Cout coutR = coutOperation.getCoutByProjet(projet.getId(), "REALISATION");
                Cout coutE = coutOperation.getCoutByProjet(projet.getId(), "ETUDE");
                Cout coutV = coutOperation.getCoutByProjet(projet.getId(), "VRD");

                ArrayList<AvnentCout> avnentCoutsR = avnentCoutOperation.getAll(coutR.getId());
                ArrayList<AvnentCout> avnentCoutsE = avnentCoutOperation.getAll(coutE.getId());
                ArrayList<AvnentCout> avnentCoutsV = avnentCoutOperation.getAll(coutV.getId());

                double totR;
                double totE;
                double totV;

                totR = avnentCoutsR.stream().mapToDouble(AvnentCout::getMontant).sum();
                totE = avnentCoutsE.stream().mapToDouble(AvnentCout::getMontant).sum();
                totV = avnentCoutsV.stream().mapToDouble(AvnentCout::getMontant).sum();

                List<StringProperty> data = new ArrayList<>();

                data.add(0, new SimpleStringProperty(String.valueOf(projet.getId())));
                data.add(1, new SimpleStringProperty(projet.getNom()));
                data.add(2, new SimpleStringProperty(projet.getSite()));
                data.add(3, new SimpleStringProperty(projet.getNumeroCF()));
                data.add(4, new SimpleStringProperty(projet.getDateInsription()));
                data.add(5, new SimpleStringProperty(String.valueOf(projet.getNomberLogts())));
                data.add(6, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", coutR.getMontant())));
                data.add(7, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totR)));
                data.add(8, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totR + coutR.getMontant())));
                data.add(9, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", coutV.getMontant())));
                data.add(10, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totV)));
                data.add(11, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totV + coutV.getMontant())));
                data.add(12, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", coutE.getMontant())));
                data.add(13, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totE)));
                data.add(14, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totE + coutE.getMontant())));


                pTableData.add(data);
            });
            tvProjet.setItems(pTableData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionAddAvnant(){
        List<StringProperty> selected = tvProjet.getSelectionModel().getSelectedItem();

        if (selected != null){
            Projet prSelect = operation.get(Integer.parseInt(selected.get(0).getValue()));
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjetViews/AddAvnantView.fxml"));
                DialogPane temp = loader.load();
                AddAvnantController controller = loader.getController();
                controller.Init(prSelect);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.initOwner(this.tfRecherche.getScene().getWindow());
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                closeButton.setVisible(false);
                dialog.showAndWait();

                if (tfProgramme.getText().isEmpty()) refresh();
                else refreshByProgramme(programmeSelected.getId());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un Projet");
            alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionListAvnant(){
        List<StringProperty> selected = tvProjet.getSelectionModel().getSelectedItem();

        if (selected != null){
            Projet prSelect = operation.get(Integer.parseInt(selected.get(0).getValue()));
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjetViews/ListAvnantView.fxml"));
                DialogPane temp = loader.load();
                ListAvnantController controller = loader.getController();
                controller.Init(prSelect);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.initOwner(this.tfRecherche.getScene().getWindow());
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                closeButton.setVisible(false);
                dialog.showAndWait();

                if (tfProgramme.getText().isEmpty()) refresh();
                else refreshByProgramme(programmeSelected.getId());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un Projet");
            alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

}
