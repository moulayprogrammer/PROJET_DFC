package Controllers.OrganismeControllers;

import BddPackage.OrganismeOperation;
import BddPackage.ProgrammeOperation;
import Models.Organisme;
import Models.Programme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {

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
    private void ActionAdd(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/OrganismeViews/AddView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.showAndWait();

            refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionUpdate(){
        Organisme organismeUpdate =  tvOrganisme.getSelectionModel().getSelectedItem();

        if (organismeUpdate != null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/OrganismeViews/UpdateView.fxml"));
                DialogPane temp = loader.load();
                UpdateController controller = loader.getController();
                controller.Init(organismeUpdate);
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
            alertWarning.setContentText("Veuillez choisir l'avnant pour modifier");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionAddToArchive(){

        Organisme organismeArchive =  tvOrganisme.getSelectionModel().getSelectedItem();

        if (organismeArchive != null){
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer l'archivation");
                alertConfirmation.setContentText("??tes-vous s??r de vouloir archiver l'organisme : "+organismeArchive.getRaisonSocial() );
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        operation.AddToArchive(organismeArchive);
                        refresh();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir l'organisme pour archiver");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionDeleteFromArchive(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/OrganismeViews/ArchiveView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.showAndWait();

            refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void ActionSearch() {
        // filtrer les donn??es
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
        ArrayList<Organisme> organismes = operation.getAll();
        dataTable.setAll(organismes);
        tvOrganisme.setItems(dataTable);
    }
}
